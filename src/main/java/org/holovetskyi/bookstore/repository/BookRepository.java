package org.holovetskyi.bookstore.repository;

import lombok.Builder;
import org.holovetskyi.bookstore.domany.Book;
import org.holovetskyi.bookstore.additional.classes.GenerateId;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BookRepository {

    Map<Integer, Book> bookList = new HashMap();

    public void createList(String bookTitle, String author, String edition, int numberOfPages) {
        Book book = new Book(bookTitle, author, edition, numberOfPages);
        book.setIdBook(GenerateId.getNewId(bookList.keySet()));
        bookList.put(book.getIdBook(), book);
    }

    public Collection<Book> allBook() {
        return bookList.values();
    }

    public Collection<Integer> allIdBook(){
        return bookList.keySet();
    }

    @PostConstruct
    public void build() {
        createList(new Book("Java Basis", "Cay S. Horstmann", "Helion", 741));
        createList(new Book("Java Advanced techniques", "Cay S. Horstmann", "Helion", 808));
        createList(new Book("Clean code", "Robert C. Martin", "Helion", 423));
        createList(new Book("Java Pierwsze kroki", "Michał Makaruk", "Akademia kodu", 187));
    }

    public void createList(Book book) {
        book.setIdBook(GenerateId.getNewId(bookList.keySet()));
        bookList.put(book.getIdBook(), book);
    }
}
