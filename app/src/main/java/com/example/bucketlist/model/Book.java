package com.example.bucketlist.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Book extends Media{

    public Book(){

    }

    public Book(String id, String title, String author){
        super(id, title, author);
    }
}
