package com.volodymyr.bookaro.order.application;

import com.volodymyr.bookaro.catalog.application.port.CatalogUseCase;
import com.volodymyr.bookaro.catalog.db.BookJpaRepository;
import com.volodymyr.bookaro.catalog.domain.Book;
import com.volodymyr.bookaro.clock.Clock;
import com.volodymyr.bookaro.order.application.port.ManipulateOrderUseCase;
import com.volodymyr.bookaro.order.application.port.ManipulateOrderUseCase.PlaceOrderCommand;
import com.volodymyr.bookaro.order.domain.OrderStatus;
import com.volodymyr.bookaro.order.domain.Recipient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        properties = "app.orders.payment-period=1H"
)
@AutoConfigureTestDatabase
class AbandonedOrdersJobTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public Clock.Fake clock(){
            return new Clock.Fake();
        }

    }

    @Autowired
    BookJpaRepository bookJpaRepository;

    @Autowired
    QueryOrderService queryOrderService;

    @Autowired
    AbandonedOrdersJob ordersJob;

    @Autowired
    ManipulateOrderService manipulateOrderService;

    @Autowired
    CatalogUseCase catalogUseCase;

    @Autowired
    Clock.Fake clock;

    @Test
    public void shouldMarkOrdersAsAbandoned(){
        // given
        Book book = givenEffectiveJava(50L);
        Long orderId = placeOrder(book.getId(), 15);

        // when
        clock.tick(Duration.ofHours(2));
        ordersJob.run();

        // then
        assertEquals(OrderStatus.ABANDONED, queryOrderService.findById(orderId).get().getStatus());
        assertEquals(50L, availableCopiesOf(book));
    }


    private Long placeOrder(Long bookId, int copies) {
        PlaceOrderCommand command = PlaceOrderCommand
                .builder()
                .recipient(recipient())
                .item(new ManipulateOrderUseCase.OrderItemCommand(bookId, copies))
                .build();
        return manipulateOrderService.placeOrder(command).getRight();
    }

    private Recipient recipient() {
        return Recipient.builder().email("marek@example.org").build();
    }

    private Book givenEffectiveJava(Long available) {
        return bookJpaRepository.save(
                new Book(
                        "Effective Java",
                        2005,
                        new BigDecimal(199.90),
                        available));
    }

    private Long availableCopiesOf(Book effectiveJava) {
        return catalogUseCase.findById(effectiveJava.getId()).get().getAvailable();
    }

}
