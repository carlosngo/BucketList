package com.example.bucketlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bucketlist.model.Book;
import com.example.bucketlist.model.Game;
import com.example.bucketlist.model.Goal;
import com.example.bucketlist.model.Movie;
import com.example.bucketlist.model.Note;
import com.example.bucketlist.model.Series;
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
        notes.add(new Book("firebase push id","Swan trumpet", "E.B. White"));
        notes.add(new Book("firebase push id","Charlotte's web",  "M. S."));
        notes.add(new Goal("firebase push id","Read book", "read at least 1 book"));
        notes.add(new Goal("firebase push id","Get thin", "lose 300 pounds"));
        notes.add(new Series("firebase push id","One Piece", "Japanese Series"));
        notes.add(new Series("firebase push id","Probinsyano", "Filipino Series"));
        notes.add(new Movie("firebase push id","Kimi no nawa", "anime"));
        notes.add(new Movie("firebase push id","Blank man the movie", "Blank villain tries to take over the world, and gets whacked by blank man"));
        notes.add(new Game("firebase push id","Rock paper scissors online", "Filipino game"));
        notes.add(new Game("firebase push id","Tumbang preso online", "Filipino game"));

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
