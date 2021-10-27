package com.volodymyr.bookaro.catalog.domain;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

public interface CatalogRepository {

    List<Book> findAll();

    void save(Book book);

    Optional<Book> findById(Long id);

    void removeById(Long id);
}
