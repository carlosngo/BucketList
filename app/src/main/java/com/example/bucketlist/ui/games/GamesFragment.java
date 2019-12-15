package com.example.bucketlist.ui.games;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bucketlist.dao.Database;
import com.example.bucketlist.dao.GameDAO;
import com.example.bucketlist.model.Game;
import com.example.bucketlist.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GamesFragment extends Fragment {

    RecyclerView listGames;
    GameAdapter gameAdapter;

    DatabaseReference gameReference;
    DatabaseReference userGameReference;
    ValueEventListener gameChangeListener;

    GameDAO gameDAO;
    private GamesViewModel gamesViewModel;
    private View root;
    private RecyclerView recyclerArea;
    private GameAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<Game> notes;
    private TextView blankMessage;
    private ProgressBar loader;

    private String userId;

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
        loader = root.findViewById(R.id.loader);

        listGames = root.findViewById(R.id.listGames);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        listGames.setLayoutManager(layoutManager);
        listGames.addItemDecoration(new DividerItemDecoration(listGames.getContext(),
                layoutManager.getOrientation()));
        blankMessage = root.findViewById(R.id.blankMessage);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        gameDAO = Database.getGameDAO();
        gameReference = gameDAO.getGameReference();
        userGameReference = gameDAO.getGameUnderUserReference(userId);
        gameAdapter = new GameAdapter(getActivity());
        listGames.setAdapter(gameAdapter);

        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(getActivity(), "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                int position = viewHolder.getAdapterPosition();
                String bookId = gameAdapter.getGame(position).getId();
                gameDAO.delete(userId, bookId);
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(listGames);

        gameChangeListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loader.setVisibility(View.VISIBLE);
                blankMessage.setVisibility(View.GONE);
                gameAdapter.clear();

                listGames.setAdapter(gameAdapter);
                if (dataSnapshot.getChildrenCount() == 0) {
                    loader.setVisibility(View.GONE);
                    blankMessage.setVisibility(View.VISIBLE);
                } else {
                    for (DataSnapshot bookSnap : dataSnapshot.getChildren()) {
                        String bookId = bookSnap.getKey();
                        gameReference.child(bookId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                loader.setVisibility(View.GONE);
                                gameAdapter.addItem(dataSnapshot.getValue(Game.class));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        userGameReference.addValueEventListener(gameChangeListener);
        return root;
    }
}