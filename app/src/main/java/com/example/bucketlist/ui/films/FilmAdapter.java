package com.example.bucketlist.ui.films;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bucketlist.R;
import com.example.bucketlist.model.*;

import java.util.ArrayList;

public class FilmAdapter extends RecyclerView.Adapter<FilmHolder>  {

    private ArrayList<Movie> contactList;
    private Activity context;

    public FilmAdapter(Activity activity){
        context = activity;
        contactList = new ArrayList<Movie>();
    }

    @Override
    public FilmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_row, parent, false);
        FilmHolder holder = new FilmHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(FilmHolder holder, final int position) {
        holder.setName(contactList.get(position).getName());
        //holder.setCat(contactList.get(position).getCategory());
        holder.setDescription(contactList.get(position).getDescription());
        holder.setId(contactList.get(position).getId()+""+position);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void addItem(Movie n){
        contactList.add(n);
        notifyItemInserted(contactList.size()-1);
    }

    public Movie getFilm(int position) {
        return contactList.get(position);
    }
}
