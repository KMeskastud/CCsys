package com.example.ccsys.ds;

public class File {
    private int id;
    private String name;
    private int folderId;

    public File(String name) {
        this.name = name;
    }

    public File(int id, String name, int folderId) {
        this.id = id;
        this.name = name;
        this.folderId = folderId;
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

}
