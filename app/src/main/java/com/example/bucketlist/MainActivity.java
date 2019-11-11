package com.example.bucketlist;

import androidx.appcompat.app.AppCompatActivity;
import android.content.*;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText email, pwd;
    Button loginBtn, registerBtn;
    Intent intent;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        email = (EditText)findViewById(R.id.txtName);
        pwd = (EditText)findViewById(R.id.txtPwd);
        loginBtn = (Button)findViewById(R.id.btnLogin);
        registerBtn = (Button)findViewById(R.id.btnRegister);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = email.getText().toString();
                String password = pwd.getText().toString();
                if(emailAddress.equals("user") && password.equals("pw")){
                    email.setText("");
                    pwd.setText("");

                    Toast.makeText(getApplicationContext(),"Log In Successful",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, LandingActivity.class);
                    startActivity(intent);
                } else if(emailAddress.length()==0 || password.length()==0){
                    Toast.makeText(MainActivity.this,
                            "Please fill in all fields first.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Username and Password combination is invalid",Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }


}
