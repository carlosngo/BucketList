package com.example.bucketlist.ui.games;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bucketlist.R;
import com.example.bucketlist.model.*;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameHolder>  {

    private ArrayList<Game> contactList;
    private Activity context;

    public GameAdapter(Activity activity){
        context = activity;
        contactList = new ArrayList<Game>();
    }

    @Override
    public GameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.game_row, parent, false);
        GameHolder holder = new GameHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(GameHolder holder, final int position) {
        holder.setName(contactList.get(position).getName());
        //holder.setCat(contactList.get(position).getCategory());
        holder.setDescription(contactList.get(position).getDescription());
        holder.setId(contactList.get(position).getId()+""+position);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void addItem(Game n){
        contactList.add(n);
        notifyItemInserted(contactList.size()-1);
    }

    public Game getGame(int position) {
        return contactList.get(position);
    }
}
