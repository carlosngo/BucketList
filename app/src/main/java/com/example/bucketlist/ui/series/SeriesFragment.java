package com.example.bucketlist.ui.series;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bucketlist.R;
import com.example.bucketlist.dao.Database;
import com.example.bucketlist.dao.SeriesDAO;
import com.example.bucketlist.model.Series;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SeriesFragment extends Fragment {

    RecyclerView listSeries;
    SeriesAdapter seriesAdapter;

    DatabaseReference seriesReference;
    DatabaseReference userSeriesReference;
    ValueEventListener seriesChangeListener;

    SeriesDAO seriesDAO;
    private SeriesViewModel mViewModel;
    private View root;
    private RecyclerView recyclerArea;
    private SeriesAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<Series> notes;
    private TextView blankMessage;

    private String userId;

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

        listSeries = root.findViewById(R.id.listSeries);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        listSeries.setLayoutManager(layoutManager);
        listSeries.addItemDecoration(new DividerItemDecoration(listSeries.getContext(),
                layoutManager.getOrientation()));
        blankMessage = root.findViewById(R.id.blankMessage);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        seriesDAO = Database.getSeriesDAO();
        seriesReference = seriesDAO.getSeriesReference();
        userSeriesReference = seriesDAO.getSeriesUnderUserReference(userId);
        seriesAdapter = new SeriesAdapter(getActivity());
        listSeries.setAdapter(seriesAdapter);

        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(getActivity(), "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                int position = viewHolder.getAdapterPosition();
                String bookId = seriesAdapter.getSeries(position).getId();
                seriesDAO.delete(userId, bookId);
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(listSeries);

        seriesChangeListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                seriesAdapter.clear();

                listSeries.setAdapter(seriesAdapter);
                for (DataSnapshot bookSnap : dataSnapshot.getChildren()) {
                    String bookId = bookSnap.getKey();
                    seriesReference.child(bookId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            seriesAdapter.addItem(dataSnapshot.getValue(Series.class));
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
        userSeriesReference.addValueEventListener(seriesChangeListener);
        return root;
    }
}