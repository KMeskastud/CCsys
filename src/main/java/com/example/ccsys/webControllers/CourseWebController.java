package com.example.ccsys.webControllers;

import com.example.ccsys.ds.Course;
import com.example.ccsys.ds.User;
import com.example.ccsys.utils.DbQuerys;
import com.example.ccsys.utils.DbUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/course", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseWebController {

    @GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Course> getAllCourses() throws SQLException {
        return DbQuerys.getAllCourses();
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Course> getCourses (@RequestParam("courseId") int courseId) throws SQLException {
        return DbQuerys.getCourses(courseId);
    }

    @DeleteMapping(value = "/delete")
    public String deleteCourse (@RequestParam("courseId") int courseId) throws SQLException {
        DbQuerys.deleteCourse(courseId);
        return "Success";
    }

    @PostMapping(value = "/create")
    public String createCourse (@RequestParam("userId") int userId, @RequestBody Map<String, String> properties) throws SQLException {
        User user;
        user = DbQuerys.getUser(userId);
        String name = properties.get("name");
        String description = properties.get("description");
        Course course = new Course (name, description);
        DbQuerys.createCourse(course, user);
        return "Success";
    }

    @PutMapping(value = "/update")
    public String updateCourse (@RequestBody Map<String, String> properties, @RequestParam("courseId") int courseId) throws SQLException {
        String name = properties.get("name");
        String description = properties.get("description");
        Course course = new Course(name, description);
        DbQuerys.editCourse(course, courseId);
        return "Success";
    }
}
