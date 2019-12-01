package com.example.bucketlist.ui.games;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bucketlist.model.Game;
import com.example.bucketlist.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class GamesFragment extends Fragment {

    private GamesViewModel gamesViewModel;
    private View root;
    private RecyclerView recyclerArea;
    private GameAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<Game> notes;
    private TextView blankMessage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gamesViewModel =
                ViewModelProviders.of(this).get(GamesViewModel.class);
//        root = inflater.inflate(R.layout.fragment_notifications, container, false);
        root = inflater.inflate(R.layout.fragment_games, container, false);
        final TextView textView = root.findViewById(R.id.text_games);
        gamesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(root.getContext(), "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Toast.makeText(root.getContext(), "on Swiped ", Toast.LENGTH_SHORT).show();
                int position = viewHolder.getAdapterPosition();
                String gameId = adapter.getGame(position).getId();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (swipeDir == ItemTouchHelper.LEFT) {

                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerArea);

        notes = new ArrayList<>();
        notes.add(new Game("firebase push id","Rock paper scissors online", "GAME", "Filipino game"));
        notes.add(new Game("firebase push id","Tumbang preso online", "GAME", "Filipino game"));

        if(notes.size()==0){
            blankMessage = root.findViewById(R.id.blankMessage);
            blankMessage.setVisibility(View.VISIBLE);
        }
        else{
            recyclerArea = root.findViewById(R.id.recycler_area);
            manager = new LinearLayoutManager(getContext());
            recyclerArea.setLayoutManager(manager);
            adapter = new GameAdapter(getActivity());
            recyclerArea.setAdapter(adapter);
            for(Game n: notes){
                adapter.addItem(n);
            }
        }
        return root;
    }
}