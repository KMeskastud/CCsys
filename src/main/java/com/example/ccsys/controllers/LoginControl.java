package com.example.ccsys.controllers;

import com.example.ccsys.Start;
import com.example.ccsys.ds.User;
import com.example.ccsys.utils.DbUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginControl {

    @FXML
    public TextField loginName;
    @FXML
    public PasswordField password;

    private Connection connection;
    private Statement statement;

    public static void alertMessage(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(s);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public void ValidateLogin(ActionEvent actionEvent) throws SQLException, IOException{
        connection = DbUtils.connectToDb();
        statement = connection.createStatement();
        String query = "SELECT * FROM user WHERE login = '" + loginName.getText() + "' AND password = '" + password.getText() + "'";
        ResultSet rs = statement.executeQuery(query);
        String userName = "null";
        String userSurname = "null";
        String userPosition = "null";
        String email = "null";
        int id = 0;
        while (rs.next()) {
            id = rs.getInt(1);
            userName = rs.getString("person_name");
            userSurname = rs.getString("person_surname");
            userPosition = rs.getString("person_position");
            email = rs.getString("person_email");
        }
        DbUtils.disconnectFromDb(connection, statement);
        if (userPosition.equals("Student") && userName != null)
            alertMessage("Logged in as Student");
        else if (userPosition.equals("Lecturer") && userName != null)
            alertMessage("Logged in as Lecturer");

        if(userName != null){
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml"));
            Parent root = fxmlLoader.load();
            MainWindowControl mainWindowControl = fxmlLoader.getController();
            mainWindowControl.setLoggedInUser(new User(id, userName, userSurname, email, userPosition));
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.loginName.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
            alertMessage("Bad credentials");
    }

    public void startSignUp(ActionEvent actionEvent) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader((Start.class.getResource("sign-up-form.fxml")));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) loginName.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
