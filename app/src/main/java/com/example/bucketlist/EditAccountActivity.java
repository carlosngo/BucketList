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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditAccountActivity extends AppCompatActivity {
    private TextView email, pwd;
    private Button editEmail, editPw, backBtn;
    private Intent intent;
    private FirebaseAuth mAuth;
    private SharedPreferences pref;
    private FloatingActionButton logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        pref = getSharedPreferences("user_details", MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        logOut = (FloatingActionButton) findViewById(R.id.logOut);
        email = (TextView) findViewById(R.id.txtName);
        pwd = (TextView)findViewById(R.id.txtPwd);
        email.setText(mAuth.getCurrentUser().getEmail());
        pwd.setText(pref.getString("password","Could not load pw"));
//        editEmail = (Button)findViewById(R.id.editEmail);
//        editPw = (Button)findViewById(R.id.editPw);
//        backBtn = (Button)findViewById(R.id.backBtn);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Log Out",Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                finish();
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();

                startActivity(new Intent(EditAccountActivity.this, MainActivity.class));
            }
        });
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
