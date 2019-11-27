package com.example.bucketlist.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Movie extends Media {
    public Movie(){

    }

    public Movie(String id, String title, String author){
        super(id, title, author);
    }
}
