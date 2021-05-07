package org.holovetskyi.bookstore.controller;

import org.holovetskyi.bookstore.domany.Book;
import org.holovetskyi.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Collection;

@Controller
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/book/list")
    public String getBook(Model model) {
        Collection<Book> allBook = bookService.getAllBook();
        model.addAttribute("allBook", allBook);
        return "book_list";
    }

    @RequestMapping("/book/order")
    public String createOrderBook(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "book_order";
    }

    @RequestMapping(value = "/book/order", method = RequestMethod.POST)
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("The field cannot be left blank");
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error.getObjectName() + " " + error.getDefaultMessage());
            });
            return "book_order";
        } else {
            bookService.getByBookId(book.getIdBook());
            return "order_congratulation";
        }
    }
}


