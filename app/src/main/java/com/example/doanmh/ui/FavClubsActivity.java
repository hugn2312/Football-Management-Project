package com.example.doanmh.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.doanmh.R;

public class FavClubsActivity extends AppCompatActivity {
    RecyclerView rv_fav;
    RelativeLayout lay_fav;
    ImageView btback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_clubs);
        //

        rv_fav = findViewById(R.id.rv_fav);
        lay_fav = findViewById(R.id.lay_fav);
        btback = findViewById(R.id.btBackF);
        rv_fav.setLayoutManager(new LinearLayoutManager(this));
    }
}