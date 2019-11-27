package com.example.bucketlist.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Series extends Media {
    public Series(){

    }

    public Series(String id, String title, String author){
        super(id, title, author);
    }
}
