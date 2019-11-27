package com.example.bucketlist.dao;

import com.example.bucketlist.model.Model;

public interface DataAccessObject {
    String add(Model model);
    void delete(String userId, String id);
    void update(String id, Model model);
}
