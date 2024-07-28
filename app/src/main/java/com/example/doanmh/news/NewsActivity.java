package com.example.doanmh.news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.doanmh.R;

public class NewsActivity extends AppCompatActivity {
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        back = findViewById(R.id.btBack);
        back.setOnClickListener(view -> finish());
    }
}