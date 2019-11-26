package com.example.bucketlist.dao;

import androidx.annotation.NonNull;

import com.example.bucketlist.model.Book;
import com.example.bucketlist.model.Metadata;
import com.example.bucketlist.model.Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookDAO implements DataAccessObject{
    String metaId;
    MetadataDAO metadataDAO = new MetadataDAO();

    public DatabaseReference getBookReference(){
        return FirebaseDatabase.getInstance().getReference("categories").child("books");
    }

    public DatabaseReference getMetadataUnderBookReference(String bookId){
        return FirebaseDatabase.getInstance().getReference("categories").child("books").child(bookId);
    }

    public DatabaseReference getMetadataReference(){
        return FirebaseDatabase.getInstance().getReference("categories").child("metadata");
    }

    @Override
    public String add(Model model){
        Book book = (Book)model;
        DatabaseReference databaseBook = getBookReference();
        String id = databaseBook.push().getKey();
        book.setId(id);
        databaseBook.child(id).setValue(book.getData().getId());
        metadataDAO.add(book.getData());
        return id;
    }

    @Override
    public void delete(String id) {
        DatabaseReference databaseBook = getBookReference().child(id);
        databaseBook.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                metaId = dataSnapshot.getKey();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseBook.removeValue();
        DatabaseReference databaseMetadata = getMetadataReference().child(metaId);
        databaseMetadata.removeValue();
    }

    @Override
    public void update(String bookId, Model model){
        DatabaseReference updateBook = getBookReference().child(bookId);
        updateBook.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                metaId = dataSnapshot.getKey();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        updateBook.child(metaId).setValue((Metadata)model);
    }

    public void addMetadataUnderBook(String categoryId, String metadataId){
        DatabaseReference databaseBookData = getMetadataUnderBookReference(categoryId);
        databaseBookData.child(metadataId).setValue(true);
    }
}
