package com.example.bucketlist.dao;

import androidx.annotation.NonNull;

import com.example.bucketlist.model.Metadata;
import com.example.bucketlist.model.Model;
import com.example.bucketlist.model.Series;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SeriesDAO implements DataAccessObject {
    String metaId;
    MetadataDAO metadataDAO = new MetadataDAO();

    public DatabaseReference getSeriesReference(){
        return FirebaseDatabase.getInstance().getReference("categories").child("series");
    }

    public DatabaseReference getMetadataUnderSeriesReference(String seriesId){
        return FirebaseDatabase.getInstance().getReference("categories").child("series").child(seriesId);
    }

    public DatabaseReference getMetadataReference(){
        return FirebaseDatabase.getInstance().getReference("categories").child("metadata");
    }

    @Override
    public String add(Model model){
        Series series = (Series)model;
        DatabaseReference databaseSeries = getSeriesReference();
        String id = databaseSeries.push().getKey();
        series.setId(id);
        databaseSeries.child(id).setValue(series);
        metadataDAO.add(series.getData());
        return id;
    }

    @Override
    public void delete(String id) {
        DatabaseReference databaseBook = getSeriesReference().child(id);
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
    public void update(String seriesId, Model model){
        DatabaseReference updateSeries = getSeriesReference().child(seriesId);
        updateSeries.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                metaId = dataSnapshot.getKey();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        updateSeries.child(metaId).setValue((Metadata)model);
    }

    public void addMetadataUnderSeries(String categoryId, String metadataId){
        DatabaseReference databaseSeriesUsers = getMetadataUnderSeriesReference(categoryId);
        databaseSeriesUsers.child(metadataId).setValue(true);
    }
}
