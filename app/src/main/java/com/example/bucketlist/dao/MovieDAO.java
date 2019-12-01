package com.example.bucketlist.dao;

import com.example.bucketlist.model.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MovieDAO implements DataAccessObject {
    public DatabaseReference getMovieReference(){
        return FirebaseDatabase.getInstance().getReference("categories").child("movies");
    }

    public DatabaseReference getMovieUnderUserReference(String userId){
        return FirebaseDatabase.getInstance().getReference("Users").child(userId).child("movies");
    }

    @Override
    public String add(Note model){
        Movie movie = (Movie)model;
        DatabaseReference databaseMovie = getMovieReference();
        String id = databaseMovie.push().getKey();
        movie.setId(id);
        databaseMovie.child(id).setValue(movie);
        return id;
    }

    @Override
    public void delete(String userId, String id) {
        DatabaseReference databaseBook = getMovieReference().child(id);
        databaseBook.removeValue();
        DatabaseReference databaseUsers = getMovieUnderUserReference(userId);
        databaseUsers.child(id).removeValue();
    }

    @Override
    public void update(String movieId, Note model){
        DatabaseReference updateMovie = getMovieReference().child(movieId);
        updateMovie.setValue((Movie)model);
    }

    public void addMetadataUnderMovie(String movieId, String userId){
        DatabaseReference databaseMovieUsers = getMovieUnderUserReference(userId);
        databaseMovieUsers.child(movieId).setValue(true);
    }
}
