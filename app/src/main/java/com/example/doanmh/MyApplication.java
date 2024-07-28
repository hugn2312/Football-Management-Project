package com.example.doanmh;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MyApplication extends Application {
    public static void addToFavList(Context context,String name) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() == null){
            Toast.makeText(context, "You're not logged in", Toast.LENGTH_SHORT).show();
        }
        else {
            //
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("name", ""+name);
            //
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            ref.child(auth.getUid()).child("Favourites").child(name)
                    .setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "Added to your favourites list...", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Failed to added to your favourites due to"+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }
    public static void RemoveFromFavList(Context context,String name){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() == null){
            Toast.makeText(context, "You're not logged in", Toast.LENGTH_SHORT).show();
        }
        else {

            //
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            ref.child(auth.getUid()).child("Favourites").child(name)
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "Removed from your favourites list...", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Failed to remove from favourites due to"+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }

    }
}
