package com.example.bucketlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditAccountActivity extends AppCompatActivity {
    EditText email, pwd;
    Button editEmail, editPw, backBtn;
    Intent intent;
    FrameLayout progressOverlay;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        mAuth = FirebaseAuth.getInstance();
        email = (EditText)findViewById(R.id.txtName);
        pwd = (EditText)findViewById(R.id.txtPwd);
        email.setText(mAuth.getCurrentUser().getEmail());
        pwd.setText("*****");
        editEmail = (Button)findViewById(R.id.editEmail);
        editPw = (Button)findViewById(R.id.editPw);
        backBtn = (Button)findViewById(R.id.backBtn);
        progressOverlay = (FrameLayout) findViewById(R.id.progress_overlay);

    }

    public void editEmail(View v){
        startActivity(new Intent(EditAccountActivity.this, EditEmailActivity.class));
        finish();
    }

    public void editPw(View v){
        startActivity(new Intent(EditAccountActivity.this, EditPasswordActivity.class));
        finish();
    }

    public void back(View v){
        finish();
    }
}
