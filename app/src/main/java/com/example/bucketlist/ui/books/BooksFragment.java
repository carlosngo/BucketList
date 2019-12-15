package com.example.bucketlist.ui.books;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bucketlist.dao.*;
import com.example.bucketlist.model.*;
import com.example.bucketlist.R;
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
    private ProgressBar loader;

    private String userId;

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
        loader = root.findViewById(R.id.loader);
        listBooks = root.findViewById(R.id.listBooks);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        listBooks.setLayoutManager(layoutManager);
        listBooks.addItemDecoration(new DividerItemDecoration(listBooks.getContext(),
                layoutManager.getOrientation()));
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
                loader.setVisibility(View.VISIBLE);
                blankMessage.setVisibility(View.GONE);
                bookAdapter.clear();
                listBooks.setAdapter(bookAdapter);
                if (dataSnapshot.getChildrenCount() == 0) {
                    loader.setVisibility(View.GONE);
                    blankMessage.setVisibility(View.VISIBLE);
                } else {
                    for (DataSnapshot bookSnap : dataSnapshot.getChildren()) {
                        String bookId = bookSnap.getKey();
                        bookReference.child(bookId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                loader.setVisibility(View.GONE);
                                bookAdapter.addItem(dataSnapshot.getValue(Book.class));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
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