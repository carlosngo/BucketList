package com.example.bucketlist;

import java.util.ArrayList;

public class User {

    private String username, password;
    private ArrayList<Note> notes;

    public User(String username, String password, ArrayList<Note> notes){
        this.username = username;
        this.password = password;
        this.notes = notes;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }
}
