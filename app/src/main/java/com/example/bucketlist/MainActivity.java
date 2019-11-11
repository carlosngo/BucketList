package com.example.bucketlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.*;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText email, pwd;
    Button loginBtn, registerBtn;
    Intent intent;
    FrameLayout progressOverlay;
    private FirebaseAuth mAuth;
    String emailAddress, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
        email = (EditText)findViewById(R.id.txtName);
        pwd = (EditText)findViewById(R.id.txtPwd);
        loginBtn = (Button)findViewById(R.id.btnLogin);
        registerBtn = (Button)findViewById(R.id.btnRegister);
        progressOverlay = (FrameLayout) findViewById(R.id.progress_overlay);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressOverlay.setVisibility(View.VISIBLE);
                emailAddress = email.getText().toString();
                password = pwd.getText().toString();
                mAuth.signInWithEmailAndPassword(emailAddress, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressOverlay.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            startActivity(new Intent(MainActivity.this, LandingActivity.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(),""+task.getException(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, LandingActivity.class));
        }
    }

}
