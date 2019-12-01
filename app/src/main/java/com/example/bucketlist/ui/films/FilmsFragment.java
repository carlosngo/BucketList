//package com.example.bucketlist.ui.films;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProviders;
//
//import com.example.bucketlist.model.Book;
//import com.example.bucketlist.R;
//
//import java.util.ArrayList;
//
//public class FilmsFragment extends Fragment {
//
//    private FilmsViewModel filmsViewModel;
//    View root;
//    LinearLayout listLayout, newLayout;
//    ArrayList<Book> categories;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        filmsViewModel =
//                ViewModelProviders.of(this).get(FilmsViewModel.class);
////        root = inflater.inflate(R.layout.fragment_dashboard, container, false);
//        root = inflater.inflate(R.layout.fragment_films, container, false);
//        final TextView textView = root.findViewById(R.id.text_films);
//        filmsViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        listLayout = root.findViewById(R.id.listLayout);
//        displayItems(categories);
//        return root;
//    }
//
//    public void displayItems(ArrayList<Book> categories){
////        for(Notes n : categories){
//        for(int i=0; i<3; i++){
//            newLayout = new LinearLayout(getContext());
//            newLayout.setOrientation(LinearLayout.VERTICAL);
//            newLayout.setLayoutParams(new ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT));
//            RelativeLayout.LayoutParams parameter = new RelativeLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.WRAP_CONTENT,
//                    RelativeLayout.LayoutParams.WRAP_CONTENT);
//            parameter.setMargins(30, 32, 10, 0); // left, top, right, bottom
//            newLayout.setLayoutParams(parameter);
//            newLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // go to details page of selected item
//                }
//            });
//
//            final TextView nameField = new TextView(getContext());
//            nameField.setLayoutParams(new ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT));
//            nameField.setTextSize(20);
//            nameField.setTextColor(Color.parseColor("#000000"));
//
//            final TextView catField = new TextView(getContext());
//            catField.setLayoutParams(new ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT));
//            catField.setTextColor(Color.parseColor("#7f7f7f"));
//            catField.setTextSize(14);
//
//            //sample films to show ui
//            nameField.setText("Film"+i);
//            catField.setText("FILM");
//
////            nameField.setText(n.getName());
////            catField.setText(n.getCategory());
//
//            newLayout.addView(nameField);
//            newLayout.addView(catField);
//
//            listLayout.addView(newLayout);
//        }
//    }
//}

package com.example.bucketlist.ui.films;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bucketlist.model.Note;
import com.example.bucketlist.R;

import java.util.ArrayList;

public class FilmsFragment extends Fragment {

    private FilmsViewModel filmsViewModel;
    View root;
    LinearLayout listLayout, newLayout;
    ArrayList<Note> notes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        filmsViewModel =
                ViewModelProviders.of(this).get(FilmsViewModel.class);
//        root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        root = inflater.inflate(R.layout.fragment_films, container, false);
        final TextView textView = root.findViewById(R.id.text_films);
        filmsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        listLayout = root.findViewById(R.id.listLayout);
        displayItems(notes);
        return root;
    }

    public void displayItems(ArrayList<Note> notes){
//        for(Notes n : notes){
        for(int i=0; i<7; i++){
            newLayout = new LinearLayout(getContext());
            newLayout.setOrientation(LinearLayout.VERTICAL);
            newLayout.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            RelativeLayout.LayoutParams parameter = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            parameter.setMargins(0, 0, 10, 0); // left, top, right, bottom
            newLayout.setPadding(10,10,10,10);
            newLayout.setLayoutParams(parameter);
            newLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // go to details page of selected item
                    Toast.makeText(getContext(), "go to this game's NoteDetailsActivity", Toast.LENGTH_SHORT).show();
                }
            });

            final TextView nameField = new TextView(getContext());
            nameField.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            nameField.setTextSize(22);
            nameField.setTextColor(Color.parseColor("#000000"));

            final TextView catField = new TextView(getContext());
            catField.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            catField.setTextColor(Color.parseColor("#7f7f7f"));
            catField.setTextSize(14);

            final TextView desField = new TextView(getContext());
            desField.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            desField.setTextColor(Color.parseColor("#000000"));
            desField.setTextSize(18);

            //sample films to show ui
            nameField.setText("Film"+(i+1));
            catField.setText("FILM");
            desField.setText("film info stuff "+(i+1));

//            nameField.setText(n.getName());
//            catField.setText(n.getCategory());

            newLayout.addView(nameField);
            newLayout.addView(catField);
            newLayout.addView(desField);


            newLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));
            listLayout.addView(newLayout);
        }
    }
}