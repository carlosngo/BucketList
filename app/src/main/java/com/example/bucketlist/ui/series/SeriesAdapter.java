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

    private ArrayList<Series> contactList;
    private Activity context;

    public SeriesAdapter(Activity activity){
        context = activity;
        contactList = new ArrayList<Series>();
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
        holder.setName(contactList.get(position).getName());
        //holder.setCat(contactList.get(position).getCategory());
        holder.setDescription(contactList.get(position).getDescription());
        holder.setId(contactList.get(position).getId()+""+position);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void addItem(Series n){
        contactList.add(n);
        notifyItemInserted(contactList.size()-1);
    }

    public Series getSeries(int position) {
        return contactList.get(position);
    }
}
