package com.volodymyr.bookaro;

import com.volodymyr.bookaro.catalog.application.CatalogService;
import com.volodymyr.bookaro.catalog.application.port.CatalogUseCase;
import com.volodymyr.bookaro.catalog.application.port.CatalogUseCase.CreateBookCommand;
import com.volodymyr.bookaro.catalog.db.AuthorJpaRepository;
import com.volodymyr.bookaro.catalog.domain.Author;
import com.volodymyr.bookaro.catalog.domain.Book;
import com.volodymyr.bookaro.order.application.port.ManipulateOrderUseCase;
import com.volodymyr.bookaro.order.application.port.ManipulateOrderUseCase.PlaceOrderCommand;
import com.volodymyr.bookaro.order.application.port.ManipulateOrderUseCase.PlaceOrderResponse;
import com.volodymyr.bookaro.order.application.port.QueryOrderUseCase;
import com.volodymyr.bookaro.order.domain.OrderItem;
import com.volodymyr.bookaro.order.domain.Recipient;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
@AllArgsConstructor
public class Starter implements CommandLineRunner {


    private final CatalogUseCase catalog;
    private final ManipulateOrderUseCase manipulateOrderUseCase;
    private final QueryOrderUseCase queryOrderUseCase;
    private final CatalogService catalogService;
    private final AuthorJpaRepository authorRepository;


    @Override
    public void run(String... args) throws Exception {

        initData();
        placeOrder();
    }

    private void placeOrder() {
        Book effectiveJava = catalog.findOneByTitle("Effective Java")
                .orElseThrow(() -> new IllegalStateException("Cannot find a book"));
        Book puzzlers = catalog.findOneByTitle("Java Puzzlers")
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

        PlaceOrderCommand command = PlaceOrderCommand
                .builder()
                .recipient(recipient)
                .item(new OrderItem(effectiveJava.getId(), 10))
                .item(new OrderItem(puzzlers.getId(), 5))
                .build();

        PlaceOrderResponse response = manipulateOrderUseCase.placeOrder(command);
        String result = response.handle(
                orderId -> "Created ORDER with id: " + orderId,
                error -> "Failed to created order: " + error
        );
        System.out.println(result);

        //list all orders
        queryOrderUseCase.findAll()
                .forEach(order -> System.out.println("GOT ORDER WITH TOTAL PRICE: " + order.totalPrice() + " DETAILS:" +
                        " " +
                        order));
    }

    private void initData() {

        Author joshua = new Author("Joshua", "Bloch");
        Author neal = new Author("Neal", "Gafter");

        authorRepository.save(joshua);
        authorRepository.save(neal);

        CreateBookCommand effectiveJava = new CreateBookCommand(
                "Effective Java",
                Set.of(joshua.getId(), neal.getId()),
                2018,
                new BigDecimal("99.00")
        );

        CreateBookCommand javaPuzzlers = new CreateBookCommand(
                "Java Puzzlers",
                Set.of(joshua.getId()),
                2005,
                new BigDecimal("79.00")
        );

        catalogService.addBook(effectiveJava);
        catalogService.addBook(javaPuzzlers);
    }
}
