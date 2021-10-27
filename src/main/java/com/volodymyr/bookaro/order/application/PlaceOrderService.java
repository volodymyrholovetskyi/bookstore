package com.volodymyr.bookaro.order.application;

import com.volodymyr.bookaro.order.application.port.PlaceOrderUseCase;
import com.volodymyr.bookaro.order.domain.Order;
import com.volodymyr.bookaro.order.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceOrderService implements PlaceOrderUseCase {

    private final OrderRepository repository;

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderCommand command) {
        Order newOrder = Order
                .builder()
                .recipient(command.getRecipient())
                .items(command.getItems())
                .build();
        Order order = repository.save(newOrder);
        return PlaceOrderResponse.success(order.getId());
    }
}
