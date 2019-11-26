package com.example.bucketlist.dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class Database {
    private static final BookDAO bookDAO= new BookDAO();
    private static final MovieDAO movieDAO= new MovieDAO();
    private static final GameDAO gameDAO= new GameDAO();
    private static final SeriesDAO seriesDAO= new SeriesDAO();
    private static final GoalDAO goalDAO= new GoalDAO();
    private static final MetadataDAO metadataDAO = new MetadataDAO();

    public static DatabaseReference getDatabaseReference() { return FirebaseDatabase.getInstance().getReference(); }

    public static BookDAO getBookDAO() {
        return bookDAO;
    }

    public static MovieDAO getMovieDAO() {
        return movieDAO;
    }

    public static GameDAO getGameDAO() {
        return gameDAO;
    }

    public static SeriesDAO getSeriesDAO() {
        return seriesDAO;
    }

    public static GoalDAO getGoalDAO() {
        return goalDAO;
    }

    public static MetadataDAO getMetadataDAO() {
        return metadataDAO;
    }
}
