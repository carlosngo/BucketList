package com.example.bucketlist.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Book extends Model{
    private String title;
    private String author;

    public Book(){

    }

    public Book(String id, String title, String author){
        super(id);
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    //    public ArrayList<String> getOwnerList() {
//        if (ownerList == null){
//            ArrayList<String> aList = new ArrayList();
//            try{
//                aList.addAll(Arrays.asList(owners.split(",")));
//            }catch(Exception e){}
//            ownerList = aList;
//            return aList;
//        }else
//            return ownerList;
//    }
//
//    public void addOwners(String owner) {
//        owners = owners + "," + owner;
//    }
//
//    public void removeOwners(String owner){
//        if(ownerList.size() > 1 && ownerList.indexOf(owner) > 0){
//            String hold = "," + owner;
//            owners = owners.replace(hold, "");
//        }else{
//            owners = owners.replace(owner, "");
//        }
//
//    }
}
