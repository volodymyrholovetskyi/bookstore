package com.volodymyr.bookaro.catalog.web;

import com.volodymyr.bookaro.catalog.application.port.AuthorsUseCase;
import com.volodymyr.bookaro.catalog.domain.Author;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/authors")
public class AuthorsController {

    private final AuthorsUseCase authors;

    @GetMapping
    List<Author> findAll() {
      return authors.findAll();
    }
}
