package com.example.bucketlist.dao;

import androidx.annotation.NonNull;

import com.example.bucketlist.model.Model;
import com.example.bucketlist.model.Series;
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
    public String add(Model model){
        Series series = (Series)model;
        DatabaseReference databaseSeries = getSeriesReference();
        String id = databaseSeries.push().getKey();
        series.setId(id);
        databaseSeries.child(id).setValue(series);
        return id;
    }

    @Override
    public void delete(String userId, String id) {
        DatabaseReference databaseBook = getSeriesReference().child(id);
        databaseBook.removeValue();
        DatabaseReference databaseUsers = getSeriesUnderUserReference(userId);
        databaseUsers.removeValue();
    }

    @Override
    public void update(String seriesId, Model model){
        DatabaseReference updateSeries = getSeriesReference().child(seriesId);
        updateSeries.setValue((Series)model);
    }

    public void addMetadataUnderSeries(String seriesId, String userId){
        DatabaseReference databaseSeriesUsers = getSeriesUnderUserReference(userId);
        databaseSeriesUsers.child(seriesId).setValue(true);
    }
}
