package com.example.bucketlist.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Series extends Model {
    private Metadata data;

    public Series(){

    }

    public Series(String id, Metadata data){
        super(id);
        this.data = data;
    }

    public Metadata getData() {
        return data;
    }
}
