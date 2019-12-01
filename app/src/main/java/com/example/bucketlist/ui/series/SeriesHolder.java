package com.example.bucketlist.ui.series;

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
public class SeriesHolder extends RecyclerView.ViewHolder{

    private TextView name;
    private TextView cat;
    private TextView des;
    private String id;
    private LinearLayout row;

    public SeriesHolder(View view, final Activity activity) {
        super(view);

        row = view.findViewById(R.id.row);
        name = view.findViewById(R.id.name_field);
        cat = view.findViewById(R.id.cat_field);
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
}

