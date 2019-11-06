package com.example.bucketlist;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Visibility;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import java.util.ArrayList;

public class NoteDetailsActivity extends AppCompatActivity {

    TextView name, descrription;
    ArrayList<String> details;
    Button deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        name = (TextView) findViewById(R.id.name);
        descrription = (TextView) findViewById(R.id.description);
        Intent intent = getIntent();
        details = intent.getStringArrayListExtra("NOTE_DETAILS");
        displayDetails(details);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
    }

    public void displayDetails(ArrayList<String> details){
        name.setText(details.get(0));
        descrription.setText(details.get(1));
    }

    public void add(View v){

    }

    public void remove(View v){

    }
}
