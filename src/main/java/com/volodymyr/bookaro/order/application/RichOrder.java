package com.volodymyr.bookaro.order.application;

import com.volodymyr.bookaro.order.application.price.OrderPrice;
import com.volodymyr.bookaro.order.domain.OrderItem;
import com.volodymyr.bookaro.order.domain.OrderStatus;
import com.volodymyr.bookaro.order.domain.Recipient;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Value
public
class RichOrder {

    Long id;
    OrderStatus status;
    Set<OrderItem> items;
    Recipient recipient;
    LocalDateTime createAt;
    OrderPrice orderPrice;
    BigDecimal finalPrice;

}
