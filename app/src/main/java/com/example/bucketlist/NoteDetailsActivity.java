package com.example.bucketlist;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NoteDetailsActivity extends AppCompatActivity {
    TextView name, descrription;
    ArrayList<String> details;
    Button addBtn, deleteBtn, editBtn, backBtn;
    FrameLayout progressOverlay;
    String nam, cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        name = (TextView) findViewById(R.id.name);
        descrription = (TextView) findViewById(R.id.description);
        Intent intent = getIntent();
        nam = intent.getStringExtra("ACTION_NAME");
        cat = intent.getStringExtra("ACTION_CATEGORY");
        details = intent.getStringArrayListExtra("NOTE_DETAILS");
        addBtn = (Button) findViewById(R.id.addBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        editBtn = (Button) findViewById(R.id.editBtn);
        backBtn = (Button) findViewById(R.id.backBtn);
        progressOverlay = (FrameLayout) findViewById(R.id.progress_overlay);
        progressOverlay.setVisibility(View.INVISIBLE);
        displayDetails(details);    //pass in details into this method to display Book details

    }

    public void displayDetails(ArrayList<String> details){
        name.setText(details.get(0)); //display name
        descrription.setText(details.get(1)); //display description like producer or smtg
        if(Boolean.parseBoolean(details.get(2))==false){ //if not added in the db yet, hide remove button and show add button
            deleteBtn.setVisibility(View.GONE);
            editBtn.setVisibility(View.GONE);
        } else {
            addBtn.setVisibility(View.GONE);
        }
    }

    public void add(View v){
        // add note to person's bucket list db lines here then return to home screen
        progressOverlay.setVisibility(View.VISIBLE);



        Toast.makeText(this,
                "Successfully added to bucket list.", Toast.LENGTH_SHORT).show();
        setResult(0);
        finish();
    }

    public void remove(View v){
        // remove note from person's bucket list db lines here then return to home screen

        Toast.makeText(this,
                "Successfully removed from bucket list.", Toast.LENGTH_SHORT).show();
        setResult(1);
        finish();
    }

    public void edit(View v){
        startActivity(new Intent(NoteDetailsActivity.this, EditNoteActivity.class));
    }

    public void back(View v){
        finish();
    }
}
