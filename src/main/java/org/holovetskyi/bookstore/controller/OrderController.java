package org.holovetskyi.bookstore.controller;

import org.holovetskyi.bookstore.domany.Customer;
import org.holovetskyi.bookstore.service.BookService;
import org.holovetskyi.bookstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;


@Controller
public class OrderController {

    private CustomerService customerService;
    private BookService bookService;

    @Autowired
    public OrderController(CustomerService customerService, BookService bookService) {
        this.customerService = customerService;
        this.bookService = bookService;
    }


    @RequestMapping(value = "/new/order", method = RequestMethod.GET)
    public String createOrder(Model model) {
        model.addAttribute("customer", new Customer());
        return "new_order";
    }

    @RequestMapping("/order/details")
    public String getOrder(@RequestParam("id") Integer id, Model model) {
        Customer customer = customerService.getCustomerId(id);
        model.addAttribute("customer", customer);
        return "order_details";
    }

    @RequestMapping(value = "/new/order", method = RequestMethod.POST)
    public String saveCustomer(@Valid Customer customer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("The field cannot be left blank");
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error.getObjectName() + " " + error.getDefaultMessage());
            });
            return "new_order";
        } else {
            customerService.saveCustomer(customer);
            System.out.println("Congratulej");
            return "redirect:/book/order";
        }
    }

    @RequestMapping("/order/list")
    public String getCustomer(Model model) {
        List<Customer> allCustomers = customerService.getAllCustomer();
//        Integer numberRandomId = bookService.randomIdBook();
        model.addAttribute("customers", allCustomers);
//        model.addAttribute("numberRandomBookId", numberRandomId);
        return "order_list";
    }

    @RequestMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable("id") Integer id) {
        customerService.deleteCustomer(id);
        return "redirect:/order/list";

    }
}
