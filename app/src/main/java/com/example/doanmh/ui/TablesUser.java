package com.example.doanmh.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.doanmh.R;
import com.example.doanmh.model.Clubs;
import com.example.doanmh.Adapter.MyAdapter2;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class TablesUser extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter2 adapter2;
    private List<Clubs> lstClubs;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables_user);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lstClubs = new ArrayList<>();
        adapter2 = new MyAdapter2(lstClubs);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Club");
        recyclerView.setAdapter(adapter2);
        getListClubsFromRealtimeDatabase();
        //
        back = findViewById(R.id.btnBackU);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void getListClubsFromRealtimeDatabase() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Club");
        Query query = reference.orderByChild("pts");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Clubs clubs = snapshot.getValue(Clubs.class);
                if (clubs != null){
                    lstClubs.add(0, clubs);
                    adapter2.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}