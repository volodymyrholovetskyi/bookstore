package com.volodymyr.bookaro.order.application.price;

import com.volodymyr.bookaro.order.domain.Order;

import java.math.BigDecimal;

public interface DiscountStrategy {

    BigDecimal calculate(Order order);
}
