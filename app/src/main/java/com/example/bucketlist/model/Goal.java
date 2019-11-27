package com.example.bucketlist.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Goal extends Media {
    public Goal(){

    }

    public Goal(String id, String title, String author){
        super(id, title, author);
    }
}
