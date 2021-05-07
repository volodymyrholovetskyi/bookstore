package org.holovetskyi.bookstore.repository;

import org.holovetskyi.bookstore.additional.classes.GenerateId;
import org.holovetskyi.bookstore.domany.Customer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Repository
public class CustomerRepository {


    Map<Integer, Customer> customers = new HashMap();

    public void createCustomer(String firstName, String lastName, String city, String email) {
        Customer newCustomer = new Customer(firstName, lastName, city, email);
        newCustomer.setIdCustomer(GenerateId.getNewId(customers.keySet()));
        customers.put(newCustomer.getIdCustomer(), newCustomer);
        System.out.println(newCustomer.getFirstName());
        System.out.println(newCustomer.getIdCustomer());
    }

    public Collection<Customer> getAllCustomer() {
        return customers.values();
    }

//    public Optional<Employee> getEmployee(String firstName) {
//        Optional<Employee> employeeByName =
//                employees.values().stream().filter(employee -> employee.getFirstName().equals(firstName)).findAny();
//        return employeeByName;
//    }

    public void createCustomer(Customer customer) {
        customer.setIdCustomer(GenerateId.getNewId(customers.keySet()));
        customers.put(customer.getIdCustomer(), customer);
    }

    public void deleteCustomer(Integer id) {
        customers.remove(id);
    }

    @PostConstruct
    public void build() {
        createCustomer("Mariusz", "Kowalski", "Lublin", "mariusz@gmail.com");
        createCustomer("Adam", "Kulgawec", "Wroclaw", "adam@gmail.com");

    }

    @Override
    public String toString() {
        return "EmployeeRepository{" +
                "customers=" + customers +
                '}';
    }


    public Customer getIdCustomer(Integer id) {
        return customers.get(id);
    }
}
