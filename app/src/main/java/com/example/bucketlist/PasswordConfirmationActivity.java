package com.example.bucketlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PasswordConfirmationActivity extends AppCompatActivity {
    Button editAccount, backBtn;
    EditText passwordInput;
    String choice, pw;
    FirebaseAuth mAuth;
    FrameLayout progressOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_confirmation);

        Intent intent = getIntent();
        choice = intent.getStringExtra("CHOICE");
        mAuth = FirebaseAuth.getInstance();
        editAccount = (Button) findViewById(R.id.editAccount);
        backBtn = (Button) findViewById(R.id.backBtn);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        progressOverlay = (FrameLayout) findViewById(R.id.progress_overlay);
    }

    public void editAccount(View v){
        progressOverlay.setVisibility(View.VISIBLE);
        pw = passwordInput.getText().toString();
        if(pw.length()==0){
            progressOverlay.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),"Password is required",Toast.LENGTH_SHORT).show();
        } else {
            AuthCredential credential = EmailAuthProvider.getCredential(mAuth.getCurrentUser().getEmail(),pw);
            mAuth.getCurrentUser().reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressOverlay.setVisibility(View.GONE);
                            if(task.isSuccessful()){
//                                startActivity(new Intent(PasswordConfirmationActivity.this, EditAccountActivity.class));
                                if(choice.equals("EMAIL")){
                                    startActivity(new Intent(PasswordConfirmationActivity.this, EditEmailActivity.class));
                                }
                                else if(choice.equals("PASSWORD")){
                                    startActivity(new Intent(PasswordConfirmationActivity.this, EditPasswordActivity.class));
                                }
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid Password"+task.getException(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
    public void cancel(View v){
        finish();
    }
}

