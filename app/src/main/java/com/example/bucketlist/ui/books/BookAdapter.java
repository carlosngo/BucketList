package com.example.bucketlist.ui.books;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bucketlist.R;
import com.example.bucketlist.model.Book;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {
    ArrayList<Book> books;

    public BookAdapter(ArrayList<Book> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.book_row, parent, false);
        BookViewHolder holder = new BookViewHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.setTitle(books.get(position).getTitle());
        holder.setAuthor(books.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public Book getBook(int position) {
        return books.get(position);
    }
}
