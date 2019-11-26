package com.example.bucketlist.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Movie extends Model {

    private Metadata data;

    public Movie(){

    }

    public Movie(String id, Metadata data){
        super(id);
        this.data = data;
    }

    public Metadata getData() {
        return data;
    }
}
