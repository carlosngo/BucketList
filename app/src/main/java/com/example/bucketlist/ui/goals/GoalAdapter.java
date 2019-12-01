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

    private ArrayList<Goal> contactList;
    private Activity context;

    public GoalAdapter(Activity activity){
        context = activity;
        contactList = new ArrayList<Goal>();
    }

    @Override
    public GoalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.note_row, parent, false);
        GoalHolder holder = new GoalHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(GoalHolder holder, final int position) {
        holder.setName(contactList.get(position).getName());
        holder.setCat(contactList.get(position).getCategory());
        holder.setDescription(contactList.get(position).getDescription());
        holder.setId(contactList.get(position).getId()+""+position);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void addItem(Goal n){
        contactList.add(n);
        notifyItemInserted(contactList.size()-1);
    }

    public Goal getGoal(int position) {
        return contactList.get(position);
    }
}
