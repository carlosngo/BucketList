package com.example.bucketlist.model;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Book extends Note {
    private String id;
    private String category;
    private String name;
    private String author;

    public Book(String id, String name, String category, String author) {
        super(id, name, category, author);
        this.id = id;
        this.category = category;
        this.name = name;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
        return author;
    }

    public void setDescription(String description) {
        this.author = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
//
//import com.google.firebase.database.IgnoreExtraProperties;
//
//@IgnoreExtraProperties
//public class Book extends Media{
//
//    public Book(){
//
//    }
//
//    public Book(String id, String title, String author){
//        super(id, title, author);
//    }
//}
