package com.example.doanmh.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.doanmh.Adapter.FavAdapter;
import com.example.doanmh.MainActivity;
import com.example.doanmh.R;
import com.example.doanmh.model.Team;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    FirebaseAuth auth;
    ImageButton back;
    TextView TvName , TvEmail;
    private RecyclerView recyclerView;
    private ArrayList<Team> lstTeam;
    private FavAdapter favAdapter;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "PROFILE_TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TvName = findViewById(R.id.nameTv);
        TvEmail = findViewById(R.id.emailTv);
        auth = FirebaseAuth.getInstance();
        back = findViewById(R.id.Backbtn);
//        recyclerView = findViewById(R.id.rvfavClub);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseAuth = FirebaseAuth.getInstance();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        if (auth.getCurrentUser() == null){
            return;
        }else {
            loadUserInfo();
        }

//        loadFavClub();
    }

    private void loadUserInfo() {
        Log.d(TAG, "loadUserInfo: Loading user info of user"+ auth.getUid());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(auth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = ""+snapshot.child("name").getValue();
                        String email = ""+snapshot.child("email").getValue();
                        TvName.setText(name);
                        TvEmail.setText(email);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void loadFavClub() {
        lstTeam = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid()).child("Favourites")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        lstTeam.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            String name = ""+ds.child("name").getValue();
                            Team team1 = new Team();
                            team1.setName(name);
                            lstTeam.add(team1);
                        }

                        favAdapter = new FavAdapter(ProfileActivity.this, lstTeam);
                        recyclerView.setAdapter(favAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}