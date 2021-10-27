package com.volodymyr.bookaro.order.domain;

import com.volodymyr.bookaro.catalog.domain.Book;
import lombok.Value;

@Value
public class OrderItem {

    Book book;
    int quantity;
}
