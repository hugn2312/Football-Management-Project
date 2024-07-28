
package com.example.doanmh.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doanmh.R;
import com.example.doanmh.model.Clubs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ImportTablesActivity extends AppCompatActivity {

    private Uri filePath;
    private EditText edStt, edName, edPl, edGd, edPts, edLogo;
    private Button btSave, btShow;

    private FirebaseDatabase db;
    private DatabaseReference reference;
    private String uId, uSTT, uName, uPl,uGd,uPts,uImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_tables);
        //anh xa
        edStt = findViewById(R.id.edSTT);
        edName = findViewById(R.id.edNameClub);
        edPl = findViewById(R.id.edPL);
        edGd = findViewById(R.id.edGD);
        edPts = findViewById(R.id.edPts);
        edLogo = findViewById(R.id.edLogo);
        btSave = findViewById(R.id.btSave);
        btShow = findViewById(R.id.btShow);
        //
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stt = edStt.getText().toString();
                String name = edName.getText().toString();
                String pl = edPl.getText().toString();
                String gd = edGd.getText().toString();
                String pts = edPts.getText().toString();
                String logo = edLogo.getText().toString();

                if (!stt.isEmpty() && !name.isEmpty() && !pl.isEmpty() && !gd.isEmpty() && !pts.isEmpty()){
                    Clubs clubs = new Clubs(stt, name , pl, gd, pts, logo);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Club");
                    String pathObject = String.valueOf(clubs.getName());
                    reference.child(pathObject).setValue(clubs).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            edStt.setText("");
                            edName.setText("");
                            edPl.setText("");
                            edGd.setText("");
                            edPts.setText("");
                            edLogo.setText("");

                            Toast.makeText(ImportTablesActivity.this,"Successfuly Updated",Toast.LENGTH_SHORT).show();

                        }
                    });

                }

            }
        });
        btShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ImportTablesActivity.this, TablesAdmin.class));
            }
        });

    }
}
