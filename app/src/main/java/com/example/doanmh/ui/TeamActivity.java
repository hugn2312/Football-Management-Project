package com.example.doanmh.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.doanmh.R;
import com.example.doanmh.model.Team;
import com.example.doanmh.Adapter.TeamAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class TeamActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TeamAdapter teamAdapter;

    private SearchView searchView;
    private List<Team> clublist;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        back = findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//      searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.rvTeam);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Team> options =
                new FirebaseRecyclerOptions.Builder<Team>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Teams"),Team.class )
                        .build();
        teamAdapter = new TeamAdapter(options, new TeamAdapter.IClickItemTeamList() {
            @Override
            public void onClickItemTeam(Team team) {
                onClickGotoDetail(team);
            }
        });

        recyclerView.setAdapter(teamAdapter);
        recyclerView.setItemAnimator(null);

    }

    @Override
    protected void onStart() {
        super.onStart();
        teamAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        teamAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_teams,menu);
        MenuItem item = menu.findItem(R.id.searchId);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                mySearch(newText);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mySearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void mySearch(String newText) {
        FirebaseRecyclerOptions<Team> options =
                new FirebaseRecyclerOptions.Builder<Team>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Teams").orderByChild("name").startAt(newText).endAt(newText+"uf8ff"),Team.class )
                        .build();
        teamAdapter = new TeamAdapter(options, new TeamAdapter.IClickItemTeamList() {
            @Override
            public void onClickItemTeam(Team team) {
                onClickGotoDetail(team);
            }
        });
        teamAdapter.startListening();
        recyclerView.setAdapter(teamAdapter);
    }
    private void onClickGotoDetail(Team team){
        Intent intent = new Intent(this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_team", team);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}