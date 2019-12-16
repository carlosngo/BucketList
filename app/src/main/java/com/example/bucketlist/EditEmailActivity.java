package com.example.bucketlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

public class EditEmailActivity extends AppCompatActivity {

    Button cancel, save;
    EditText emailInput;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    String email;
    FrameLayout progressOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_email);

        mAuth = FirebaseAuth.getInstance();
        cancel = (Button) findViewById(R.id.backBtn);
        save = (Button) findViewById(R.id.saveBtn);
        emailInput = (EditText) findViewById(R.id.emailInput);
        progressOverlay = (FrameLayout) findViewById(R.id.progress_overlay);
    }

    public void save(View v){
        email = emailInput.getText().toString();
        if(email.length()==0){
            Toast.makeText(getApplicationContext(),"Please enter an email",Toast.LENGTH_SHORT).show();
        } else{
            progressOverlay.setVisibility(View.VISIBLE);
            mAuth.getCurrentUser().updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    progressOverlay.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        try {
                            database.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child("email")
                                    .setValue(email);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(getApplicationContext(), "Update Successful.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        if(task.getException() instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(getApplicationContext(),"This email is already in use.",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Update Failed.",Toast.LENGTH_SHORT).show();
                        }                    }

                }
            });
        }
    }

    public void cancel(View v){
        finish();
    }
}
