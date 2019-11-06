package com.example.bucketlist.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bucketlist.MainActivity;
import com.example.bucketlist.Note;
import com.example.bucketlist.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    View root;
    LinearLayout listLayout, newLayout;
    ArrayList<Note> notes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        listLayout = root.findViewById(R.id.listLayout);
        displayItems(listLayout, notes);
        return root;
    }

    public void displayItems(LinearLayout linearLayout, ArrayList<Note> notes){
    listLayout = root.findViewById(R.id.listLayout);
    for(Note n : notes){
        newLayout = new LinearLayout(getContext());
        newLayout.setOrientation(LinearLayout.HORIZONTAL);
        newLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        final TextView nameField = new TextView(getContext());
        nameField.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        final TextView catField = new TextView(getContext());
        catField.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        nameField.setText(n.getName());
        catField.setText(n.getCategory());

        nameField.setText("The Sound of Insects");
        catField.setText("Film");

        newLayout.addView(nameField);
        newLayout.addView(catField);

        listLayout.addView(newLayout);
    }

    }
}