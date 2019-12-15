package com.example.bucketlist.dao;

import com.example.bucketlist.model.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SeriesDAO implements DataAccessObject {
    public DatabaseReference getSeriesReference(){
        return FirebaseDatabase.getInstance().getReference("categories").child("series");
    }

    public DatabaseReference getSeriesUnderUserReference(String userId){
        return FirebaseDatabase.getInstance().getReference("Users").child(userId).child("series");
    }

    @Override
    public String add(Note model){
        Series series = (Series)model;
        DatabaseReference databaseSeries = getSeriesReference();
        String id = databaseSeries.push().getKey();
        series.setId(id);
        databaseSeries.child(id).setValue(series);
        return id;
    }

    @Override
    public void delete(String userId, String id) {
        DatabaseReference databaseUsers = getSeriesUnderUserReference(userId);
        databaseUsers.child(id).removeValue();
    }

    @Override
    public void update(String seriesId, Note model){
        DatabaseReference updateSeries = getSeriesReference().child(seriesId);
        updateSeries.setValue((Series)model);
    }

    public void addMetadataUnderSeries(String seriesId, String userId){
        DatabaseReference databaseSeriesUsers = getSeriesUnderUserReference(userId);
        databaseSeriesUsers.child(seriesId).setValue(true);
    }
}
