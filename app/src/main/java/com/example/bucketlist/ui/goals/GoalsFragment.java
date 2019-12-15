package com.example.bucketlist.ui.goals;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bucketlist.dao.Database;
import com.example.bucketlist.dao.GoalDAO;
import com.example.bucketlist.model.Goal;
import com.example.bucketlist.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GoalsFragment extends Fragment {

    RecyclerView listGoals;
    GoalAdapter goalAdapter;

    DatabaseReference goalReference;
    DatabaseReference userGoalReference;
    ValueEventListener goalChangeListener;

    GoalDAO goalDAO;

    private GoalsViewModel goalsViewModel;
    private View root;
    private RecyclerView recyclerArea;
    private GoalAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<Goal> notes;
    private TextView blankMessage;

    private String userId;

    public static GoalsFragment newInstance() {
        return new GoalsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        goalsViewModel =
                ViewModelProviders.of(this).get(GoalsViewModel.class);
//        root = inflater.inflate(R.layout.fragment_notifications, container, false);
        root = inflater.inflate(R.layout.fragment_goals, container, false);
        final TextView textView = root.findViewById(R.id.text_goals);
        goalsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        listGoals = root.findViewById(R.id.listGoals);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        listGoals.setLayoutManager(layoutManager);
        listGoals.addItemDecoration(new DividerItemDecoration(listGoals.getContext(),
                layoutManager.getOrientation()));
        blankMessage = root.findViewById(R.id.blankMessage);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        goalDAO = Database.getGoalDAO();
        goalReference = goalDAO.getGoalReference();
        userGoalReference = goalDAO.getGoalUnderUserReference(userId);
        goalAdapter = new GoalAdapter(getActivity());
        listGoals.setAdapter(goalAdapter);

        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(getActivity(), "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                int position = viewHolder.getAdapterPosition();
                String bookId = goalAdapter.getGoal(position).getId();
                goalDAO.delete(userId, bookId);
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(listGoals);

        goalChangeListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                goalAdapter.clear();

                listGoals.setAdapter(goalAdapter);
                for (DataSnapshot bookSnap : dataSnapshot.getChildren()) {
                    String bookId = bookSnap.getKey();
                    goalReference.child(bookId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            goalAdapter.addItem(dataSnapshot.getValue(Goal.class));
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
        userGoalReference.addValueEventListener(goalChangeListener);
        return root;
    }
}