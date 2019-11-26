package com.example.bucketlist.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Game extends Model {
    private Metadata data;

    public Game(){

    }

    public Game(String id, Metadata data){
        super(id);
        this.data = data;
    }

    public Metadata getData() {
        return data;
    }
}
