package com.skytrail.model;

import com.skytrail.enums.Gender;

/**
 * Holds personal details for a single passenger in a booking.
 */
public class Passenger {

    private final String name;
    private final String contact;
    private final String idNumber;
    private final int age;
    private final Gender gender;

    public Passenger(String name, String contact, String idNumber, int age, Gender gender) {
        this.name     = name;
        this.contact  = contact;
        this.idNumber = idNumber;
        this.age      = age;
        this.gender   = gender;
    }

    public String getName()     { return name; }
    public String getContact()  { return contact; }
    public String getIdNumber() { return idNumber; }
    public int getAge()         { return age; }
    public Gender getGender()   { return gender; }

    @Override
    public String toString() {
        return "Name: " + name + ", Contact: " + contact
                + ", ID: " + idNumber + ", Age: " + age + ", Gender: " + gender;
    }
}
