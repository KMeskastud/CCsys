package com.example.ccsys.controllers;

import com.example.ccsys.Start;
import com.example.ccsys.ds.Course;
import com.example.ccsys.ds.User;
import com.example.ccsys.utils.DbQuerys;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EditCourseControl {

    @FXML
    public TextField courseName;
    @FXML
    public TextField courseDescription;

    private User loggedInUser;
    private Course selectedCourse;

    public void setLoggedInUser(User user) throws SQLException {
        this.loggedInUser = user;
    }

    public void setSelectedCourse(Course course) throws SQLException {
        this.selectedCourse = course;
    }

    public void createCourse(ActionEvent actionEvent) throws IOException, SQLException {
        DbQuerys.editCourse(new Course(this.courseName.getText(), this.courseDescription.getText()), selectedCourse.getId());
        this.goBack();
    }

    public void goBack() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        MainWindowControl mainCoursesWindow = fxmlLoader.getController();
        mainCoursesWindow.setLoggedInUser(this.loggedInUser);

        Stage stage = (Stage) this.courseName.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
