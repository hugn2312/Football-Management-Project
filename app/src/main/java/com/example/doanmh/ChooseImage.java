package com.example.doanmh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ChooseImage extends AppCompatActivity {

    private Button btnGetImage;
    private ImageView imageView;
    private EditText edNameImage;

    private Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    private final int PICK_IMAGE_REQUEST = 71;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_image);
        btnGetImage = (Button) findViewById(R.id.btnGetImage);
        edNameImage = findViewById(R.id.edNameImage);
        imageView = (ImageView) findViewById(R.id.imgView);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        btnGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(ChooseImage.this);
                progressDialog.setMessage("Fetching Image....");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String imageID = edNameImage.getText().toString();

                storageReference = FirebaseStorage.getInstance().getReference("images/"+imageID+".png");

                try {
                    File localfile = File.createTempFile("tempfile",".png");
                    storageReference.getFile(localfile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                    if (progressDialog.isShowing())
                                        progressDialog.dismiss();

                                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                                    imageView.setImageBitmap(bitmap);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (progressDialog.isShowing())
                                        progressDialog.dismiss();

                                    Toast.makeText(ChooseImage.this, "Failed to retrieve", Toast.LENGTH_SHORT).show();

                                }
                            });
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        });
    }
}