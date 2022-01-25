package com.example.ccsys.webControllers;

import com.example.ccsys.ds.Course;
import com.example.ccsys.ds.User;
import com.example.ccsys.utils.DbQuerys;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserWebController {

    @PostMapping(value = "/authenticate")
    public User validateUser(@RequestBody Map<String, String> properties) throws SQLException {
        String login = properties.get("login");
        String password = properties.get("password");
        return DbQuerys.validateLogin(login, password);
    }

    @GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() throws SQLException {
        return DbQuerys.getUsers();
    }
}
