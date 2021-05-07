package org.holovetskyi.bookstore.szkolenia;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RepositoryPerson {

    Map<Integer, Person> personMap = new HashMap<>();

    public void createPerson() {
        Person igor = new Person(23, "Igor");
        Person vova = new Person(25, "Volodymyr");
        igor.setId(getNewId(personMap.keySet()));
        vova.setId(getNewId(personMap.keySet()));
        personMap.put(igor.getId(), igor);
        personMap.put(vova.getId(), vova);
        for (Map.Entry<Integer, Person> map : personMap.entrySet()) {
            System.out.println(map);
        }
    }

    public static int getNewId(Set<Integer> keysSoFar) {

        if (keysSoFar.isEmpty()) {
            return 0;
        } else {
            Integer integer = keysSoFar.stream().max((o1, o2) -> o1.compareTo(o2)).get();
            return integer + 1;
        }
    }
}
