package com.volodymyr.bookaro.catalog.application.port;

import com.volodymyr.bookaro.catalog.domain.Author;

import java.util.List;

public interface AuthorsUseCase {

    List<Author> findAll();
}
