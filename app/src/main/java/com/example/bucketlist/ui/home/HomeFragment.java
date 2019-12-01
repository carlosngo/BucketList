//package com.example.bucketlist.ui.home;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProviders;
//
//import com.example.bucketlist.R;
//import com.example.bucketlist.model.Book;
//
//import java.util.ArrayList;
//
//public class HomeFragment extends Fragment {
//
//    private HomeViewModel homeViewModel;
//    View root;
//    LinearLayout listLayout, newLayout;
//    ArrayList<Book> categories;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
//        root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        listLayout = root.findViewById(R.id.listLayout);
//        displayItems(listLayout, categories);
//        return root;
//    }
//
//    public void displayItems(LinearLayout linearLayout, ArrayList<Book> categories){
////        for(Notes n : categories){
//        for(int i=0; i<3; i++){
//                newLayout = new LinearLayout(getContext());
//                newLayout.setOrientation(LinearLayout.VERTICAL);
//                newLayout.setLayoutParams(new ViewGroup.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT));
//                RelativeLayout.LayoutParams parameter = new RelativeLayout.LayoutParams(
//                        RelativeLayout.LayoutParams.WRAP_CONTENT,
//                        RelativeLayout.LayoutParams.WRAP_CONTENT);
//                parameter.setMargins(30, 32, 10, 0); // left, top, right, bottom
//                newLayout.setLayoutParams(parameter);
//                newLayout.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // go to details page of selected item
//                    }
//                });
//
//                final TextView nameField = new TextView(getContext());
//                nameField.setLayoutParams(new ViewGroup.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT));
//                nameField.setTextSize(20);
//                nameField.setTextColor(Color.parseColor("#000000"));
//
//                final TextView catField = new TextView(getContext());
//                catField.setLayoutParams(new ViewGroup.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT));
//                catField.setTextColor(Color.parseColor("#7f7f7f"));
//                catField.setTextSize(14);
//
//            // sample data to show ui
//                nameField.setText("Book"+i);
//                catField.setText("item");
//
////            nameField.setText(n.getName());
////            catField.setText(n.getCategory());
//
//                newLayout.addView(nameField);
//                newLayout.addView(catField);
//
//                linearLayout.addView(newLayout);
//
//        }
//
//
//    }
//}
//

package com.example.bucketlist.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bucketlist.R;
import com.example.bucketlist.model.Note;
import com.example.bucketlist.ui.NoteAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    View root;

    private RecyclerView recyclerArea;
    private NoteAdapter adapter;
    private RecyclerView.LayoutManager manager;
    ArrayList<Note> notes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        recyclerArea = root.findViewById(R.id.recycler_area);
        //displayItems(listLayout, notes);

        //(2) The LinearLayoutManager is in-charge of the layout of the RecyclerView
        manager = new LinearLayoutManager(getContext());
        recyclerArea.setLayoutManager(manager);

        //(3) The RecyclerView.Adapter manages the internal content of the RecyclerView
        adapter = new NoteAdapter(getActivity());
        recyclerArea.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(root.getContext(), "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Toast.makeText(root.getContext(), "on Swiped ", Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();
                //String bookId = bookAdapter.getBook(position).getId();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (swipeDir == ItemTouchHelper.LEFT) {
//                    bookDAO.delete(userId, bookId);
//                    books.remove(position);
//                    bookAdapter.notifyItemRemoved(position);
                }
//                arrayList.remove(position);
//                adapter.notifyDataSetChanged();

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerArea);

        notes = new ArrayList<>();
        notes.add(new Note("firebase push id","Swan trumpet", "BOOK", "E.B. White"));
        notes.add(new Note("firebase push id","Charlotte's web", "BOOK", "M. S."));
        notes.add(new Note("firebase push id","Read book", "GOAL", "read at least 1 book"));
        notes.add(new Note("firebase push id","Get thin", "GOAL", "lose 300 pounds"));
        notes.add(new Note("firebase push id","One Piece", "SERIES", "Japanese Series"));
        notes.add(new Note("firebase push id","Probinsyano", "SERIES", "Filipino Series"));
        notes.add(new Note("firebase push id","Kimi no nawa", "FILM", "anime"));
        notes.add(new Note("firebase push id","Blank man the movie", "FILM", "Blank villain tries to take over the world, and gets whacked by blank man"));
        notes.add(new Note("firebase push id","Rock paper scissors online", "GAME", "Filipino game"));
        notes.add(new Note("firebase push id","Tumbang preso online", "GAME", "Filipino game"));
        for(Note n : notes){
//            String name = notes.get(i).getName();
//            String category = notes.get(i).getCategory();
//            String description = notes.get(i).getDescription();
//            adapter.addItem(name, category, description);
            adapter.addItem(n);
        }
        return root;
    }
}