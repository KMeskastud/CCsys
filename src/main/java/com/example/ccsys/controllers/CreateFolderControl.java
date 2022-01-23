package com.example.ccsys.controllers;

import com.example.ccsys.Start;
import com.example.ccsys.ds.Course;
import com.example.ccsys.ds.Folder;
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

public class CreateFolderControl {

    @FXML
    public TextField folderName;

    private User loggedInUser;
    private Course selectedCourse;
    private int selectedFolderId;

    public void setLoggedInUser(User user) throws SQLException {
        this.loggedInUser = user;
    }

    public void setSelectedCourse(Course course) throws SQLException {
        this.selectedCourse = course;
    }

    public void setSelectedFolderId(int folderId) throws SQLException {
        this.selectedFolderId = folderId;
    }

    public void createFolder(ActionEvent actionEvent) throws SQLException, IOException   {
        DbQuerys.createFolder(new Folder(this.selectedFolderId, this.selectedCourse.getId(), this.folderName.getText()));
        this.goBack();
    }

    public void goBack() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        MainWindowControl mainCoursesWindow = fxmlLoader.getController();
        mainCoursesWindow.setLoggedInUser(this.loggedInUser);

        Stage stage = (Stage) this.folderName.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
