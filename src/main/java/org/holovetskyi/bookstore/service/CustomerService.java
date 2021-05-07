package org.holovetskyi.bookstore.service;

import org.holovetskyi.bookstore.domany.Customer;
import org.holovetskyi.bookstore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {


    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomer() {
        return new ArrayList<>(customerRepository.getAllCustomer());
    }

    public void saveCustomer(Customer customer) {
        customerRepository.createCustomer(customer);

    }

    public Customer getCustomerId(Integer id) {
        return customerRepository.getIdCustomer(id);
    }

    public void deleteCustomer(Integer id) {
        customerRepository.deleteCustomer(id);

    }
}
