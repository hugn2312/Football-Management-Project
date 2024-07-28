package com.example.doanmh.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.doanmh.MainActivity;
import com.example.doanmh.R;
import com.example.doanmh.intro.WelcomeActivity;

public class LogInActivity extends AppCompatActivity {
Button btLogin;
Button btStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        btLogin = findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LogInActivity.this, Login2Activity.class);
                startActivity(i);
            }
        });
        btStart = findViewById(R.id.btStart);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LogInActivity.this, WelcomeActivity.class);
                startActivity(i);
            }
        });
    }
}