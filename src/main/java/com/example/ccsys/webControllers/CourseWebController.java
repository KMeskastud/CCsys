package com.example.ccsys.webControllers;

import com.example.ccsys.ds.Course;
import com.example.ccsys.utils.DbUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseWebController {

    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;

    @GetMapping(value = "/course/allCourses", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Course> getAllCourses1() throws SQLException {
        ArrayList<Course> courses = new ArrayList<>();
        connection = DbUtils.connectToDb();
        statement = connection.createStatement();
        String query = "SELECT * FROM course";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            courses.add(new Course(rs.getInt(1), rs.getString("course_name"), rs.getString("description")));
        }
        DbUtils.disconnectFromDb(connection, statement);

        return courses;
    }
}
