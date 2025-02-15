package com.example.bucketlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.bucketlist.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegistrationActivity extends AppCompatActivity {

    private EditText email, pwd;
    private Button registerBtn, backbtn;
    private FirebaseAuth mAuth;
    private FrameLayout progressOverlay;
    private String emailAddress, password;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        pref = getSharedPreferences("user_details", MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        email = (EditText)findViewById(R.id.txtName);
        pwd = (EditText)findViewById(R.id.txtPwd);
        registerBtn = (Button)findViewById(R.id.btnRegister);
        backbtn = (Button)findViewById(R.id.backBtn);
        progressOverlay = (FrameLayout) findViewById(R.id.progress_overlay);
        progressOverlay.setVisibility(View.INVISIBLE);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailAddress = email.getText().toString();
                password = pwd.getText().toString();
                email.setText("");
                pwd.setText("");
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

    public void back(View v){
        finish();
    }

//    @Override
//    protected void onStart(){
//        super.onStart();
//
//        if(mAuth.getCurrentUser() != null){
//            Toast.makeText(RegistrationActivity.this,
//                    "Welcome" + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(RegistrationActivity.this, LandingActivity.class);
//            startActivity(intent);
//        }
//    }

    public void registerAccount(){
        progressOverlay.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emailAddress, generateHash(password))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(emailAddress, generateHash(password));
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        progressOverlay.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getApplicationContext(),"Registration Successful.",Toast.LENGTH_SHORT).show();
                                        finish();
//                                        Intent intent = new Intent(RegistrationActivity.this, LandingActivity.class);
//                                        startActivity(intent);

                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("password", password);
                                        editor.commit();
                                        Intent intent = new Intent(RegistrationActivity.this, LandingActivity.class);
//                                        intent.putExtra("PW",password);
                                        startActivity(intent);
                                    } else {
                                        progressOverlay.setVisibility(View.INVISIBLE);
                                        if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                            Toast.makeText(getApplicationContext(),"This email is already registered.",Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(getApplicationContext(),""+task.getException(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                        } else{
                            progressOverlay.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),"Failed to register account. Please enter a valid email address.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public String generateHash(String input) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            Toast.makeText(getApplicationContext(),"NoSuchAlgorithmException",Toast.LENGTH_SHORT).show();
        }

        return hash.toString();
    }
}
