package com.foxminded.sql;

import java.util.List;
import java.util.Set;

public class TestData {

    private final List<Student> students;
    private final List<String> courses;
    private final Set<String> groups;

    public TestData(List<Student> students, List<String> courses, Set<String> groups) {
        this.students = students;
        this.courses = courses;
        this.groups = groups;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<String> getCourses() {
        return courses;
    }

    public Set<String> getGroups() {
        return groups;
    }
}