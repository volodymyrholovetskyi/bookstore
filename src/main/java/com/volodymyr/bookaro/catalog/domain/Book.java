package com.volodymyr.bookaro.catalog.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Book {

    Long id;
    String title;
    String author;
    Integer year;
    BigDecimal price;

    public Book(String title, String author, Integer year, BigDecimal price) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.price = price;
    }
}
