package com.example.bucketlist.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bucketlist.R;
import com.example.bucketlist.model.*;
import com.example.bucketlist.dao.*;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultHolder> {
    private ArrayList<Note> contactList;
    private Activity context;

    public SearchResultAdapter(Activity activity){
        context = activity;
        contactList = new ArrayList<Note>();
    }

    @Override
    public SearchResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_result_row, parent, false);
        SearchResultHolder holder = new SearchResultHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(SearchResultHolder holder, final int position) {
        final Note note = contactList.get(position);
        holder.setName(contactList.get(position).getName());
        holder.setIcon(contactList.get(position));
//        holder.setCat(contactList.get(position).getCategory());
        holder.setDescription(contactList.get(position).getDescription());
        holder.setId(contactList.get(position).getId()+""+position);
        holder.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteId = note.getId();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (note instanceof Movie) {
                    FilmDAO movieDAO = Database.getMovieDAO();
                    if (noteId.equals("")) noteId = movieDAO.add(note);
                    movieDAO.addMetadataUnderMovie(noteId, userId);
                }
                else if (note instanceof Game) {
                    GameDAO gameDAO = Database.getGameDAO();
                    if (noteId.equals("")) noteId = gameDAO.add(note);
                    gameDAO.addGameUnderUser(noteId, userId);
                }
                else if (note instanceof Book) {
                    BookDAO bookDAO = Database.getBookDAO();
                    if (noteId.equals("")) noteId = bookDAO.add(note);
                    bookDAO.addBookUnderUser(noteId, userId);
                }
                else if (note instanceof Series) {
                    SeriesDAO seriesDAO = Database.getSeriesDAO();
                    if (noteId.equals("")) noteId = seriesDAO.add(note);
                    seriesDAO.addMetadataUnderSeries(noteId, userId);
                }
                else if (note instanceof Goal) {
                    GoalDAO goalDAO = Database.getGoalDAO();
                    if (noteId.equals("")) noteId = goalDAO.add(note);
                    goalDAO.addMetadataUnderGoal(noteId, userId);
                }
                Toast.makeText(context.getApplicationContext(), "Added " + note.getName() + " in your bucket list.", Toast.LENGTH_SHORT).show();
            }
        });
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

