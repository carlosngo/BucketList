package com.example.bucketlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.storage.StorageReference;

public class EditNoteActivity extends AppCompatActivity {
    private RadioGroup categoryChoices;
    private RadioButton selectedCategory;
    private Button editBtn, backBtn;
    private EditText name;
    private StorageReference mStorageRef;
    private FrameLayout progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        categoryChoices = (RadioGroup) findViewById(R.id.categoryChoices);
        editBtn = (Button) findViewById(R.id.addBtn);
        backBtn = (Button) findViewById(R.id.backBtn);
        name = (EditText) findViewById(R.id.name);
        progressbar = (FrameLayout) findViewById(R.id.progress_overlay);

        editBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int selectedId = categoryChoices.getCheckedRadioButtonId();
                selectedCategory = (RadioButton) findViewById(selectedId);
                String nameInput = name.getText().toString();
                String category = selectedCategory.getText().toString();

                if(nameInput.length()==0 || category.length()==0){
                    Toast.makeText(EditNoteActivity.this,
                            "Please fill in all fields first.", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(EditNoteActivity.this,
                            "Successfully updated " +name.getText().toString()+" ("+selectedCategory.getText()+")", Toast.LENGTH_SHORT).show();
                    // update Book object into db lines here


                    finish();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //go back to search screen
                finish();
            }
        });
    }
}
