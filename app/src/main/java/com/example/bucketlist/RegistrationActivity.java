package com.example.bucketlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity {

    EditText email, pwd;
    Button registerBtn;
    Intent intent;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    String emailAddress, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        email = (EditText)findViewById(R.id.txtName);
        pwd = (EditText)findViewById(R.id.txtPwd);
        registerBtn = (Button)findViewById(R.id.btnRegister);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailAddress = email.getText().toString();
                password = pwd.getText().toString();
                if(emailAddress.length()==0 || password.length()==0){
                    Toast.makeText(RegistrationActivity.this,
                            "Please fill in all fields first.", Toast.LENGTH_SHORT).show();
                } else if(emailAddress.length()>0 && password.length()<6){
                    Toast.makeText(RegistrationActivity.this,
                            "Passwords should have a minimum length of 6.", Toast.LENGTH_SHORT).show();
                }else {
                    registerAccount();
                }
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();

        if(mAuth.getCurrentUser() != null){
            Toast.makeText(RegistrationActivity.this,
                    "Welcome" + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegistrationActivity.this, LandingActivity.class);
            startActivity(intent);
        }
    }

    public void registerAccount(){
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emailAddress, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            ArrayList<Note> notes = null;
                            User user = new User(emailAddress, password, notes);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getApplicationContext(),"Registration Successful.",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegistrationActivity.this, LandingActivity.class);
                                        startActivity(intent);
                                    } else {
                                        if(task.getException() instanceof FirebaseAuthUserCollisionException){progressBar.setVisibility(View.INVISIBLE);
                                            Toast.makeText(getApplicationContext(),"You are already registered.",Toast.LENGTH_SHORT).show();
                                        }else{
                                            progressBar.setVisibility(View.INVISIBLE);
                                            Toast.makeText(getApplicationContext(),""+task.getException(),Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            });
                        } else{
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),"Failed to register account."+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
