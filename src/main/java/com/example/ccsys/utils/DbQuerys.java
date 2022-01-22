package com.example.ccsys.utils;

import com.example.ccsys.controllers.LoginControl;
import com.example.ccsys.ds.Course;
import com.example.ccsys.ds.File;
import com.example.ccsys.ds.Folder;
import com.example.ccsys.ds.User;

import java.sql.*;
import java.util.ArrayList;

public class DbQuerys {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;

    public static void createUser(User user) {
        try {
            connection = DbUtils.connectToDb();
            String insertString = "INSERT INTO user(`login`, `password`, `person_name`, `person_surname`, `person_position`, `person_email`) VALUES (?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            preparedStatement.setString(5, user.getPosition());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.execute();
            DbUtils.disconnectFromDb(connection, preparedStatement);
            LoginControl.alertMessage("User created");
        } catch (Exception e) {
            LoginControl.alertMessage("User not created" + e);
        }
    }

    public static ArrayList<Course> getCourses(int userId) throws SQLException {
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Integer> coursesId = new ArrayList<>();

        connection = DbUtils.connectToDb();
        statement = connection.createStatement();

        String query1 = "SELECT course_id user FROM course_access WHERE user_id LIKE '%" + userId + "%'";
        ResultSet rs = statement.executeQuery(query1);
        while (rs.next()) {
            coursesId.add(rs.getInt(1));
        }
        for (int i = 0; i < coursesId.size(); i++)
        {
            String query = "SELECT * FROM course WHERE id LIKE '%" + coursesId.get(i) + "%'";
            ResultSet rs1 = statement.executeQuery(query);
            while (rs1.next()) {
                courses.add(new Course(rs1.getInt(1), rs1.getString("course_name"), rs1.getString("description")));
            }

        }
        DbUtils.disconnectFromDb(connection, statement);
        return courses;
    }

    public static ArrayList<Folder> getFolders(int courseId) throws SQLException {
        ArrayList<Folder> folders = new ArrayList<>();

        connection = DbUtils.connectToDb();
        statement = connection.createStatement();

        //String query1 = "SELECT * FROM folder WHERE course_id LIKE '%" + courseId + "%' AND parent_id LIKE '%" + "none" + "%'";
        String query1 = "SELECT * FROM folder WHERE course_id LIKE '%" + courseId + "%'";
        ResultSet rs1 = statement.executeQuery(query1);
        while (rs1.next()) {
            folders.add(new Folder(rs1.getInt(1), rs1.getInt("parent_id"), rs1.getInt("course_id"), rs1.getString("folder_name")));
        }
        DbUtils.disconnectFromDb(connection, statement);
        return folders;
    }

    public static ArrayList<File> getFiles(int folderId) throws SQLException {
        ArrayList<File> files = new ArrayList<>();

        connection = DbUtils.connectToDb();
        statement = connection.createStatement();

        String query1 = "SELECT * FROM file WHERE folder_id LIKE '%" + folderId + "%'";
        ResultSet rs1 = statement.executeQuery(query1);
        while (rs1.next()) {
            files.add(new File(rs1.getInt(1), rs1.getString("file_name"), rs1.getInt("folder_id")));
        }
        DbUtils.disconnectFromDb(connection, statement);
        return files;
    }
}

