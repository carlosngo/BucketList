package com.example.bucketlist.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Goal extends Model {
    private Metadata data;

    public Goal(){

    }

    public Goal(String id, Metadata data){
        super(id);
        this.data = data;
    }

    public Metadata getData() {
        return data;
    }
}
