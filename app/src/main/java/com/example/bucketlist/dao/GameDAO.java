package com.example.bucketlist.dao;

import androidx.annotation.NonNull;

import com.example.bucketlist.model.Game;
import com.example.bucketlist.model.Metadata;
import com.example.bucketlist.model.Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameDAO implements DataAccessObject {
    String metaId;
    MetadataDAO metadataDAO = new MetadataDAO();

    public DatabaseReference getGameReference(){
        return FirebaseDatabase.getInstance().getReference("categories").child("games");
    }

    public DatabaseReference getMetadataUnderGameReference(String gameId){
        return FirebaseDatabase.getInstance().getReference("categories").child("games").child(gameId);
    }

    public DatabaseReference getMetadataReference(){
        return FirebaseDatabase.getInstance().getReference("categories").child("metadata");
    }

    @Override
    public String add(Model model){
        Game game = (Game)model;
        DatabaseReference databaseGame = getGameReference();
        String id = databaseGame.push().getKey();
        game.setId(id);
        databaseGame.child(id).setValue(game);
        metadataDAO.add(game.getData());
        return id;
    }

    @Override
    public void delete(String id) {
        DatabaseReference databaseBook = getGameReference().child(id);
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
    public void update(String gameId, Model model){
        DatabaseReference updateGame = getGameReference().child(gameId);
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

    public void addMetadataUnderGame(String categoryId, String metadataId){
        DatabaseReference databaseGameUsers = getMetadataUnderGameReference(categoryId);
        databaseGameUsers.child(metadataId).setValue(true);
    }
}
