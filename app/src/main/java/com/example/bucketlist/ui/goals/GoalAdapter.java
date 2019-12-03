package com.example.bucketlist.ui.goals;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bucketlist.R;
import com.example.bucketlist.model.*;
import com.example.bucketlist.ui.games.GameHolder;

import java.util.ArrayList;

public class GoalAdapter extends RecyclerView.Adapter<GoalHolder>  {

    private ArrayList<Goal> goals;
    private Activity context;

    public GoalAdapter(Activity activity){
        context = activity;
        goals = new ArrayList<Goal>();
    }

    @Override
    public GoalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.goal_row, parent, false);
        GoalHolder holder = new GoalHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(GoalHolder holder, final int position) {
        holder.setName(goals.get(position).getName());
        //holder.setCat(goals.get(position).getCategory());
        holder.setDescription(goals.get(position).getDescription());
        holder.setId(goals.get(position).getId()+""+position);
    }

    @Override
    public int getItemCount() {
        return goals.size();
    }

    public void addItem(Goal n){
        goals.add(n);
        notifyItemInserted(goals.size()-1);
    }

    public void clear() {
        goals.clear();
        notifyDataSetChanged();
    }

    public Goal getGoal(int position) {
        return goals.get(position);
    }
}
