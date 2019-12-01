package com.example.bucketlist.dao;

import com.example.bucketlist.model.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GameDAO implements DataAccessObject {
    public DatabaseReference getGameReference(){
        return FirebaseDatabase.getInstance().getReference("categories").child("games");
    }

    public DatabaseReference getGameUnderUserReference(String userId){
        return FirebaseDatabase.getInstance().getReference("Users").child(userId).child("games");
    }

    @Override
    public String add(Note model){
        Game game = (Game)model;
        DatabaseReference databaseGame = getGameReference();
        String id = databaseGame.push().getKey();
        game.setId(id);
        databaseGame.child(id).setValue(game);
        return id;
    }

    @Override
    public void delete(String userId, String id) {
        DatabaseReference databaseBook = getGameReference().child(id);
        databaseBook.removeValue();
        DatabaseReference databaseUsers = getGameUnderUserReference(userId);
        databaseUsers.child(id).removeValue();
    }

    @Override
    public void update(String gameId, Note model){
        DatabaseReference updateGame = getGameReference().child(gameId);
        updateGame.setValue((Game)model);
    }

    public void addGameUnderUser(String gameId, String userId){
        DatabaseReference databaseGameUsers = getGameUnderUserReference(userId);
        databaseGameUsers.child(gameId).setValue(true);
    }
}
