package com.example.bucketlist.dao;

import com.example.bucketlist.model.*;

public interface DataAccessObject {
    String add(Note model);
    void delete(String userId, String id);
    void update(String id, Note model);
}
