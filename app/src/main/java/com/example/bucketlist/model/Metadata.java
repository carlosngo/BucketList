package com.example.bucketlist.model;

public class Metadata extends Model{
    String title, author;

    public Metadata(){}

    public Metadata(String id, String title, String author){
        super(id);
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
