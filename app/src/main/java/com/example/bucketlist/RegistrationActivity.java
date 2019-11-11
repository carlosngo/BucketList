package com.example.bucketlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.storage.StorageReference;

public class RegistrationActivity extends AppCompatActivity {

    EditText uname, pwd;
    Button registerBtn;
    Intent intent;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        uname = (EditText)findViewById(R.id.txtName);
        pwd = (EditText)findViewById(R.id.txtPwd);
        registerBtn = (Button)findViewById(R.id.btnRegister);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LandingActivity.class);
                startActivity(intent);
            }
        });
    }
}
