package com.foxminded.sql;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Application {

    public static void main(String[] args) throws IOException, SQLException {

        TestDataGenerator gen = new TestDataGenerator();
        DBFactory factory = new DBFactory();
        TestData data = gen.getTestData();

//        factory.createDataBase();
//        factory.fillDataBase(data);
//        factory.createRelationsBetweenStudentsAndCourses();

        factory.separateStudentsByCourses(200,10);
    }
}
