package org.holovetskyi.bookstore.additional.classes;

import java.util.Set;

public class GenerateId {

    public static int getNewId(Set<Integer> keysSoFar) {

        if (keysSoFar.isEmpty()) {
            System.out.println("Null");
            return 1;
        } else {
            Integer integer = keysSoFar.stream().max((o1, o2) -> o1.compareTo(o2)).get();
            System.out.println("integer: " + integer);
            return integer + 1;
        }
    }
}