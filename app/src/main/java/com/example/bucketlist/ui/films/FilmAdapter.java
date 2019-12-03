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

    private ArrayList<Movie> films;
    private Activity context;

    public FilmAdapter(Activity activity){
        context = activity;
        films = new ArrayList<Movie>();
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
        holder.setName(films.get(position).getName());
        //holder.setCat(films.get(position).getCategory());
        holder.setDescription(films.get(position).getDescription());
        holder.setId(films.get(position).getId()+""+position);
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public void addItem(Movie n){
        films.add(n);
        notifyItemInserted(films.size()-1);
    }

    public void clear() {
        films.clear();
        notifyDataSetChanged();
    }

    public Movie getFilm(int position) {
        return films.get(position);
    }
}
