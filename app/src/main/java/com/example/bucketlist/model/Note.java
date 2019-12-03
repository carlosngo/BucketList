package com.example.bucketlist.model;

// This class is a bean class that contains a single contact row's information. The getters and
// setters were made using the Refactor -> Encapsulate fields feature of Android Studio
public class Note {
    private String id;
//    private String category;
    private String name;
    private String description;

    public Note() {}

    public Note(String cid, String cname, String des){
        id = cid;
        name = cname;
        description = des;
    }

//    public Note(String cid, String cname,String cat, String des){
//        id = cid;
//        name = cname;
//        category = cat;
//        description = des;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }

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
