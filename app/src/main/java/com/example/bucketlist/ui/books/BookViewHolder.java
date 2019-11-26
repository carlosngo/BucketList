package com.example.bucketlist.ui.books;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bucketlist.R;

public class BookViewHolder extends RecyclerView.ViewHolder {
    public TextView txtTitle;
    public TextView txtAuthor;

    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
        txtTitle = itemView.findViewById(R.id.txtTitle);
        txtAuthor = itemView.findViewById(R.id.txtAuthor);
    }

    public void setTitle(String newTitle) {
        txtTitle.setText(newTitle);
    }

    public void setAuthor(String newAuthor) {
        txtAuthor.setText(newAuthor);
    }
}
