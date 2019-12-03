package com.example.bucketlist.ui.films;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bucketlist.R;
import com.example.bucketlist.model.Movie;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class FilmsFragment extends Fragment {

    private FilmsViewModel filmsViewModel;
    private View root;
    private RecyclerView recyclerArea;
    private FilmAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<Movie> notes;
    private TextView blankMessage;

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

        notes = new ArrayList<>();
//        notes.add(new Movie("firebase push id","Kimi no nawa", "FILM", "anime"));
//        notes.add(new Movie("firebase push id","Blank man the movie", "FILM", "Blank villain tries to take over the world, and gets whacked by blank man"));

        if(notes.size()==0){
            blankMessage = root.findViewById(R.id.blankMessage);
            blankMessage.setVisibility(View.VISIBLE);
        }
        else{
            blankMessage.setVisibility(View.GONE);
            recyclerArea = root.findViewById(R.id.recycler_area);
            manager = new LinearLayoutManager(getContext());
            recyclerArea.setLayoutManager(manager);
            adapter = new FilmAdapter(getActivity());
            recyclerArea.setAdapter(adapter);
            for(Movie n : notes){
                adapter.addItem(n);
            }
        }
        return root;
    }
}