package com.foxminded.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringJoiner;

public class DBFactory {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "1234";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Test";

    public void createDataBase() throws SQLException {

        String createGroupTable = "create table if not exists grps(group_id SERIAL primary key, group_name VARCHAR(5));";
        String createCoursesTable = "create table if not exists courses " +
                "(course_id SERIAL primary key, course_name VARCHAR(20), course_description VARCHAR(20));";
        String createStudentsTable = "create table if not exists students" +
                " (student_id serial primary key, group_id INT,first_name VARCHAR(20),last_name VARCHAR(20)," +
                "FOREIGN KEY (group_id)  REFERENCES grps (group_id));";


        String delete = "delete from grps; delete from students; delete from courses";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            Statement statement = connection.createStatement();

            statement.execute(createGroupTable + createStudentsTable + createCoursesTable);
            statement.execute(delete);
            statement.execute("alter sequence grps_group_id_seq restart; alter sequence courses_course_id_seq restart;" +
                    "alter sequence students_student_id_seq restart ");
        }
    }

    public void fillDataBase(TestData data) throws SQLException {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

            String groupsFillingTask = fillGroups(data.getGroups());
            String coursesFillingTask = fillCourses(data.getCourses());
            String studentsFillingTask = fillStudents(data.getStudents());

            Statement statement = connection.createStatement();
            statement.executeUpdate(groupsFillingTask);
            statement.executeUpdate(coursesFillingTask);
            statement.executeUpdate(studentsFillingTask);
        }
    }

    public void createRelationsBetweenStudentsAndCourses() throws SQLException {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

            String task = "create table if not exists students_courses(student_course_id SERIAL primary key,student_id INT,course_id INT," +
                    "FOREIGN KEY (student_id)  REFERENCES students (student_id)," +
                    "FOREIGN KEY (course_id)  REFERENCES courses (course_id) )";

            Statement statement = connection.createStatement();
            statement.execute(task);
        }


    }

    public void separateStudentsByCourses(int studentsQuantity, int courseQuantity) throws SQLException {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

           String tableDropTask = "delete from students_courses";
           String restartCounterTask = "alter sequence students_courses_student_course_id_seq restart";

            connection.createStatement().execute(tableDropTask);
            connection.createStatement().execute(restartCounterTask);

            for (int studentID = 1; studentID < studentsQuantity + 1; studentID++) {

                Random random = new Random();
                int quantityOfCourses = random.nextInt(1, 3);
                Set<Integer> coursesIDs = uniqueNumberGenerator(quantityOfCourses,1,courseQuantity);

                for(Integer courseID : coursesIDs){

                    String task = "insert into students_courses(student_id,course_id)" +
                            "values(" + studentID +"," + courseID + ")";

                    Statement statement = connection.createStatement();
                    statement.execute(task);
                }
            }
        }

    }

    private String fillGroups(Set<String> groupsNames) {
        StringJoiner buffer = new StringJoiner(",");

        for (String group_name : groupsNames) {
            buffer.add("('" + group_name + "')");
        }
        String names = buffer.toString();
        return "insert into grps (group_name) values" + names + ";";
    }

    private String fillCourses(List<String> coursesList) {
        StringJoiner buffer = new StringJoiner(",");

        for (String course_name : coursesList) {
            buffer.add("('" + course_name + "')");
        }
        String courseNames = buffer.toString();
        return "insert into courses (course_name) values" + courseNames + ";";
    }

    private String fillStudents(List<Student> listOfStudents) {
        StringJoiner buffer = new StringJoiner(",");

        for (Student student : listOfStudents) {

            String studentName = student.getFirstName();
            String studentLastName = student.getLastName();
            int studentGroupID = student.getGroupID();

            buffer.add("('" + studentName + "','" + studentLastName + "'," + studentGroupID + ")");
        }
        String personalData = buffer.toString();
        return "insert into students (first_name,last_name,group_id) values" + personalData + ";";
    }

    private Set<Integer> uniqueNumberGenerator(int quantityOfNumbers,int origin,int bound){

        Set<Integer> list = new HashSet<>();
        Random random = new Random();

        while (list.size() <= quantityOfNumbers){
            list.add(random.nextInt(origin,bound));
        }
        return list;
    }
}
