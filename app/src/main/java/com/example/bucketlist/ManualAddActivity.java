package com.example.bucketlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

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

                String nameInput = name.getText().toString();
                String category = selectedCategory.getText().toString();

                if(nameInput.length()==0 || category.length()==0){
                    Toast.makeText(ManualAddActivity.this,
                            "Please fill in all fields first.", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(ManualAddActivity.this,
                            "Successfully added " +name.getText().toString()+" ("+selectedCategory.getText()+")"+
                                    " to bucket list.", Toast.LENGTH_SHORT).show();
                    
                    if(description.getText().toString().length()==0){
                        String itemDescription = "No description.";
                    }
                    else{
                        String optionalDescription = description.getText().toString();
                    }
                    // add new Book object into db lines here

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
