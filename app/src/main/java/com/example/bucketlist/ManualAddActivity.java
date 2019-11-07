package com.example.bucketlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class ManualAddActivity extends AppCompatActivity {
    private RadioGroup categoryChoices;
    private RadioButton selectedCategory;
    private Button addBtn, backBtn;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoryChoices = (RadioGroup) findViewById(R.id.categoryChoices);
        addBtn = (Button) findViewById(R.id.addBtn);
//        backBtn = (Button) findViewById(R.id.backBtn);
        name = (EditText) findViewById(R.id.name);

        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int selectedId = categoryChoices.getCheckedRadioButtonId();
                selectedCategory = (RadioButton) findViewById(selectedId);
                Toast.makeText(ManualAddActivity.this,
                        "Successfully added " +name.getText().toString()+" ("+selectedCategory.getText()+")"+
                                " to bucket list.", Toast.LENGTH_SHORT).show();
                String nameInput = name.getText().toString();
                String category = selectedCategory.getText().toString();


                // add new Note object into db lines here

                setResult(0);
                finish();
            }
        });

//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) { go back to search screen
//                setResult(2);
//                finish();
//            }
//        });
    }
}
