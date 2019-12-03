package com.example.bucketlist.ui.series;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bucketlist.R;
import com.example.bucketlist.model.*;

import java.util.ArrayList;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesHolder>  {

    private ArrayList<Series> series;
    private Activity context;

    public SeriesAdapter(Activity activity){
        context = activity;
        series = new ArrayList<Series>();
    }

    @Override
    public SeriesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.series_row, parent, false);
        SeriesHolder holder = new SeriesHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(SeriesHolder holder, final int position) {
        holder.setName(series.get(position).getName());
        //holder.setCat(series.get(position).getCategory());
        holder.setDescription(series.get(position).getDescription());
        holder.setId(series.get(position).getId()+""+position);
    }

    @Override
    public int getItemCount() {
        return series.size();
    }

    public void addItem(Series n){
        series.add(n);
        notifyItemInserted(series.size()-1);
    }

    public void clear() {
        series.clear();
        notifyDataSetChanged();
    }

    public Series getSeries(int position) {
        return series.get(position);
    }
}
