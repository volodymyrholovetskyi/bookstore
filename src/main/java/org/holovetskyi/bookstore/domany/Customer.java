package org.holovetskyi.bookstore.domany;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Customer {

    private int idCustomer;

    @NotNull
    @Size(min = 2, max = 40, message = "imie musi miec mędzy, 2 a 40 znaków")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 40, message = "nazwisko musi miec mędzy, 2 a 40 znaków")
    private String lastName;

    @NotNull
    @Size(min = 2, max = 40, message = "nie wpisałeś miasto dostawy")
    private String city;

    @Email
    @NotNull
//    @Size(min = 2, max = 40, message = "podaj prawidłowy adres e-mail")
    private String email;


//    private Book book;

    public Customer() {

    }

    public Customer(String firstName, String lastName, String city, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.email = email;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "idCustomer=" + idCustomer +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
