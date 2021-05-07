package org.holovetskyi.bookstore.szkolenia;


import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

       RepositoryManager repositoryManager = new RepositoryManager();

       RepositoryPerson repositoryPerson = new RepositoryPerson();

       Service service = new Service(repositoryManager);


//        Scanner scanner = new Scanner(System.in);
//        System.out.printf("Podaj imie: ");
//        String firstName = scanner.nextLine();
//        System.out.printf("Podaj nazwisko: ");
//        String lastName = scanner.nextLine();
//        System.out.printf("Podaj wyk: ");
//        int age = scanner.nextInt();
//        service.saveManager(firstName, lastName, age);
//
//        Manager manager = service.getManager(0);
//
//        System.out.println(manager);
        Person igor = new Person(23, "Igor");
        Person vova = new Person(25, "Volodymyr");

        String firstName1 = service.getFirstName();
        System.out.println(firstName1);

        repositoryPerson.createPerson();

        Car car = new Car("benzyn", 4);
        System.out.println(car.getOpony());

    }
}

