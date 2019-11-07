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
                        selectedCategory.getText() + name.getText().toString(), Toast.LENGTH_SHORT).show();

                // add new Note object into db
                String nameInput = name.getText().toString();
                String category = selectedCategory.getText().toString();
            }
        });

//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // go back to search screen
//            }
//        });
    }
}
