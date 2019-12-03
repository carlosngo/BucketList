package com.example.bucketlist.ui.films;

import android.graphics.Canvas;
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
import com.example.bucketlist.dao.Database;
import com.example.bucketlist.dao.FilmDAO;
import com.example.bucketlist.model.Movie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FilmsFragment extends Fragment {

    RecyclerView listFilms;
    FilmAdapter filmAdapter;

    DatabaseReference filmReference;
    DatabaseReference userFilmReference;
    ValueEventListener filmChangeListener;

    FilmDAO filmDAO;
    private FilmsViewModel filmsViewModel;
    private View root;
    private RecyclerView recyclerArea;
    private FilmAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<Movie> notes;
    private TextView blankMessage;

    private String userId;

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

        listFilms = root.findViewById(R.id.listFilms);
        listFilms.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false));
        blankMessage = root.findViewById(R.id.blankMessage);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        filmDAO = Database.getMovieDAO();
        filmReference = filmDAO.getMovieReference();
        userFilmReference = filmDAO.getMovieUnderUserReference(userId);
        filmAdapter = new FilmAdapter(getActivity());
        listFilms.setAdapter(filmAdapter);

        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(getActivity(), "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                int position = viewHolder.getAdapterPosition();
                String bookId = filmAdapter.getFilm(position).getId();
                filmDAO.delete(userId, bookId);
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(listFilms);

        filmChangeListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                filmAdapter.clear();

                listFilms.setAdapter(filmAdapter);
                for (DataSnapshot bookSnap : dataSnapshot.getChildren()) {
                    String bookId = bookSnap.getKey();
                    filmReference.child(bookId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            filmAdapter.addItem(dataSnapshot.getValue(Movie.class));
                            blankMessage.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        userFilmReference.addValueEventListener(filmChangeListener);
        return root;
    }
}