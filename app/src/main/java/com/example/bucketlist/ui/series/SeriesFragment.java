package com.example.bucketlist.ui.series;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bucketlist.R;
import com.example.bucketlist.model.Series;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class SeriesFragment extends Fragment {

    private SeriesViewModel mViewModel;
    private View root;
    private RecyclerView recyclerArea;
    private SeriesAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<Series> notes;
    private TextView blankMessage;

    public static SeriesFragment newInstance() {
        return new SeriesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_series, container, false);

        mViewModel =
                ViewModelProviders.of(this).get(SeriesViewModel.class);
//        root = inflater.inflate(R.layout.fragment_notifications, container, false);
        root = inflater.inflate(R.layout.fragment_series, container, false);
        final TextView textView = root.findViewById(R.id.text_series);
        mViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        notes = new ArrayList<>();
        notes.add(new Series("firebase push id","One Piece", "SERIES", "Japanese Series"));
        notes.add(new Series("firebase push id","Probinsyano", "SERIES", "Filipino Series"));

        if(notes.size()==0){
            blankMessage = root.findViewById(R.id.blankMessage);
            blankMessage.setVisibility(View.VISIBLE);
        }
        else{
            blankMessage.setVisibility(View.GONE);
            recyclerArea = root.findViewById(R.id.recycler_area);
            manager = new LinearLayoutManager(getContext());
            recyclerArea.setLayoutManager(manager);
            adapter = new SeriesAdapter(getActivity());
            recyclerArea.setAdapter(adapter);
            for(Series n : notes){
                adapter.addItem(n);
            }
        }
        return root;
    }
}