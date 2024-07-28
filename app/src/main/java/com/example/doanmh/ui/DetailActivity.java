package com.example.doanmh.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doanmh.Adapter.FavAdapter;
import com.example.doanmh.MyApplication;
import com.example.doanmh.R;
import com.example.doanmh.model.Team;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {

    TextView tvName, tvDetail,getUrl;
    ImageView imDetail, imStadium;
    Button btBack, btFav;
    private ArrayList<Team> lstTeam;
    private FavAdapter favAdapter;

    boolean isInMyFav = false;
    private FirebaseAuth firebaseAuth;
    String nameClub;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvName = findViewById(R.id.tvNameDe);
        imDetail = findViewById(R.id.imgDetail);
        tvDetail = findViewById(R.id.tvDetail);
        btBack = findViewById(R.id.Backbtn);
        imStadium = findViewById(R.id.imStadium);
        btFav = findViewById(R.id.btnFavourite);

//        Intent intent = getIntent();
//        nameClub = intent.getStringExtra("name");


        firebaseAuth = FirebaseAuth.getInstance();


        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Bundle bundle = getIntent().getExtras();
        if(bundle == null)
        {
            return;
        }
        Team team = (Team) bundle.get("object_team");
        tvName.setText(team.getName());
        tvDetail.setText(team.getDetail());
        Glide.with(imDetail.getContext())
                .load(team.getSurl())
                .placeholder(R.mipmap.ic_launcher_round)
                .centerCrop()
                .error(R.mipmap.ic_launcher_round)
                .into(imDetail);
        Glide.with(imStadium.getContext())
                .load(team.getSurl2())
                .placeholder(R.mipmap.ic_launcher_round)
                .centerCrop()
                .error(R.mipmap.ic_launcher_round)
                .into(imStadium);

        nameClub = tvName.getText().toString();
        if (firebaseAuth.getCurrentUser() != null)
        {
            checkIsFav(nameClub);
        }
        btFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firebaseAuth.getCurrentUser() == null){
                    Toast.makeText(DetailActivity.this, "You're not logged in", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (isInMyFav){
                        MyApplication.RemoveFromFavList(DetailActivity.this,nameClub);
                    }else {
                        MyApplication.addToFavList(DetailActivity.this,nameClub);
                    }
                }

            }
        });

    }
    private void checkIsFav(String nameClub){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid()).child("Favourites").child(nameClub)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        isInMyFav = snapshot.exists();
                        if (isInMyFav){
                            btFav.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.baseline_favorite_24, 0, 0);
                            btFav.setText("Remove Favourite");
                            btFav.setTextColor(getApplication().getResources().getColor(R.color.white));
                            btFav.setTextSize(20);
                        }
                        else{
                            btFav.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.baseline_favorite_border_24, 0, 0);
                            btFav.setText("Add Favourite");
                            btFav.setTextColor(getApplication().getResources().getColor(R.color.white));
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

}