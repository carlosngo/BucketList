package com.example.bucketlist.model;

public abstract class Media extends  Model{
    private String title, author;

    public Media(){}

    public Media(String id, String title, String author){
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
