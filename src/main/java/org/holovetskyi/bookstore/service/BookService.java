package org.holovetskyi.bookstore.service;

import org.holovetskyi.bookstore.domany.Book;
import org.holovetskyi.bookstore.repository.BookRepository;
import org.holovetskyi.bookstore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
public class BookService {

    public BookRepository bookRepository;

    public CustomerRepository customerRepository;

    @Autowired
    public BookService(BookRepository bookRepository, CustomerRepository customerRepository) {
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
    }

//    public void orderAmount(String diameterCable, double cableLength, String name) {
//        cableRepository.AllCable().stream().filter();
//
//    }

    public Collection<Book> getAllBook() {
        Collection<Book> books = bookRepository.allBook();
        return books;
    }

    public void saveBook(Book book) {
        bookRepository.createList(book);
    }

//    public Integer randomIdBook() {
//        Random random = new Random();
//        Collection<Book> books = bookRepository.allBook();
//        List<Integer> collect = new ArrayList<>(integers);
//        //TODO
//        Integer integer1 = collect.get(random.nextInt(collect.size()));
//        return integer1;
//    }

    public void getByBookId(int idBook) {


    }
}

