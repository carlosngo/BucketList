package com.example.bucketlist.dao;

import com.example.bucketlist.model.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookDAO implements DataAccessObject{
    public DatabaseReference getBookReference(){
        return FirebaseDatabase.getInstance().getReference("categories").child("books");
    }

    public DatabaseReference getBookUnderUserReference(String userId){
        return FirebaseDatabase.getInstance().getReference("Users").child(userId).child("books");
    }

    @Override
    public String add(Note model){
        Book book = (Book)model;
        DatabaseReference databaseBook = getBookReference();
        String id = databaseBook.push().getKey();
        book.setId(id);
        databaseBook.child(id).setValue(book);
        return id;
    }

    @Override
    public void delete(String userId, String id) {
        DatabaseReference databaseUsers = getBookUnderUserReference(userId);
        databaseUsers.child(id).removeValue();
    }

    @Override
    public void update(String bookId, Note model){
        DatabaseReference updateBook = getBookReference().child(bookId);
        updateBook.setValue((Book)model);
    }

    public void addBookUnderUser(String bookId, String userId){
        DatabaseReference databaseBookData = getBookUnderUserReference(userId);
        databaseBookData.child(bookId).setValue(true);
    }
}
