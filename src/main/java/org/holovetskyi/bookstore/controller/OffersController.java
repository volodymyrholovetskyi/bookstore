package org.holovetskyi.bookstore.controller;

import org.holovetskyi.bookstore.domany.Customer;
import org.holovetskyi.bookstore.service.BookService;
import org.holovetskyi.bookstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class OffersController {

    private BookService ordersService;
    private CustomerService customerService;

    @Autowired
    public OffersController(BookService bookService, CustomerService customerService) {
        this.ordersService = bookService;
        this.customerService = customerService;
    }

    @RequestMapping("/offers")
    public String getOffers() {
        return "offers";
    }
}
