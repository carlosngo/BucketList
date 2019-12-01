package com.example.bucketlist.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bucketlist.R;
import com.example.bucketlist.model.Note;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;

//(1) The ContactAdapter is a child of RecyclerView.Adapter and uses the holder ContactHolder.
//    This means the adapter manages entries of ContactHolder
public class NoteAdapter extends RecyclerView.Adapter<NoteHolder>{

    //(2) The constructor and ArrayList are used to keep track of the information displayed. Though
    //    the RecyclerView.Adapter functions manage the views in the holders, the information in
    //    the views are kept separately and should be managed by the adapter class itself.
    private ArrayList<Note> contactList;
    private Activity context;

    public NoteAdapter(Activity activity){
        context = activity;
        contactList = new ArrayList<Note>();
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.note_row, parent, false);
        NoteHolder holder = new NoteHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, final int position) {
        holder.setName(contactList.get(position).getName());
        holder.setIcon(contactList.get(position).getCategory());
//        holder.setCat(contactList.get(position).getCategory());
        holder.setDescription(contactList.get(position).getDescription());
        holder.setId(contactList.get(position).getId()+""+position);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void addItem(Note n){
        contactList.add(n);
        notifyItemInserted(contactList.size()-1);
    }
}
