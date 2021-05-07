package org.holovetskyi.bookstore.szkolenia;

import java.util.ArrayList;
import java.util.List;

public class RepositoryManager {


    List<Manager> managerList = new ArrayList<>();

    public List<Manager> createManager(String firstName, String lastName, int age) {
        managerList.add(new Manager(firstName, lastName, age));
        return managerList;
    }

    public Manager getManager(int index) {
        Manager manager = managerList.get(index);
        return manager;
    }

    public List<Manager> getAllManager(){
        return managerList;
    }

    @Override
    public String toString() {
        return "RepositoryManager{" +
                "managerList=" + managerList +
                '}';
    }
}
