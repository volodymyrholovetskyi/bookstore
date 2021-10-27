package com.volodymyr.bookaro.catalog.application;

import com.volodymyr.bookaro.catalog.application.port.CatalogUseCase;
import com.volodymyr.bookaro.catalog.domain.Book;
import com.volodymyr.bookaro.catalog.domain.CatalogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class CatalogService implements CatalogUseCase {

    private final CatalogRepository catalogRepository;

    @Override
    public List<Book> findAll() {
        return catalogRepository.findAll();
    }

    @Override
    public List<Book> findByTitle(String title) {
        return catalogRepository.findAll()
                .stream()
                .filter(book -> book.getTitle().startsWith(title))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findOneByTitle(String title) {
        return catalogRepository.findAll()
                .stream()
                .filter(book -> book.getTitle().startsWith(title))
                .findFirst();
    }

    @Override
    public Optional<Book> findOneTitleAndAuthor(String title, String author) {
        return catalogRepository.findAll()
                .stream()
                .filter(book -> book.getTitle().startsWith(title))
                .filter(book -> book.getAuthor().startsWith(author))
                .findFirst();
    }

    @Override
    public void addBook(CreateBookCommand command) {
        Book book = command.toBook();
        catalogRepository.save(book);
    }

    @Override
    public void removeById(Long id) {
catalogRepository.removeById(id);
    }

    @Override
    public UpdateBookResponse updateBook(UpdateBookCommand command) {
        return catalogRepository.
                findById(command.getId())
                .map(book -> {
                    Book updateBook = command.updateFields(book);
                    catalogRepository.save(updateBook);
                    return UpdateBookResponse.SUCCESS;
                }).orElseGet(() -> new UpdateBookResponse(false,
                        Collections.singletonList("Book not fond with id: " + command.getId())));
    }
}
