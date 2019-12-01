package com.example.bucketlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bucketlist.model.Note;
import com.example.bucketlist.ui.NoteAdapter;

import java.util.ArrayList;

public class ViewAllActivity extends AppCompatActivity {
    private RecyclerView recyclerArea;
    private NoteAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<Note> notes;
    private TextView blankMessage;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        backBtn = (Button) findViewById(R.id.backBtn);

        notes = new ArrayList<>();
        notes.add(new Note("firebase push id","Swan trumpet", "BOOK", "E.B. White"));
        notes.add(new Note("firebase push id","Charlotte's web", "BOOK", "M. S."));
        notes.add(new Note("firebase push id","Read book", "GOAL", "read at least 1 book"));
        notes.add(new Note("firebase push id","Get thin", "GOAL", "lose 300 pounds"));
        notes.add(new Note("firebase push id","One Piece", "SERIES", "Japanese Series"));
        notes.add(new Note("firebase push id","Probinsyano", "SERIES", "Filipino Series"));
        notes.add(new Note("firebase push id","Kimi no nawa", "FILM", "anime"));
        notes.add(new Note("firebase push id","Blank man the movie", "FILM", "Blank villain tries to take over the world, and gets whacked by blank man"));
        notes.add(new Note("firebase push id","Rock paper scissors online", "GAME", "Filipino game"));
        notes.add(new Note("firebase push id","Tumbang preso online", "GAME", "Filipino game"));

        if(notes.size()==0){
            blankMessage = findViewById(R.id.blankMessage);
            blankMessage.setVisibility(View.VISIBLE);
        }
        else{
            recyclerArea = findViewById(R.id.recycler_area);
            manager = new LinearLayoutManager(this);
            recyclerArea.setLayoutManager(manager);
            adapter = new NoteAdapter(this);
            recyclerArea.setAdapter(adapter);
            for(Note n : notes){
                adapter.addItem(n);
            }
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
