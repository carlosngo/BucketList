package com.example.bucketlist.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Game extends Note {
    private String id;
    private String category;
    private String name;
    private String description;

    public Game(String cid, String cname,String cat, String des){
        super(cid, cname, cat, des);
        id = cid;
        name = cname;
        category = cat;
        description = des;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

