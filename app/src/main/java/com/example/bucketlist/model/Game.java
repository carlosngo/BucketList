package com.example.bucketlist.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Game extends Media {

    public Game(){

    }

    public Game(String id, String title, String author){
        super(id, title, author);
    }
}
