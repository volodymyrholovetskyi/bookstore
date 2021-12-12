package com.volodymyr.bookaro.order.application.port;

import com.volodymyr.bookaro.commons.Either;
import com.volodymyr.bookaro.order.domain.Delivery;
import com.volodymyr.bookaro.order.domain.OrderItem;
import com.volodymyr.bookaro.order.domain.OrderStatus;
import com.volodymyr.bookaro.order.domain.Recipient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Singular;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ManipulateOrderUseCase {

    PlaceOrderResponse placeOrder(PlaceOrderCommand command);

    void deleteOrderById(Long id);

    UpdateStatusResponse updateOrderStatus(UpdateStatusCommand command);

    @Builder
    @Value
    @AllArgsConstructor
    class PlaceOrderCommand {
        @Singular
        List<OrderItemCommand> items;
        Recipient recipient;
        @Builder.Default
        Delivery delivery = Delivery.COURIER;
    }

    @Value
     class OrderItemCommand {
        Long bookId;
        int quantity;
    }

    @Value
    class UpdateStatusCommand{
        Long orderId;
        OrderStatus status;
        UserDetails user;
    }


    class PlaceOrderResponse extends Either<String, Long> {
        public PlaceOrderResponse(boolean success, String left, Long right) {
            super(success, left, right);
        }

        public static PlaceOrderResponse success(Long orderId) {
            return new PlaceOrderResponse(true, null, orderId);
        }

        public static PlaceOrderResponse failure(String error) {
            return new PlaceOrderResponse(false, error, null);

        }
    }

    class UpdateStatusResponse extends Either<Error, OrderStatus> {
        public UpdateStatusResponse(boolean success, Error left, OrderStatus right) {
            super(success, left, right);
        }

        public static UpdateStatusResponse success(OrderStatus status) {
            return new UpdateStatusResponse(true, null, status);
        }

        public static UpdateStatusResponse failure(Error error) {
            return new UpdateStatusResponse(false, error, null);

        }
    }

    @AllArgsConstructor
    @Getter
    enum Error{
        NOT_FOUND(HttpStatus.NOT_FOUND),
        FORBIDDEN(HttpStatus.FORBIDDEN);

        private final HttpStatus status;
    }
}
