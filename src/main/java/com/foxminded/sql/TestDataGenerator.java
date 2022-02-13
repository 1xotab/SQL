package com.foxminded.sql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TestDataGenerator {

    public TestData getTestData() throws IOException {

        final String pathToNames = "names.txt";
        final String pathToLastNames = "lastNames.txt";
        final String pathToCourses = "courses.txt";
        final int STUDENTS_QUANTITY = 200;
        final int GROUPS_QUANTITY = 10;

        List<Student> students = createStudents(pathToNames, pathToLastNames, STUDENTS_QUANTITY, GROUPS_QUANTITY);
        List<String> courses = getListFromFile(pathToCourses);
        Set<String> groups = createGroups(GROUPS_QUANTITY);

        return new TestData(students, courses, groups);
    }

    private Set<String> createGroups(int quantity) {

        Set<String> result = new HashSet<>();
        Random random = new Random();

        while (result.size() < quantity) {

            int randomNumber = random.nextInt(10, 99);
            char firstLetter = (char) random.nextInt(65, 90);
            char secondLetter = (char) random.nextInt(65, 90);

            result.add(firstLetter + "" + secondLetter + "-" + randomNumber);
        }
        return result;
    }

    private List<Student> createStudents(String pathToNames, String pathToLastNames, int studentsQuantity, int groupsQuantity) throws IOException {

        List<String> names = createRandomListFromFile(pathToNames, studentsQuantity);
        List<String> lastNames = createRandomListFromFile(pathToLastNames, studentsQuantity);
        List<Integer> groupIDs = separateStudentsByGroup(studentsQuantity, groupsQuantity);

        List<Student> studentsList = new ArrayList<>();

        for (int i = 0; i < studentsQuantity; i++) {

            String name = names.get(i);
            String lastName = lastNames.get(i);
            int groupID = groupIDs.get(i);

            studentsList.add(new Student(name, lastName, groupID));
        }
        return studentsList;
    }

    private List<String> createRandomListFromFile(String pathToFile, int quantity) throws IOException {

        List<String> list = getListFromFile(pathToFile);
        List<String> result = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < quantity; i++) {

            int numberOfStringFromList = random.nextInt(list.size());
            String randomString = list.get(numberOfStringFromList);

            result.add(randomString);
        }
        return result;
    }

    private List<String> getListFromFile(String pathToFile) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(pathToFile);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        List<String> result = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null) {
            result.add(line);
        }
        return result;
    }

    private List<Integer> separateStudentsByGroup(int quantityOfStudents, int quantityOfGroups) {

        List<Integer> result = new ArrayList<>();
        Random random = new Random();

        int studentsWithoutGroup = quantityOfStudents;
        int groupID = 1;

        while (groupID <= quantityOfGroups) {

            int personsInGroup = random.nextInt(10, 30);
            addSameNumberInList(result, groupID, personsInGroup);

            studentsWithoutGroup = studentsWithoutGroup - personsInGroup;
            groupID++;

            if (studentsWithoutGroup < 30 && groupID < 11) {

                addSameNumberInList(result, groupID, studentsWithoutGroup);
                break;
            }
        }
        if (studentsWithoutGroup != 0) {

            for (int i = result.size(); i < 201; i++) {
                result.add(0);
            }
        }
        return result;
    }

    private void addSameNumberInList(List<Integer> list, int number, int quantityOfRepetition) {

        for (int i = 0; i < quantityOfRepetition; i++) {
            list.add(number);
        }
    }
}

