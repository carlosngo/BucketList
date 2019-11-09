package com.example.bucketlist.ui.books;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bucketlist.Note;
import com.example.bucketlist.R;

import java.util.ArrayList;

public class BooksFragment extends Fragment {

    private BooksViewModel booksViewModel;
    View root;
    LinearLayout listLayout, newLayout;
    ArrayList<Note> notes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        booksViewModel =
                ViewModelProviders.of(this).get(BooksViewModel.class);
        root = inflater.inflate(R.layout.fragment_books, container, false);
        final TextView textView = root.findViewById(R.id.text_books);
        booksViewModel.getText().observe(this, new Observer<String>() {
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
        for(int i=0; i<3; i++){
            newLayout = new LinearLayout(getContext());
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
                }
            });

            final TextView nameField = new TextView(getContext());
            nameField.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            nameField.setTextSize(20);
            nameField.setTextColor(Color.parseColor("#000000"));

            final TextView catField = new TextView(getContext());
            catField.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            catField.setTextColor(Color.parseColor("#7f7f7f"));
            catField.setTextSize(14);

            //sample books to show ui
            nameField.setText("Book"+i);
            catField.setText("BOOK");

//            nameField.setText(n.getName());
//            catField.setText(n.getCategory());

            newLayout.addView(nameField);
            newLayout.addView(catField);

            listLayout.addView(newLayout);

        }
    }
}