package com.example.bucketlist.ui;


import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bucketlist.R;
import com.example.bucketlist.SearchResultsActivity;

// This class serves as the container class that the view rests on. All the manipulation and
// interaction functions of the view is stored here.
public class NoteHolder extends RecyclerView.ViewHolder{

    private TextView name;
    private TextView cat;
    private TextView des;
    private ImageView icon;
    private String id;
    private LinearLayout row;

    public NoteHolder(View view, final Activity activity) {
        super(view);

        row = view.findViewById(R.id.row);
        name = view.findViewById(R.id.name_field);
        icon = view.findViewById(R.id.imageView);
        //cat = view.findViewById(R.id.cat_field);
        des = view.findViewById(R.id.description_field);

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

    public void setIcon(String category) {
        switch (category) {
            case "FILM":
                icon.setImageResource(R.drawable.blk_films);
                break;
            case "BOOK":
                icon.setImageResource(R.drawable.blk_book);
                break;
            case "GAME":
                icon.setImageResource(R.drawable.blk_games);
                break;
            case "SERIES":
                icon.setImageResource(R.drawable.blk_series);
                break;
            case "GOAL":
                icon.setImageResource(R.drawable.blk_goals);
                break;
        }
    }
}
