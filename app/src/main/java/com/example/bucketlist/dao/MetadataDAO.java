package com.example.bucketlist.dao;

import com.example.bucketlist.model.Metadata;
import com.example.bucketlist.model.Model;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MetadataDAO implements DataAccessObject{
    public DatabaseReference getMetadataReference(){
        return FirebaseDatabase.getInstance().getReference("categories").child("metadata");
    }

    public DatabaseReference getMetadataUnderMetadataReference(String metadataId){
        return FirebaseDatabase.getInstance().getReference("categories").child("metadata").child(metadataId);
    }

    @Override
    public String add(Model model){
        Metadata data = (Metadata)model;
        DatabaseReference databaseMetadata = getMetadataReference();
        String id = databaseMetadata.push().getKey();
        data.setId(id);
        databaseMetadata.child(id).setValue(data);
        return id;
    }

    @Override
    public void delete(String id) {
        DatabaseReference databaseData = getMetadataReference().child(id);
        databaseData.removeValue();
    }

    @Override
    public void update(String metadataId, Model model){
        DatabaseReference updateData = getMetadataReference().child(metadataId);
        updateData.setValue((Metadata)model);
    }

    public void addMetadataUnderMetadata(String categoryId, String metadataId){
        DatabaseReference databaseMetadataUsers = getMetadataUnderMetadataReference(categoryId);
        databaseMetadataUsers.child(metadataId).setValue(true);
    }

}
