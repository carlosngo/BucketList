package com.example.bucketlist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {

    LinearLayout listLayout;
    ArrayList<Note> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(getApplicationContext(), "You searched for: " + query, Toast.LENGTH_LONG).show();
        }

        listLayout = findViewById(R.id.listLayout);
//        for(Note n : results){
//            displayResults(n.getName(), n.getCategory());
//        }

        //below is dummy data
        LinearLayout newLayout;
        for(int i=0; i<3; i++){
            newLayout = new LinearLayout(this);
            newLayout.setOrientation(LinearLayout.VERTICAL);
            newLayout.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            RelativeLayout.LayoutParams parameter = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            parameter.setMargins(30, 32, 10, 0); // left, top, right, bottom
            newLayout.setLayoutParams(parameter);
            newLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // go to details page of selected item
                    Intent intent = new Intent(SearchResultsActivity.this, NoteDetailsActivity.class);
                    startActivityForResult(intent, 1);
                }
            });

            final TextView nameField = new TextView(this);
            nameField.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            nameField.setTextSize(20);
            nameField.setTextColor(Color.parseColor("#000000"));

            final TextView catField = new TextView(this);
            catField.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            catField.setTextColor(Color.parseColor("#7f7f7f"));
            catField.setTextSize(14);

            nameField.setText("Film"+i);
            catField.setText("FILM");

            newLayout.addView(nameField);
            newLayout.addView(catField);

            listLayout.addView(newLayout);
        }
        // end of dummy data
    }

    public void displayResults(String name, String category){
        LinearLayout newLayout = new LinearLayout(this);
        newLayout.setOrientation(LinearLayout.HORIZONTAL);
        newLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        RelativeLayout.LayoutParams parameter = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        parameter.setMargins(30, 32, 10, 0); // left, top, right, bottom
        newLayout.setLayoutParams(parameter);
        newLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to details page of selected item lines here
            }
        });

        final TextView nameField = new TextView(this);
        nameField.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        nameField.setText(name);
        nameField.setTextSize(20);
        nameField.setTextColor(Color.parseColor("#000000"));

        final TextView catField = new TextView(this);
        catField.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        catField.setText(category);
        catField.setTextColor(Color.parseColor("#7f7f7f"));
        catField.setTextSize(14);


        newLayout.addView(nameField);
        newLayout.addView(catField);

        listLayout.addView(newLayout);
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
}
