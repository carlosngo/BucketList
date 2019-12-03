package com.example.bucketlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class EditPasswordActivity extends AppCompatActivity {

    Button cancel, save;
    EditText passwordInput;
    FirebaseAuth mAuth;
    FrameLayout progressOverlay;
    FirebaseDatabase database;
    String pw;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        pref = getSharedPreferences("user_details", MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        cancel = (Button) findViewById(R.id.backBtn);
        save = (Button) findViewById(R.id.saveBtn);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        progressOverlay = (FrameLayout) findViewById(R.id.progress_overlay);
    }


    public void save(View v){
        pw = passwordInput.getText().toString();
        if(pw.length()>=6){
            progressOverlay.setVisibility(View.VISIBLE);
            mAuth.getCurrentUser().updatePassword(pw).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    try {
                        database.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("password")
                                .setValue(pw);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                    progressOverlay.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("password", pw);
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "Update Successful.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Update Failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else{
            Toast.makeText(getApplicationContext(), "Password should be at least 6 characters.", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancel(View v){
        finish();
    }
}
