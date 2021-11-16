package com.volodymyr.bookaro.catalog.application;

import com.volodymyr.bookaro.catalog.application.port.AuthorsUseCase;
import com.volodymyr.bookaro.catalog.db.AuthorJpaRepository;
import com.volodymyr.bookaro.catalog.domain.Author;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorsService implements AuthorsUseCase {

    private final AuthorJpaRepository authorRepository;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }
}
