package com.example.ccsys.controllers;

import com.example.ccsys.Start;
import com.example.ccsys.ds.User;
import com.example.ccsys.utils.DbQuerys;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SignUpControl {
    @FXML
    public TextField loginName;
    @FXML
    public PasswordField password;
    @FXML
    public TextField name;
    @FXML
    public TextField surname;
    @FXML
    public TextField email;
    @FXML
    public RadioButton radioLecturer;
    @FXML
    public RadioButton radioStudent;

    private Connection connection;
    private PreparedStatement preparedStatement;

    public void startSignUp(ActionEvent actionEvent) throws IOException {
        if (this.isValidInput(this.getLoginName()) && this.isValidInput(this.getPassword()) && this.isValidInput(this.getName()) && this.isValidInput(this.getSurname())
                && this.isValidInput(this.getEmail()) && this.isValidInput(this.getUserType())) {
            DbQuerys.createUser(new User(this.getLoginName(), this.getPassword(), this.getUserType(), this.getName(), this.getSurname(), this.getEmail()));
            this.returnToPrevious();
        }
    }

    private void returnToPrevious() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("login-window.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) loginName.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public String getUserType() {
        if (radioLecturer.isSelected())
            return "Lecturer";
        else if (radioStudent.isSelected())
            return "Student";
        LoginControl.alertMessage("You must select your status");
        return "";
    }

    public String getLoginName() {
        String loginName = this.loginName.getText();
        if (loginName.length() > 0)
            return loginName;
        LoginControl.alertMessage("You must enter login name");
        return "";
    }

    public String getPassword() {
        String password = this.password.getText();
        if (password.length() > 0)
            return password;
        LoginControl.alertMessage("You must enter password");
        return "";
    }

    public String getName() {
        String name = this.name.getText();
        if (name.length() > 0)
            return name;
        LoginControl.alertMessage("You must enter name");
        return "";
    }

    public String getSurname() {
        String surname = this.surname.getText();
        if (surname.length() > 0)
            return surname;
        LoginControl.alertMessage("You must enter last name");
        return "";
    }

    public String getEmail() {
        String email = this.email.getText();
        if (email.length() > 0)
            return email;
        LoginControl.alertMessage("You must enter email");
        return "";
    }

    private boolean isValidInput(String input) {
        if (input.length() == 0)
            return false;
        return true;
    }
}
