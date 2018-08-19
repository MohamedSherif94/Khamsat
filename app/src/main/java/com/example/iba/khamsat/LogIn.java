package com.example.iba.khamsat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void logIn(View view) {
        Toast.makeText(this, "أهلا بيك فى IBA", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LogIn.this, SecondActivity.class);
        startActivity(intent);
    }
}
