package com.volodymyr.bookaro.order.application;

import com.volodymyr.bookaro.order.application.port.QueryOrderUseCase;
import com.volodymyr.bookaro.order.domain.Order;
import com.volodymyr.bookaro.order.domain.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QueryOrderService implements QueryOrderUseCase {

    private final OrderRepository repository;

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }
}
