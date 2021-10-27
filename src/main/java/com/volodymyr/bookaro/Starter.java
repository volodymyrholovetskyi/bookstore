package com.volodymyr.bookaro;

import com.volodymyr.bookaro.catalog.application.port.CatalogUseCase;
import com.volodymyr.bookaro.catalog.application.port.CatalogUseCase.CreateBookCommand;
import com.volodymyr.bookaro.catalog.application.port.CatalogUseCase.UpdateBookCommand;
import com.volodymyr.bookaro.catalog.application.port.CatalogUseCase.UpdateBookResponse;
import com.volodymyr.bookaro.catalog.domain.Book;
import com.volodymyr.bookaro.order.application.port.PlaceOrderUseCase;
import com.volodymyr.bookaro.order.application.port.PlaceOrderUseCase.PlaceOrderCommand;
import com.volodymyr.bookaro.order.application.port.PlaceOrderUseCase.PlaceOrderResponse;
import com.volodymyr.bookaro.order.application.port.QueryOrderUseCase;
import com.volodymyr.bookaro.order.domain.OrderItem;
import com.volodymyr.bookaro.order.domain.Recipient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class Starter implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final PlaceOrderUseCase placeOrderUseCase;
    private final QueryOrderUseCase queryOrderUseCase;
    private final String title;

    public Starter(CatalogUseCase catalog,
                   PlaceOrderUseCase placeOrderUseCase,
                   QueryOrderUseCase queryOrderUseCase,
                   @Value("${starter.title}") String title) {
        this.catalog = catalog;
        this.placeOrderUseCase = placeOrderUseCase;
        this.queryOrderUseCase = queryOrderUseCase;
        this.title = title;
    }

    @Override
    public void run(String... args) throws Exception {

        initData();
        searchCatalog();
        placeOrder();
    }

    private void placeOrder() {
        Book clen = catalog.findOneByTitle("Clen")
                .orElseThrow(() -> new IllegalStateException("Cannot find a book"));
        Book architect = catalog.findOneByTitle("Architect")
                .orElseThrow(() -> new IllegalStateException("Cannot find a book"));

        //create recipient
        Recipient recipient = Recipient
                .builder()
                .name("Jan Kowalski")
.phone("+480 000")
                .city("Kraków")
                .street("Al. Sikorskiego 1")
                .zipCod("80-114")
                .email("mail@mail.com")
                .build();
        //place order command

        PlaceOrderCommand command = PlaceOrderCommand
                .builder()
                .recipient(recipient)
                .item(new OrderItem(clen, 10))
                .item(new OrderItem(architect, 5))
                .build();

        PlaceOrderResponse response = placeOrderUseCase.placeOrder(command);
        System.out.println("Created ORDER id: " + response.getOrderId());

        //list all orders
        queryOrderUseCase.findAll()
                .forEach(order -> System.out.println("GOT ORDER WITH TOTAL PRICE: " + order.totalPrice() + " DETAILS:" +
                        " " +
                        order));
    }

    private void searchCatalog() {
        findByTitle();
        findAndUpdate();
        findByTitle();
    }

    private void initData() {
        catalog.addBook(new CreateBookCommand("Clen", "Helion", 1998, new BigDecimal("19.90")));
        catalog.addBook(new CreateBookCommand("Architect", "Helion", 1996 , new BigDecimal("11.90")));
    }

    private void findByTitle() {
        List<Book> books = catalog.findByTitle(this.title);
        books.forEach(System.out::println);
    }

    private void findAndUpdate() {
        System.out.println("Update book....");
        catalog.findOneTitleAndAuthor("Clen", "Helion")
                .ifPresent(book -> {
                    UpdateBookCommand command = UpdateBookCommand.builder()
                            .id(book.getId())
                            .title("Clen cod")
                            .build();
                    UpdateBookResponse response = catalog.updateBook(command);
                    System.out.println("Update book result: " + response.isSuccess());
                });
    }


}
