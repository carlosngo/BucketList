package com.example.bucketlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.bucketlist.dao.*;
import com.example.bucketlist.model.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;

public class ManualAddActivity extends AppCompatActivity {
    private RadioGroup categoryChoices;
    private RadioButton selectedCategory;
    private Button addBtn, backBtn;
    private EditText name, description;
    private StorageReference mStorageRef;
    private FrameLayout progressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_add);

        categoryChoices = (RadioGroup) findViewById(R.id.categoryChoices);
        addBtn = (Button) findViewById(R.id.addBtn);
        backBtn = (Button) findViewById(R.id.backBtn);
        name = (EditText) findViewById(R.id.name);
        description = (EditText) findViewById(R.id.description);
        progressbar = (FrameLayout) findViewById(R.id.progress_overlay);

        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int selectedId = categoryChoices.getCheckedRadioButtonId();
                selectedCategory = (RadioButton) findViewById(selectedId);

                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String nameInput = name.getText().toString();
                String descriptionInput;
                String category = selectedCategory.getText().toString();

                if(nameInput.length()==0 || category.length()==0){
                    Toast.makeText(ManualAddActivity.this,
                            "Please fill in all fields first.", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(ManualAddActivity.this,
                            "Successfully added " +name.getText().toString()+" ("+selectedCategory.getText()+")"+
                                    " to bucket list.", Toast.LENGTH_SHORT).show();
                    
                    if(description.getText().toString().length()==0){
                        descriptionInput = "No description.";
                    }
                    else{
                        descriptionInput = description.getText().toString();
                    }
                    String newId;
                    switch (category) {
                        case "Films":
                            FilmDAO movieDAO = Database.getMovieDAO();
                            Movie movie = new Movie();
                            movie.setName(nameInput);
                            movie.setDescription(descriptionInput);
                            newId = movieDAO.add(movie);
                            movieDAO.addMetadataUnderMovie(newId, userId);
                            break;
                        case "Books":
                            BookDAO bookDAO = Database.getBookDAO();
                            Book book = new Book();
                            book.setName(nameInput);
                            book.setDescription(descriptionInput);
                            newId = bookDAO.add(book);
                            bookDAO.addBookUnderUser(newId, userId);
                            break;
                        case "Games":
                            GameDAO gameDAO = Database.getGameDAO();
                            Game game = new Game();
                            game.setName(nameInput);
                            game.setDescription(descriptionInput);
                            newId = gameDAO.add(game);
                            gameDAO.addGameUnderUser(newId, userId);
                            break;
                        case "Series":
                            SeriesDAO seriesDAO = Database.getSeriesDAO();
                            Series series = new Series();
                            series.setName(nameInput);
                            series.setDescription(descriptionInput);
                            newId = seriesDAO.add(series);
                            seriesDAO.addMetadataUnderSeries(newId, userId);
                            break;
                        case "Goals":
                            GoalDAO goalDAO = Database.getGoalDAO();
                            Goal goal = new Goal();
                            goal.setName(nameInput);
                            goal.setDescription(descriptionInput);
                            newId = goalDAO.add(goal);
                            goalDAO.addMetadataUnderGoal(newId, userId);
                            break;
                    }
                    setResult(0);
                    finish();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //go back to search screen
                setResult(2);
                finish();
            }
        });
    }
}
