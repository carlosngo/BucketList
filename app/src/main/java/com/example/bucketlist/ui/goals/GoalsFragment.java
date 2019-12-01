package com.example.bucketlist.ui.goals;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bucketlist.model.Goal;
import com.example.bucketlist.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class GoalsFragment extends Fragment {

    private GoalsViewModel mViewModel;
    private View root;
    private RecyclerView recyclerArea;
    private GoalAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<Goal> notes;
    private TextView blankMessage;

    public static GoalsFragment newInstance() {
        return new GoalsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_goals, container, false);
        mViewModel =
                ViewModelProviders.of(this).get(GoalsViewModel.class);
//        root = inflater.inflate(R.layout.fragment_notifications, container, false);
        root = inflater.inflate(R.layout.fragment_goals, container, false);
        final TextView textView = root.findViewById(R.id.text_goals);
        mViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        notes = new ArrayList<>();
        notes.add(new Goal("firebase push id","Read book", "GOAL", "read at least 1 book"));
        notes.add(new Goal("firebase push id","Get thin", "GOAL", "lose 300 pounds"));

        if(notes.size()==0){
            blankMessage = root.findViewById(R.id.blankMessage);
            blankMessage.setVisibility(View.VISIBLE);
        }
        else{
            recyclerArea = root.findViewById(R.id.recycler_area);
            manager = new LinearLayoutManager(getContext());
            recyclerArea.setLayoutManager(manager);
            adapter = new GoalAdapter(getActivity());
            recyclerArea.setAdapter(adapter);
            for(Goal n : notes){
                adapter.addItem(n);
            }
        }
        return root;
    }

//    public void addGoal(View v){
//        startActivity(new Intent(getActivity(), ManualAddActivity.class));
//    }
}