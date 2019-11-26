package com.example.bucketlist.dao;

import androidx.annotation.NonNull;

import com.example.bucketlist.model.Metadata;
import com.example.bucketlist.model.Model;
import com.example.bucketlist.model.Movie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MovieDAO implements DataAccessObject {
    String metaId;
    MetadataDAO metadataDAO = new MetadataDAO();

    public DatabaseReference getMovieReference(){
        return FirebaseDatabase.getInstance().getReference("categories").child("movies");
    }

    public DatabaseReference getMetadataUnderMovieReference(String movieId){
        return FirebaseDatabase.getInstance().getReference("categories").child("movies").child(movieId);
    }

    public DatabaseReference getMetadataReference(){
        return FirebaseDatabase.getInstance().getReference("categories").child("metadata");
    }

    @Override
    public String add(Model model){
        Movie movie = (Movie)model;
        DatabaseReference databaseMovie = getMovieReference();
        String id = databaseMovie.push().getKey();
        movie.setId(id);
        databaseMovie.child(id).setValue(movie);
        metadataDAO.add(movie.getData());
        return id;
    }

    @Override
    public void delete(String id) {
        DatabaseReference databaseBook = getMovieReference().child(id);
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
    public void update(String movieId, Model model){
        DatabaseReference updateMovie = getMovieReference().child(movieId);
        updateMovie.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                metaId = dataSnapshot.getKey();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        updateMovie.child(metaId).setValue((Metadata)model);
    }

    public void addMetadataUnderMovie(String categoryId, String metadataId){
        DatabaseReference databaseMovieUsers = getMetadataUnderMovieReference(categoryId);
        databaseMovieUsers.child(metadataId).setValue(true);
    }
}
