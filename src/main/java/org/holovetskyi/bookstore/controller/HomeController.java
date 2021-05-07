package org.holovetskyi.bookstore.controller;

import org.holovetskyi.bookstore.service.BookService;
import org.holovetskyi.bookstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private BookService ordersService;
    private CustomerService customerService;


    @Autowired
    public HomeController(BookService bookService, CustomerService customerService) {
        this.ordersService = bookService;
        this.customerService = customerService;
    }

    @RequestMapping("/home/page")
    public String getCable() {
        return "home_page";
    }

}

