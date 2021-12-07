package com.volodymyr.bookaro.order.application;

import com.volodymyr.bookaro.catalog.db.BookJpaRepository;
import com.volodymyr.bookaro.catalog.domain.Book;
import com.volodymyr.bookaro.order.application.port.ManipulateOrderUseCase;
import com.volodymyr.bookaro.order.db.OrderJpaRepository;
import com.volodymyr.bookaro.order.db.RecipientJpaRepository;
import com.volodymyr.bookaro.order.domain.Order;
import com.volodymyr.bookaro.order.domain.OrderItem;
import com.volodymyr.bookaro.order.domain.Recipient;
import com.volodymyr.bookaro.order.domain.UpdateStatusResult;
import com.volodymyr.bookaro.security.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ManipulateOrderService implements ManipulateOrderUseCase {

    private final OrderJpaRepository repository;
    private final BookJpaRepository bookRepository;
    private final RecipientJpaRepository recipientRepository;
    private final UserSecurity userSecurity;

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderCommand command) {
        Set<OrderItem> items = command.getItems()
                .stream()
                .map(this::toOrderItem)
                .collect(Collectors.toSet());

        Order order = Order
                .builder()
                .recipient(getOrCreateRecipient(command.getRecipient()))
                .delivery(command.getDelivery())
                .items(items)
                .build();
        Order saveOrder = repository.save(order);
        bookRepository.saveAll(reduceBooks(items));
        return PlaceOrderResponse.success(saveOrder.getId());
    }

    private Recipient getOrCreateRecipient(Recipient recipient) {
        return recipientRepository.findByEmailIgnoreCase(recipient.getEmail())
                .orElse(recipient);
    }

    private Set<Book> reduceBooks(Set<OrderItem> items) {
        return items.stream()
                .map(item -> {
                    Book book = item.getBook();
                    book.setAvailable(book.getAvailable() - item.getQuantity());
                    return book;
                }).collect(Collectors.toSet());
    }

    private OrderItem toOrderItem(OrderItemCommand command) {
        Book book = bookRepository.getOne(command.getBookId());
        int quantity = command.getQuantity();
        if (book.getAvailable() >= quantity) {
            return new OrderItem(book, command.getQuantity());
        }
        throw new IllegalArgumentException("Too many copies of book " + book.getId() + " requested: " + quantity + " " +
                " of " + book.getAvailable() + " available ");
    }

    @Override
    public void deleteOrderById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public UpdateStatusResponse updateOrderStatus(UpdateStatusCommand command) {
        return repository.findById(command.getOrderId())
                .map(order -> {
                    if (userSecurity.isOwnerOrAdmin(order.getRecipient().getEmail(), command.getUser())) {
                        UpdateStatusResult result = order.updateStatus(command.getStatus());
                        if (result.isRevoked()) {
                            bookRepository.saveAll(revokeBooks(order.getItems()));
                        }
                        repository.save(order);
                        return UpdateStatusResponse.success(order.getStatus());
                    }
                   return UpdateStatusResponse.failure(Error.FORBIDDEN);
                })
                .orElse(UpdateStatusResponse.failure(Error.NOT_FOUND));
    }
//
//    private boolean hasAccess(UpdateStatusCommand command, Order order) {
//        String email = command.getEmail();
//        return command.getEmail().equalsIgnoreCase(order.getRecipient().getEmail()) ||
//                email.equalsIgnoreCase("admin@example.org");
//    }

    private Set<Book> revokeBooks(Set<OrderItem> items) {
        return items.stream()
                .map(item -> {
                    Book book = item.getBook();
                    book.setAvailable(book.getAvailable() + item.getQuantity());
                    return book;
                }).collect(Collectors.toSet());
    }
}
