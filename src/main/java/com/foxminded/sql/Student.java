package com.foxminded.sql;

public class Student {

    private final String firstName;
    private final String lastName;
    private final int groupID;

    public Student(String firstName, String lastName, int groupID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupID = groupID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getGroupID() {
        return groupID;
    }
}
