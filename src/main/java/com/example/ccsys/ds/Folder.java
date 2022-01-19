package com.example.ccsys.ds;

import java.util.ArrayList;

public class Folder {

    private int id;
    private String name;
    private ArrayList<User> users;
    private ArrayList<Folder> subFolders;

    public Folder() {
    }

    public Folder(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Folder(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Folder> getSubFolders() {
        return subFolders;
    }

    public void setSubFolders(ArrayList<Folder> subFolders) {
        this.subFolders = subFolders;
    }
}
