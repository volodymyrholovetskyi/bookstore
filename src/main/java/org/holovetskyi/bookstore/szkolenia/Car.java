package org.holovetskyi.bookstore.szkolenia;

public class Car {

    private String silnik = "benzyn";

    private int opony = 5;

    public Car(String silnik, int opony) {
        this.silnik = silnik;
        this.opony = opony;
    }

    public String getSilnik() {
        return silnik;
    }

    public void setSilnik(String silnik) {
        this.silnik = silnik;
    }

    public int getOpony() {
        return opony;
    }

    public void setOpony(int opony) {
        this.opony = opony;
    }
}
