package com.example.bucketlist;

import java.util.ArrayList;

public class User {

    private String email, password;
    private ArrayList<Note> notes;

    public User(String email, String password, ArrayList<Note> notes){
        this.email = email;
        this.password = password;
        this.notes = notes;
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return email;
    }

    public void setUsername(String username) {
        this.email = username;
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
