package com.volodymyr.bookaro.order.application;

import com.volodymyr.bookaro.catalog.application.port.CatalogUseCase;
import com.volodymyr.bookaro.catalog.db.BookJpaRepository;
import com.volodymyr.bookaro.catalog.domain.Book;
import com.volodymyr.bookaro.order.application.port.QueryOrderUseCase;
import com.volodymyr.bookaro.order.domain.Delivery;
import com.volodymyr.bookaro.order.domain.OrderStatus;
import com.volodymyr.bookaro.order.domain.Recipient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.List;

import static com.volodymyr.bookaro.order.application.port.ManipulateOrderUseCase.OrderItemCommand;
import static com.volodymyr.bookaro.order.application.port.ManipulateOrderUseCase.PlaceOrderCommand;
import static com.volodymyr.bookaro.order.application.port.ManipulateOrderUseCase.PlaceOrderResponse;
import static com.volodymyr.bookaro.order.application.port.ManipulateOrderUseCase.UpdateStatusCommand;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@DataJpaTest
//@Import({ManipulateOrderService.class})
@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderServiceTest {

    @Autowired
    BookJpaRepository bookJpaRepository;

    @Autowired
    ManipulateOrderService service;

    @Autowired
    QueryOrderUseCase queryOrderService;

    @Autowired
    CatalogUseCase catalogUseCase;

    @Test
    public void userCanPlaceOrder() {
        // given
        Book effectiveJava = givenEffectiveJava(50L);
        Book jcip = givenJavaConcurrency(50L);
        PlaceOrderCommand command = PlaceOrderCommand
                .builder()
                .recipient(recipient())
                .item(new OrderItemCommand(effectiveJava.getId(), 15))
                .item(new OrderItemCommand(jcip.getId(), 10))
                .build();
        // when
        PlaceOrderResponse response = service.placeOrder(command);
        // then
        assertTrue(response.isSuccess());
        assertEquals(35L, availableCopiesOf(effectiveJava));
        assertEquals(40L, availableCopiesOf(jcip));
    }

    @Test
    public void userCanRevokeOrder() {
        // given
        Book effectiveJava = givenEffectiveJava(50L);
        String marek = "marek@example.org";
        Long orderId = placedOrder(effectiveJava.getId(), 15, marek);
        assertEquals(35L, availableCopiesOf(effectiveJava));

        // when

        UpdateStatusCommand command = new UpdateStatusCommand(orderId, OrderStatus.CANCELED, user(marek));
        service.updateOrderStatus(command);

        // then
        assertEquals(50L, availableCopiesOf(effectiveJava));
        assertEquals(OrderStatus.CANCELED, queryOrderService.findById(orderId).get().getStatus());

    }

    @Test
    public void shippingCostsAreAddedToTotalOrderPrice() {
        // given
        Book book = givenBook(50L, "49.90");

        // when
        Long orderId = placedOrder(book.getId(), 1);

        // then
        RichOrder order = orderOf(orderId);
        assertEquals("59.80", orderOf(orderId).getFinalPrice().toPlainString());
    }


    @Test
    public void shippingCostsAreDiscountedOver100zlotys() {
        // given
        Book book = givenBook(50L, "49.90");

        // when
        Long orderId = placedOrder(book.getId(), 3);

        // then
        RichOrder order = orderOf(orderId);
        assertEquals("149.70", order.getFinalPrice().toPlainString());
        assertEquals("149.70", order.getFinalPrice().toPlainString());
    }

    @Test
    public void cheapestBookIsHalfPricedWhenTotalOver200zlotys() {
        // given
        Book book = givenBook(50L, "49.90");

        // when
        Long orderId = placedOrder(book.getId(), 5);

        // then
        RichOrder order = orderOf(orderId);
        assertEquals("224.55", orderOf(orderId).getFinalPrice().toPlainString());
    }

    @Test
    public void cheapestBookIsFreeWhenTotalOver400zlotys() {
        // given
        Book book = givenBook(50L, "49.90");

        // when
        Long orderId = placedOrder(book.getId(), 10);

        // then
        RichOrder order = orderOf(orderId);
        assertEquals("449.10", orderOf(orderId).getFinalPrice().toPlainString());
    }

    @Disabled()
    public void userCannotRevokePaidOrder() {
        // given
        // when

        // then

    }

    @Disabled()
    public void userCannotRevokeShippedOrder() {

    }

    @Disabled()
    public void userCannotOrderNoExistingBooks() {

    }

    @Disabled()
    public void userCannotOrderNegativeNumberOfBooks() {

    }

    @Test
    public void userCannotRevokeOtherUsersOrder() {
        // given
        Book effectiveJava = givenEffectiveJava(50L);
        String adam = "adam@example.org";
        Long orderId = placedOrder(effectiveJava.getId(), 15, adam);
        assertEquals(35L, availableCopiesOf(effectiveJava));

        // when
        UpdateStatusCommand command = new UpdateStatusCommand(orderId, OrderStatus.CANCELED, user("marek@example.org"));
        service.updateOrderStatus(command);

        // then
        assertEquals(35L, availableCopiesOf(effectiveJava));
        assertEquals(OrderStatus.NEW, queryOrderService.findById(orderId).get().getStatus());
    }

    @Test
    public void adminCannotRevokeOtherUsersOrder() {
        // given
        Book effectiveJava = givenEffectiveJava(50L);
        String marek = "marek@example.org";
        Long orderId = placedOrder(effectiveJava.getId(), 15, marek);
        assertEquals(35L, availableCopiesOf(effectiveJava));

        // when
        UpdateStatusCommand command = new UpdateStatusCommand(orderId, OrderStatus.CANCELED, adminUser());
        service.updateOrderStatus(command);

        // then
        assertEquals(50L, availableCopiesOf(effectiveJava));
        assertEquals(OrderStatus.CANCELED, queryOrderService.findById(orderId).get().getStatus());
    }

    @Test
    public void adminCanMarkOrderAsPaid() {
        // given
        Book effectiveJava = givenEffectiveJava(50L);
        String recipient = "marek@example.org";
        Long orderId = placedOrder(effectiveJava.getId(), 15, recipient);
        assertEquals(35L, availableCopiesOf(effectiveJava));

        // when
        String admin = "admin@example.org";

        UpdateStatusCommand command = new UpdateStatusCommand(orderId, OrderStatus.PAID, adminUser());
        service.updateOrderStatus(command);

        // then
        assertEquals(35L, availableCopiesOf(effectiveJava));
        assertEquals(OrderStatus.PAID, queryOrderService.findById(orderId).get().getStatus());

    }

    @Test
    public void userCantOrderMoreBooksThanAvailable() {
        // given
        Book effectiveJava = givenEffectiveJava(5L);
        PlaceOrderCommand command = PlaceOrderCommand
                .builder()
                .recipient(recipient())
                .item(new OrderItemCommand(effectiveJava.getId(), 10))
                .build();
        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.placeOrder(command);
        });

        // then
        assertTrue(exception.getMessage().contains("Too many copies of book " + effectiveJava.getId() + " requested"));
    }

    private User user(String email){
       return new User(email, "", List.of(new SimpleGrantedAuthority("ROLE_USER")));

    }

    private User adminUser(){
        return new User("admin", "", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

    }

    private Book givenBook(long available, String price) {
        return bookJpaRepository.save(new Book("Java Concurrency in Practice", 2006, new BigDecimal(price), available));
    }

    private RichOrder orderOf(Long orderId) {
        return queryOrderService.findById(orderId).get();
    }

    private Long availableCopiesOf(Book effectiveJava) {
        return catalogUseCase.findById(effectiveJava.getId()).get().getAvailable();
    }

    private Long placedOrder(Long bookId, int copies, String recipient) {
        PlaceOrderCommand command = PlaceOrderCommand
                .builder()
                .recipient(recipient(recipient))
                .item(new OrderItemCommand(bookId, copies))
                .delivery(Delivery.COURIER)
                .build();
        return service.placeOrder(command).getRight();
    }

    private Long placedOrder(Long bookId, int copies) {
        return placedOrder(bookId, copies, "john@example.org");
    }

    private Book givenJavaConcurrency(Long available) {
        return bookJpaRepository.save(
                new Book(
                        "Java Concurrency in Practice",
                        2006,
                        new BigDecimal(99.90),
                        available));
    }

    private Book givenEffectiveJava(Long available) {
        return bookJpaRepository.save(
                new Book(
                        "Effective Java",
                        2005,
                        new BigDecimal(199.90),
                        available));
    }

    private Recipient recipient() {
        return Recipient.builder().email("john@example.org").build();
    }

    private Recipient recipient(String email) {
        return Recipient.builder().email(email).build();
    }

}
