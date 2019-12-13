package com.example.bucketlist.ui.books;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bucketlist.dao.*;
import com.example.bucketlist.model.*;
import com.example.bucketlist.R;
import com.example.bucketlist.ui.ConfirmationPopup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BooksFragment extends Fragment {

    RecyclerView listBooks;
    BookAdapter bookAdapter;

    DatabaseReference bookReference;
    DatabaseReference userBookReference;
    ValueEventListener bookChangeListener;

    BookDAO bookDAO;
    private BooksViewModel booksViewModel;
    private View root;
    private RecyclerView recyclerArea;
    private RecyclerView.LayoutManager manager;
    private TextView blankMessage;

    private String userId;

    ConfirmationPopup pop;
    Context context;
    ConstraintLayout constraintLayout;
    Activity activity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getBaseContext();

        activity = getActivity();
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
        constraintLayout = root.findViewById(R.id.bookLayout);
        listBooks = root.findViewById(R.id.listBooks);
        listBooks.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false));
        blankMessage = root.findViewById(R.id.blankMessage);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        bookDAO = Database.getBookDAO();
        bookReference = bookDAO.getBookReference();
        userBookReference = bookDAO.getBookUnderUserReference(userId);
        bookAdapter = new BookAdapter(getActivity());
        listBooks.setAdapter(bookAdapter);


        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(getActivity(), "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                pop.popup(context, constraintLayout, activity);
                int position = viewHolder.getAdapterPosition();
                String bookId = bookAdapter.getBook(position).getId();
                bookDAO.delete(userId, bookId);
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(listBooks);



//        displayItems(categories);
        bookChangeListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookAdapter.clear();

                listBooks.setAdapter(bookAdapter);
                for (DataSnapshot bookSnap : dataSnapshot.getChildren()) {
                    String bookId = bookSnap.getKey();
                    bookReference.child(bookId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            bookAdapter.addItem(dataSnapshot.getValue(Book.class));
                            blankMessage.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        userBookReference.addValueEventListener(bookChangeListener);
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