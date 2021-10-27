package com.volodymyr.bookaro.order.application.port;

import com.volodymyr.bookaro.order.domain.Order;

import java.util.List;

public interface QueryOrderUseCase {

    List<Order> findAll();
}
