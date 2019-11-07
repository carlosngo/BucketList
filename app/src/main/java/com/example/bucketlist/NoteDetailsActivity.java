package com.example.bucketlist;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Visibility;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NoteDetailsActivity extends AppCompatActivity {
    TextView name, descrription;
    ArrayList<String> details;
    Button addBtn, deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        name = (TextView) findViewById(R.id.name);
        descrription = (TextView) findViewById(R.id.description);
        Intent intent = getIntent();
        details = intent.getStringArrayListExtra("NOTE_DETAILS");
        addBtn = (Button) findViewById(R.id.addBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        displayDetails(details);    //pass in details into this method to display Note details

    }

    public void displayDetails(ArrayList<String> details){
        name.setText(details.get(0)); //display name
        descrription.setText(details.get(1)); //display description like producer or smtg
        if(Boolean.parseBoolean(details.get(2))==false){ //if not added in the db yet, hide remove button and show add button
            deleteBtn.setVisibility(View.GONE);
        } else {
            addBtn.setVisibility(View.GONE);
        }
    }

    public void add(View v){
        // add note to person's bucket list db lines here then return to home screen

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
}
