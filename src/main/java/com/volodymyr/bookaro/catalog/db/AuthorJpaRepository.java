package com.volodymyr.bookaro.catalog.db;

import com.volodymyr.bookaro.catalog.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorJpaRepository extends JpaRepository<Author, Long> {
}
