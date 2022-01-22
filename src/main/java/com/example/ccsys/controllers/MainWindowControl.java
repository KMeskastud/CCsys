package com.example.ccsys.controllers;

import com.example.ccsys.ds.Course;
import com.example.ccsys.ds.File;
import com.example.ccsys.ds.Folder;
import com.example.ccsys.ds.User;
import com.example.ccsys.utils.DbQuerys;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainWindowControl{

    @FXML
    public ListView coursesList;
    @FXML
    public ListView filesList;
    @FXML
    public TreeView<String> foldersTree;

    private Connection connection;
    private Statement statement;

    private ArrayList<Course> courses;
    private ArrayList<Folder> folders;
    private ArrayList<File> files;

    private User loggedInUser;
    private Course selectedCourse;
    private Folder selectedFolder;


    public void setLoggedInUser(User user) throws SQLException {
        this.loggedInUser = user;
        this.setCoursesList(loggedInUser.getId());
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    //Courses//
////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setCoursesList(int userIdLike) throws SQLException{
        this.coursesList.getItems().clear();
        for (Course course : this.getCourses(userIdLike)) {
            this.coursesList.getItems().add(course.getName());
        }
    }

    private ArrayList<Course> getCourses(int userIdLike) throws SQLException {
        ArrayList<Course> courses = DbQuerys.getCourses(userIdLike);
        this.courses = courses;
        return courses;
    }

    public void selectCourse(MouseEvent mouseEvent) throws SQLException{
        if (this.coursesList.getSelectionModel().getSelectedItem() != null) {
            String courseName = this.coursesList.getSelectionModel().getSelectedItem().toString();
            for (Course course : this.courses) {
                if(course.getName().equals(courseName))
                    selectedCourse = course;
            }
            System.out.println(selectedCourse.getName());//temp
            this.setFoldersTree();
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    //FOLDERS//
////////////////////////////////////////////////////////////////////////////////////////////////////
    private ArrayList<Folder> getFolders(int courseIdlike) throws SQLException {
        ArrayList<Folder> folders = DbQuerys.getFolders(courseIdlike);
        this.folders = folders;
        return folders;
    }

    public void setFoldersTree() throws  SQLException {
        TreeItem<String> rootItem = new TreeItem<>(selectedCourse.getName());
        this.foldersTree.setRoot(rootItem);
        getFolders(selectedCourse.getId());


        for (Folder folder : getFolders(selectedCourse.getId())) {
            if(folder.getParentId() == 0)
            {
                TreeItem<String> item = new TreeItem<>(folder.getName());
                rootItem.getChildren().add(item);
                isThereChildren(folder.getId(), item);
            }

        }
    }

    private void isThereChildren(int folderId, TreeItem<String> item) {
        for (Folder folder : this.folders) {
            if(folder.getParentId() == folderId) {
                TreeItem<String> childItem = new TreeItem<>(folder.getName());
                item.getChildren().add(childItem);

                isThereChildren(folder.getId(), childItem);
            }
        }
    }

    public void selectFolder(MouseEvent mouseEvent) throws SQLException{
        if (this.foldersTree.getSelectionModel().getSelectedItem() != null && this.foldersTree.getSelectionModel().getSelectedItem().getValue().toString() != selectedCourse.getName().toString()) {
            String folderName = this.foldersTree.getSelectionModel().getSelectedItem().getValue().toString();
            for (Folder folder : this.folders) {
                if(folder.getName().equals(folderName))
                    selectedFolder = folder;
            }
            System.out.println(selectedFolder.getName());//temp
            setFilesList(selectedFolder.getId());
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    //Files//
////////////////////////////////////////////////////////////////////////////////////////////////////
    private ArrayList<File> getFiles(int folderIdLike) throws SQLException {
        ArrayList<File> files = DbQuerys.getFiles(folderIdLike);
        this.files = files;
        return files;
    }

    public void setFilesList(int folderIdLike) throws SQLException{
        this.filesList.getItems().clear();
        for (File file : this.getFiles(folderIdLike)) {
            this.filesList.getItems().add(file.getName());
        }
    }
}
