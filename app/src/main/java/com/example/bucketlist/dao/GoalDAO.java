package com.example.bucketlist.dao;

import androidx.annotation.NonNull;

import com.example.bucketlist.model.Goal;
import com.example.bucketlist.model.Metadata;
import com.example.bucketlist.model.Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GoalDAO implements DataAccessObject {
    String metaId;
    MetadataDAO metadataDAO = new MetadataDAO();

    public DatabaseReference getGoalReference(){
        return FirebaseDatabase.getInstance().getReference("categories").child("goals");
    }

    public DatabaseReference getMetadataUnderGoalReference(String goalId){
        return FirebaseDatabase.getInstance().getReference("categories").child("goals").child(goalId);
    }

    public DatabaseReference getMetadataReference(){
        return FirebaseDatabase.getInstance().getReference("categories").child("metadata");
    }

    @Override
    public String add(Model model){
        Goal goal = (Goal)model;
        DatabaseReference databaseGoal = getGoalReference();
        String id = databaseGoal.push().getKey();
        goal.setId(id);
        databaseGoal.child(id).setValue(goal);
        metadataDAO.add(goal.getData());
        return id;
    }

    @Override
    public void delete(String id) {
        DatabaseReference databaseBook = getGoalReference().child(id);
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
    public void update(String goalId, Model model){
        DatabaseReference updateGame = getGoalReference().child(goalId);
        updateGame.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                metaId = dataSnapshot.getKey();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        updateGame.child(metaId).setValue((Metadata)model);
    }

    public void addMetadataUnderGoal(String categoryId, String metadataId){
        DatabaseReference databaseGoalUsers = getMetadataUnderGoalReference(categoryId);
        databaseGoalUsers.child(metadataId).setValue(true);
    }
}
