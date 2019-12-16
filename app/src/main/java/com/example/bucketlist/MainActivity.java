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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    private EditText email, pwd;
    private Button loginBtn, registerBtn;
    private Intent intent;
    private FrameLayout progressOverlay;
    private FirebaseAuth mAuth;
    private String emailAddress, password;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        pref = getSharedPreferences("user_details", MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        email = (EditText)findViewById(R.id.txtName);
        pwd = (EditText)findViewById(R.id.txtPwd);
        loginBtn = (Button)findViewById(R.id.btnLogin);
        registerBtn = (Button)findViewById(R.id.btnRegister);
        progressOverlay = (FrameLayout) findViewById(R.id.progress_overlay);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailAddress = email.getText().toString();
                password = pwd.getText().toString();
                if(emailAddress.length()>0 && password.length()>0){
                    progressOverlay.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(emailAddress, generateHash(password)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressOverlay.setVisibility(View.GONE);
                            if(task.isSuccessful()){
//                                startActivity(new Intent(MainActivity.this, LandingActivity.class));
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("password", password);
                                editor.commit();
                                Intent intent = new Intent(MainActivity.this, LandingActivity.class);
//                                intent.putExtra("PW",password);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Invalid email or password.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please fill up all fields.",Toast.LENGTH_SHORT).show();
                }
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
//            startActivity(new Intent(MainActivity.this, LandingActivity.class));
//            Toast.makeText(getApplicationContext(),"Welcome "+mAuth.getCurrentUser().getEmail(),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LandingActivity.class);
//            intent.putExtra(pref.getString("password", null),"PW");
            startActivity(intent);
        }
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
