package com.volodymyr.bookaro.order.application.price;

import com.volodymyr.bookaro.order.application.RichOrder;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class OrderPrice {
    BigDecimal itemsPrice;
    BigDecimal deliveryPrice;
    BigDecimal discounts;

    public BigDecimal finalPrice(){
        return itemsPrice.add(deliveryPrice).subtract(discounts);
    }
}
