package com.volodymyr.bookaro.order.application;

import com.volodymyr.bookaro.catalog.db.BookJpaRepository;
import com.volodymyr.bookaro.catalog.domain.Book;
import com.volodymyr.bookaro.order.application.port.QueryOrderUseCase;
import com.volodymyr.bookaro.order.db.OrderJpaRepository;
import com.volodymyr.bookaro.order.domain.Order;
import com.volodymyr.bookaro.order.domain.OrderItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QueryOrderService implements QueryOrderUseCase {

    private final OrderJpaRepository repository;
    private final BookJpaRepository catalogRepository;

    @Override
    @Transactional
    public List<RichOrder> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toRichOrder)
                .collect(Collectors.toList());
    }



    @Override
    public Optional<RichOrder> findById(Long id) {
        return repository.findById(id)
                .map(this::toRichOrder);
    }

    private RichOrder toRichOrder(Order order){
      List<RichOrderItem> richItems = toRichItems(order.getItems());
      return new RichOrder(
              order.getId(),
              order.getStatus(),
              richItems,
              order.getRecipient(),
              order.getCreateAt()
      );
    }

    private List<RichOrderItem> toRichItems(List<OrderItem> items){
        return items.stream()
                .map(item -> {
                    Book book = catalogRepository
                            .findById(item.getBookId())
                            .orElseThrow(() -> new IllegalStateException("Unable to find book with ID: " + item.getBookId()));
                    return new RichOrderItem(book, item.getQuantity());
                })
                .collect(Collectors.toList());
    }
}
