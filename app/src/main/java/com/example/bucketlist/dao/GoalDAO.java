package com.example.bucketlist.dao;


import com.example.bucketlist.model.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GoalDAO implements DataAccessObject {
    public DatabaseReference getGoalReference(){
        return FirebaseDatabase.getInstance().getReference("categories").child("goals");
    }

    public DatabaseReference getGoalUnderUserReference(String userId){
        return FirebaseDatabase.getInstance().getReference("Users").child(userId).child("goals");
    }

    @Override
    public String add(Note model){
        Goal goal = (Goal)model;
        DatabaseReference databaseGoal = getGoalReference();
        String id = databaseGoal.push().getKey();
        goal.setId(id);
        databaseGoal.child(id).setValue(goal);
        return id;
    }

    @Override
    public void delete(String userId, String id) {
        DatabaseReference databaseBook = getGoalReference().child(id);
        databaseBook.removeValue();
        DatabaseReference databaseUsers = getGoalUnderUserReference(userId);
        databaseUsers.child(id).removeValue();
    }

    @Override
    public void update(String goalId, Note model){
        DatabaseReference updateGame = getGoalReference().child(goalId);
        updateGame.setValue((Goal)model);
    }

    public void addMetadataUnderGoal(String goalId, String userId){
        DatabaseReference databaseGoalUsers = getGoalUnderUserReference(userId);
        databaseGoalUsers.child(goalId).setValue(true);
    }
}
