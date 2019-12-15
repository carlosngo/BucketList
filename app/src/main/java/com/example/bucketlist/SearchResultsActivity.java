package com.example.bucketlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androdocs.httprequest.HttpRequest;
import com.example.bucketlist.dao.*;
import com.example.bucketlist.model.*;
import com.example.bucketlist.ui.NoteAdapter;
import com.example.bucketlist.ui.SearchResultAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {

    private RecyclerView recyclerArea;
    private RecyclerView imdbRecycler;
    private RecyclerView rawgRecycler;
    private RecyclerView gbooksRecycler;

    private SearchResultAdapter adapter;
    private SearchResultAdapter imdbAdapter;
    private SearchResultAdapter rawgAdapter;
    private SearchResultAdapter gbooksAdapter;

    private ProgressBar usersLoader;
    private ProgressBar imdbLoader;
    private ProgressBar rawgLoader;
    private ProgressBar gbooksLoader;

    private TextView txtQuery;
    private String query;

    private final String OMDB_API = "45b6e697";

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Intent intent = getIntent();
        txtQuery = findViewById(R.id.txtQuery);

        recyclerArea = findViewById(R.id.recycler_area);
        recyclerArea.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchResultAdapter(this);
        recyclerArea.setAdapter(adapter);
        recyclerArea.setNestedScrollingEnabled(false);

        imdbRecycler = findViewById(R.id.imdb_recycler);
        imdbRecycler.setLayoutManager(new LinearLayoutManager(this));
        imdbAdapter = new SearchResultAdapter(this);
        imdbRecycler.setAdapter(imdbAdapter);
        imdbRecycler.setNestedScrollingEnabled(false);

        rawgRecycler = findViewById(R.id.rawg_recycler);
        rawgRecycler.setLayoutManager(new LinearLayoutManager(this));
        rawgAdapter = new SearchResultAdapter(this);
        rawgRecycler.setAdapter(rawgAdapter);
        rawgRecycler.setNestedScrollingEnabled(false);

        gbooksRecycler = findViewById(R.id.gbooks_recycler);
        gbooksRecycler.setLayoutManager(new LinearLayoutManager(this));
        gbooksAdapter = new SearchResultAdapter(this);
        gbooksRecycler.setAdapter(gbooksAdapter);
        gbooksRecycler.setNestedScrollingEnabled(false);

        usersLoader = findViewById(R.id.usersLoader);
        imdbLoader = findViewById(R.id.imdbLoader);
        rawgLoader = findViewById(R.id.rawgLoader);
        gbooksLoader = findViewById(R.id.gbooksLoader);

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            databaseReference = Database.getDatabaseReference();
            query = intent.getStringExtra(SearchManager.QUERY);
            String s = "Search results for \"" + query + "\"";
            txtQuery.setText(s);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int results = 0;
                    for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                        String category = categorySnapshot.getKey();
                        for (DataSnapshot noteSnapshot : categorySnapshot.getChildren()) {
                            String name = noteSnapshot.child("name").getValue(String.class);
                            if (name.toLowerCase().replaceAll(" ", "").contains
                                    (query.toLowerCase().replaceAll(" ", ""))) {
                                switch (category) {
                                    case "movies":
                                        adapter.addItem(noteSnapshot.getValue(Movie.class));
                                        break;
                                    case "books":
                                        adapter.addItem(noteSnapshot.getValue(Book.class));
                                        break;
                                    case "games":
                                        adapter.addItem(noteSnapshot.getValue(Game.class));
                                        break;
                                    case "series":
                                        adapter.addItem(noteSnapshot.getValue(Series.class));
                                        break;
                                    case "goals":
                                        adapter.addItem(noteSnapshot.getValue(Goal.class));
                                        break;
                                }
                                results++;
                            }
                        }

                    }
                    if (results == 0) findViewById(R.id.blankMessage).setVisibility(View.VISIBLE);
                    usersLoader.setVisibility(View.GONE);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            new omdbSearchTask().execute();
            new rawgSearchTask().execute();
            new gbooksSearchTask().execute();
        }
    }

    public void manualAdd(View v){
        Intent intent = new Intent(this, ManualAddActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1){
            if(resultCode == 0){
                finish(); // done manual adding to db
            }
            else if(requestCode == 1){
                finish(); // done removing a Note from db
            }
            else if(requestCode == 2){
                // cancelled adding, if we are going to add that button
            }
        }
    }

    class omdbSearchTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            try {
                String encodedQuery = URLEncoder.encode(query, "UTF-8");
                response = HttpRequest.excuteGet("http://www.omdbapi.com/?apikey=" + OMDB_API + "&s=" + encodedQuery);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObj = new JSONObject(s);
                JSONArray searchResults = jsonObj.getJSONArray("Search");
                int results = Math.min(10, searchResults.length());
                for (int i = 0; i < results; i++) {
                    JSONObject media = searchResults.getJSONObject(i);
                    String title = media.getString("Title");
                    boolean isMovie = media.getString("Type").equals("movie");

                    if (isMovie) {
                        String description = media.getString("Year") + " Movie";
                        imdbAdapter.addItem(new Movie("", title, description));
                    }
                    else {
                        String description = media.getString("Year") + " Series";
                        imdbAdapter.addItem(new Series("", title, description));
                    }
                }
                imdbLoader.setVisibility(View.GONE);
            } catch (JSONException e) {

            }

        }
    }

    class rawgSearchTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            try {
                String encodedQuery = URLEncoder.encode(query, "UTF-8");
                response = HttpRequest.excuteGet("https://api.rawg.io/api/games?page=1&page_size=5&search=" + encodedQuery);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray searchResults = jsonObject.getJSONArray("results");
                int results = Math.min(5, searchResults.length());
                for (int i = 0; i < results; i++) {
                    JSONObject game = searchResults.getJSONObject(i);
                    String name = game.getString("name");
                    String description = game.getString("released").substring(0, 4);
                    rawgAdapter.addItem(new Game("", name, description));
                }
                rawgLoader.setVisibility(View.GONE);
            } catch (JSONException e) {

            }
        }
    }

    class gbooksSearchTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            try {
                String encodedQuery = URLEncoder.encode(query, "UTF-8");
                response = HttpRequest.excuteGet("https://www.googleapis.com/books/v1/volumes?q=" + encodedQuery + "&maxResults=5");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray searchResults = jsonObject.getJSONArray("items");
                int results = Math.min(5, searchResults.length());
                for (int i = 0; i < results; i++) {
                    JSONObject book = searchResults.getJSONObject(i).getJSONObject("volumeInfo");
                    String title = book.getString("title");
                    String description;
                    if (!book.isNull("authors")) description = "by " + book.getJSONArray("authors").getString(0);
                    else description = "No author(s) provided.";
                    gbooksAdapter.addItem(new Book("", title, description));
                }
                gbooksLoader.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

