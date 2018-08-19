package com.example.iba.khamsat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void signUp(View view) {
        Toast.makeText(this, "مبروك عليك التسجيل", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignUp.this, LogIn.class);
        startActivity(intent);
    }
}
