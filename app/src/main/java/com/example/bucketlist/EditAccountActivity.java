package com.example.bucketlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditAccountActivity extends AppCompatActivity {
    private TextView email, pwd;
    private Button editEmail, editPw, backBtn;
    private Intent intent;
    private FirebaseAuth mAuth;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        pref = getSharedPreferences("user_details", MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        email = (TextView) findViewById(R.id.txtName);
        pwd = (TextView)findViewById(R.id.txtPwd);
        email.setText(mAuth.getCurrentUser().getEmail());
        pwd.setText(pref.getString("password","Could not load pw"));
//        editEmail = (Button)findViewById(R.id.editEmail);
//        editPw = (Button)findViewById(R.id.editPw);
//        backBtn = (Button)findViewById(R.id.backBtn);

    }

    public void editEmail(View v){
//        startActivity(new Intent(EditAccountActivity.this, EditEmailActivity.class));
        Intent intent = new Intent(this, PasswordConfirmationActivity.class);
        intent.putExtra("CHOICE","EMAIL");
        startActivity(intent);
        finish();
    }

    public void editPw(View v){
//        startActivity(new Intent(EditAccountActivity.this, EditPasswordActivity.class));
        Intent intent = new Intent(this, PasswordConfirmationActivity.class);
        intent.putExtra("CHOICE","PASSWORD");
        startActivity(intent);
        finish();
    }

    public void back(View v){
        finish();
    }
}
