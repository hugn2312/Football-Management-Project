package com.example.doanmh.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doanmh.R;
import com.example.doanmh.Adapter.MyAdapter;
import com.example.doanmh.model.Clubs;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TablesAdmin extends AppCompatActivity {
    private RecyclerView recyclerView;

    private MyAdapter adapter;
    private List<Clubs> lstClubs;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private TablesAdmin activity;
    private Clubs clubs;
    Button back;

    CircleImageView btAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);
        back = findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lstClubs = new ArrayList<>();
        adapter = new MyAdapter(lstClubs, new MyAdapter.IClickListener() {
            @Override
            public void onCLickUpdateItem(Clubs clubs) {
                updateItem(clubs);
            }

            @Override
            public void OnClickDeleteItem(Clubs clubs) {
                deleteItem(clubs);
            }
        });


        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Club");

        recyclerView.setAdapter(adapter);
        getListClubsFromRealtimeDatabase();
        btAdd= findViewById(R.id.btAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TablesAdmin.this, ImportTablesActivity.class);
                startActivity(i);
            }
        });
    }

    private void deleteItem(Clubs clubs) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database = FirebaseDatabase.getInstance();
                        reference = database.getReference("Club");
                        reference.child(String.valueOf(clubs.getName())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(TablesAdmin.this, "Delete item sucessfull", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                })
                .setNegativeButton("Cancel",null)
                .show();
    }

    private void updateItem(Clubs clubs) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_update);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText edStt = dialog.findViewById(R.id.ed_Stt);
        EditText edName = dialog.findViewById(R.id.ed_Name);
        EditText edPl = dialog.findViewById(R.id.ed_Pl);
        EditText edGd = dialog.findViewById(R.id.ed_Gd);
        EditText edPts = dialog.findViewById(R.id.ed_Pts);
        Button btUpdate = dialog.findViewById(R.id.btUpdate);
        Button btCancel = dialog.findViewById(R.id.btCancel);

        edStt.setText(clubs.getStt());
        edName.setText(clubs.getName());
        edPl.setText(clubs.getPl());
        edGd.setText(clubs.getGd());
        edPts.setText(clubs.getPts());

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("Club");

                String newPts = edPts.getText().toString().trim();
                String newName = edName.getText().toString().trim();
                String newPl = edPl.getText().toString().trim();
                String newGd = edGd.getText().toString().trim();
                String newStt = edStt.getText().toString().trim();
                clubs.setPts(newPts);
                clubs.setName(newName);
                clubs.setPl(newPl);
                clubs.setGd(newGd);
                clubs.setStt(newStt);

                reference.child(String.valueOf(clubs.getName())).updateChildren(clubs.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(TablesAdmin.this, "Update Successfull", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

            }
        });

        dialog.show();
    }

    private void getListClubsFromRealtimeDatabase(){
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Club");
        Query query = reference.orderByChild("pts");

        query.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Clubs clubs = snapshot.getValue(Clubs.class);
                if (clubs != null){
                   lstClubs.add(0, clubs);
                   adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Clubs clubs = snapshot.getValue(Clubs.class);
                if (lstClubs == null || lstClubs.isEmpty() || clubs == null){
                    return ;}
                for (int i = 0; i <lstClubs.size() ; i++){
                    if (clubs.getName() == lstClubs.get(i).getName()){
                        lstClubs.set(i, clubs);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Clubs clubs = snapshot.getValue(Clubs.class);
                if (lstClubs == null || lstClubs.isEmpty() || clubs == null){
                    return ;}
                for (int i = 0; i <lstClubs.size() ; i++){
                    if (clubs.getName() == lstClubs.get(i).getName()){
                        lstClubs.remove(lstClubs.get(i));
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TablesAdmin.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });

    }
}