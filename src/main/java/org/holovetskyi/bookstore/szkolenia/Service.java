package org.holovetskyi.bookstore.szkolenia;

import java.util.stream.Collectors;

public class Service {

    public RepositoryManager repositoryManager;

    public Service(RepositoryManager repositoryManager) {
        this.repositoryManager = repositoryManager;
    }


    public void saveManager(String firstName, String lastName, int age) {
        repositoryManager.createManager(firstName, lastName, age);
    }


    public Manager getManager(int index) {
        return repositoryManager.getManager(index);
    }


    public String getFirstName() {

        String volodymyr =
                repositoryManager.getAllManager().stream().filter(manager -> manager.getFirstName().equals("Volodymyr")).map(manager -> manager.getFirstName()).collect(Collectors.joining());
        return volodymyr;

    }
}
