package com.example.bucketlist.ui;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bucketlist.R;
import com.example.bucketlist.SearchResultsActivity;
import com.example.bucketlist.model.Book;
import com.example.bucketlist.model.Game;
import com.example.bucketlist.model.Goal;
import com.example.bucketlist.model.Movie;
import com.example.bucketlist.model.Note;
import com.example.bucketlist.model.Series;

public class SearchResultHolder extends RecyclerView.ViewHolder{
    private TextView name;
    private TextView cat;
    private TextView des;
    private ImageView icon;
    private String id;
    private LinearLayout row;
    private ImageButton btnAddNote;

    public SearchResultHolder(View view, final Activity activity) {
        super(view);

        row = view.findViewById(R.id.row);
        name = view.findViewById(R.id.name_field);
        icon = view.findViewById(R.id.imageView);
        //cat = view.findViewById(R.id.cat_field);
        des = view.findViewById(R.id.description_field);
        btnAddNote = view.findViewById(R.id.btnAddNote);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity instanceof SearchResultsActivity){
                    Toast.makeText(activity, "Search activity's"+id+"'s ("+name.getText().toString()+") NoteDetailsActivity", Toast.LENGTH_SHORT).show();

                } else {
                    // go to details page of selected item
                    Toast.makeText(activity, "go to noteId: "+id+"'s ("+name.getText().toString()+") NoteDetailsActivity", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setName(String newName){
        name.setText(newName);
    }

    public void setCat(String newCat){
        cat.setText(newCat);
    }

    public void setDescription(String newDescription){
        des.setText(newDescription);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIcon(Note note) {

        if (note instanceof Movie) {
            icon.setImageResource(R.drawable.blk_films);
        }
        else if (note instanceof Book) {
            icon.setImageResource(R.drawable.blk_book);
        }
        if (note instanceof Game) {
            icon.setImageResource(R.drawable.blk_games);
        }
        if (note instanceof Series) {
            icon.setImageResource(R.drawable.blk_series);
        }
        if (note instanceof Goal) {
            icon.setImageResource(R.drawable.blk_goals);
        }

    }

    public void setClickListener(View.OnClickListener listener) { btnAddNote.setOnClickListener(listener); }
}
