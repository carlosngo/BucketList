package com.example.bucketlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class EditPasswordActivity extends AppCompatActivity {

    Button cancel, save;
    EditText passwordInput;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        mAuth = FirebaseAuth.getInstance();
        cancel = (Button) findViewById(R.id.backBtn);
        save = (Button) findViewById(R.id.saveBtn);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
    }


    public void save(View v){
        String pw = passwordInput.getText().toString();
        mAuth.getCurrentUser().updatePassword(pw).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                Toast.makeText(getApplicationContext(), "Update Successful.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Update Failed.", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }

    public void cancel(View v){
        finish();
    }
}
