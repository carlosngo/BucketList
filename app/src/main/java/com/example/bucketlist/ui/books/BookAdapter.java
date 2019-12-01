package com.example.bucketlist.ui.books;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bucketlist.R;
import com.example.bucketlist.model.*;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookHolder>  {

    private ArrayList<Book> books;
    private Activity context;

    public BookAdapter(Activity activity){
        context = activity;
        books = new ArrayList<Book>();
    }

    @Override
    public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.book_row, parent, false);
        BookHolder holder = new BookHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(BookHolder holder, final int position) {
        holder.setName(books.get(position).getName());
        //holder.setCat(books.get(position).getCategory());
        holder.setDescription(books.get(position).getDescription());
        holder.setId(books.get(position).getId()+""+position);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void addItem(Book n){
        books.add(n);
        notifyItemInserted(books.size()-1);
    }

    public Book getBook(int position) {
        return books.get(position);
    }
}

//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.bucketlist.R;
//import com.example.bucketlist.model.Book;
//
//import java.util.ArrayList;
//
//public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {
//    ArrayList<Book> books;
//
//    public BookAdapter(ArrayList<Book> books) {
//        this.books = books;
//    }
//
//    @NonNull
//    @Override
//    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View row = inflater.inflate(R.layout.book_row, parent, false);
//        BookViewHolder holder = new BookViewHolder(row);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
//        holder.setTitle(books.get(position).getTitle());
//        holder.setAuthor(books.get(position).getAuthor());
//    }
//
//    @Override
//    public int getItemCount() {
//        return books.size();
//    }
//
//    public Book getBook(int position) {
//        return books.get(position);
//    }
//}
