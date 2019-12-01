package com.example.bucketlist.ui.books;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bucketlist.dao.BookDAO;
import com.example.bucketlist.model.*;
import com.example.bucketlist.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BooksFragment extends Fragment {

//    RecyclerView listBooks;
//    BookAdapter bookAdapter;
//
//    DatabaseReference bookReference;
//    ValueEventListener bookChangeListener;
//
//    BookDAO bookDAO;
    private BooksViewModel booksViewModel;
    private View root;
    private RecyclerView recyclerArea;
    private BookAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<Book> notes;
    private TextView blankMessage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        booksViewModel =
                ViewModelProviders.of(this).get(BooksViewModel.class);
//        root = inflater.inflate(R.layout.fragment_notifications, container, false);
        root = inflater.inflate(R.layout.fragment_books, container, false);
        final TextView textView = root.findViewById(R.id.text_books);
        booksViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(root.getContext(), "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Toast.makeText(root.getContext(), "on Swiped ", Toast.LENGTH_SHORT).show();
                int position = viewHolder.getAdapterPosition();
                String bookId = adapter.getBook(position).getId();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (swipeDir == ItemTouchHelper.LEFT) {

                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerArea);


//        bookDAO = Database.getBookDAO();
//        bookReference = bookDAO.getBookReference();
//        books = new ArrayList<>();
////        displayItems(categories);
//        bookChangeListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                books.clear();
//                for (DataSnapshot bookSnap : dataSnapshot.getChildren()) {
//                    books.add(bookSnap.getValue(Book.class));
//                }
//                bookAdapter = new BookAdapter(books);
//                listBooks.setAdapter(bookAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        };
//        bookReference.addValueEventListener(bookChangeListener);

        notes = new ArrayList<>();
        notes.add(new Book("firebase push id","Swan trumpet", "BOOK", "E.B. White"));
        notes.add(new Book("firebase push id","Charlotte's web", "BOOK", "M. S."));

        if(notes.size()==0){
            blankMessage = root.findViewById(R.id.blankMessage);
            blankMessage.setVisibility(View.VISIBLE);
        }
        else{
            recyclerArea = root.findViewById(R.id.recycler_area);
            manager = new LinearLayoutManager(getContext());
            recyclerArea.setLayoutManager(manager);
            adapter = new BookAdapter(getActivity());
            recyclerArea.setAdapter(adapter);
            for(Book n: notes){
                adapter.addItem(n);
            }
        }
        return root;
    }
}
//package com.example.bucketlist.ui.books;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProviders;
//import androidx.recyclerview.widget.ItemTouchHelper;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.bucketlist.dao.BookDAO;
//import com.example.bucketlist.dao.Database;
//import com.example.bucketlist.model.Book;
//import com.example.bucketlist.R;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//public class BooksFragment extends Fragment {
//
//    private BooksViewModel booksViewModel;
//    View root;
//
//    RecyclerView listBooks;
//    BookAdapter bookAdapter;
//
//    DatabaseReference bookReference;
//    ValueEventListener bookChangeListener;
//
//    BookDAO bookDAO;
//
//    LinearLayout listLayout, newLayout;
//    ArrayList<Book> books;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        booksViewModel =
//                ViewModelProviders.of(this).get(BooksViewModel.class);
//        root = inflater.inflate(R.layout.fragment_books, container, false);
//        final TextView textView = root.findViewById(R.id.text_books);
//        booksViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        listLayout = root.findViewById(R.id.listLayout);
//        listBooks = root.findViewById(R.id.listBooks);
//        listBooks.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false));
//        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                Toast.makeText(root.getContext(), "on Move", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
//                Toast.makeText(root.getContext(), "on Swiped ", Toast.LENGTH_SHORT).show();
//                //Remove swiped item from list and notify the RecyclerView
//                int position = viewHolder.getAdapterPosition();
//                String bookId = bookAdapter.getBook(position).getId();
//                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                if (swipeDir == ItemTouchHelper.LEFT) {
//                    bookDAO.delete(userId, bookId);
////                    books.remove(position);
////                    bookAdapter.notifyItemRemoved(position);
//                }
////                arrayList.remove(position);
////                adapter.notifyDataSetChanged();
//
//            }
//        };
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
//        itemTouchHelper.attachToRecyclerView(listBooks);
//
//
//        bookDAO = Database.getBookDAO();
//        bookReference = bookDAO.getBookReference();
//        books = new ArrayList<>();
////        displayItems(categories);
//        bookChangeListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                books.clear();
//                for (DataSnapshot bookSnap : dataSnapshot.getChildren()) {
//                    books.add(bookSnap.getValue(Book.class));
//                }
//                bookAdapter = new BookAdapter(books);
//                listBooks.setAdapter(bookAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        };
//        bookReference.addValueEventListener(bookChangeListener);
//        return root;
//    }
//
//    public void displayItems(ArrayList<Book> categories){
////        for(Notes n : categories){
//        for(int i=0; i<3; i++){
//            newLayout = new LinearLayout(getContext());
//            newLayout.setOrientation(LinearLayout.VERTICAL);
//            newLayout.setLayoutParams(new ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT));
//            RelativeLayout.LayoutParams parameter = new RelativeLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.WRAP_CONTENT,
//                    RelativeLayout.LayoutParams.WRAP_CONTENT);
//            parameter.setMargins(30, 32, 10, 0); // left, top, right, bottom
//            newLayout.setLayoutParams(parameter);
//            newLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // go to details page of selected item
//                }
//            });
//
//            final TextView nameField = new TextView(getContext());
//            nameField.setLayoutParams(new ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT));
//            nameField.setTextSize(20);
//            nameField.setTextColor(Color.parseColor("#000000"));
//
//            final TextView catField = new TextView(getContext());
//            catField.setLayoutParams(new ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT));
//            catField.setTextColor(Color.parseColor("#7f7f7f"));
//            catField.setTextSize(14);
//
//            //sample books to show ui
//            nameField.setText("Book"+i);
//            catField.setText("BOOK");
//
////            nameField.setText(n.getName());
////            catField.setText(n.getCategory());
//
//            newLayout.addView(nameField);
//            newLayout.addView(catField);
//
//            listLayout.addView(newLayout);
//
//        }
//    }
//}