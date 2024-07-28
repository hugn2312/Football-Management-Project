package com.example.doanmh.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doanmh.MainActivity;
import com.example.doanmh.R;
import com.example.doanmh.intro.WelcomeActivity;
import com.example.doanmh.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

public class Login2Activity extends AppCompatActivity {
    EditText edEmail,edPassword;

    Button btLogin;
    Button btRegister, btBack;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    private ProgressDialog progressDialog;
    private  final  Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        //        getSupportActionBar().setTitle("Login");
        //
        anhxa();
        //anh xa
        //
        taosukien();
    }
    private void anhxa() {
        progressDialog = new ProgressDialog(this);
        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btnRegister);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        btBack = findViewById(R.id.btBack);
    }
    private void taosukien(){
        btLogin.setOnClickListener(view -> onLickSignIn());
        btRegister.setOnClickListener(funRegister());
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login2Activity.this,LogInActivity.class);
                startActivity(i);
            }
        });
    }

    private void onLickSignIn() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();

        boolean isValid =checkEmail(email) && checkPassword(password);
        progressDialog.show();
        if (isValid){
            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            Toast.makeText(Login2Activity.this, "Login  Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                            checkUserAccessLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login2Activity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    private void checkUserAccessLevel(String uid) {
        DocumentReference df = firestore.collection("Users").document(uid);
        //
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess: " + documentSnapshot.getData() );
                //

                if(documentSnapshot.getString("isAdmin") != null){
                    //
                    startActivity(new Intent(getApplicationContext(), TablesAdmin.class));
                    finish();
                }
                if (documentSnapshot.getString("isUser") != null){
                    startActivity(new Intent(getApplicationContext(), TablesUser.class));
                    finish();
                }
            }
        });
    }


    private boolean checkPassword(String password) {
        if(password == null || password.isEmpty())
        {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkEmail(String email) {
        if(email == null || email.isEmpty())
        {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @NonNull
    private View.OnClickListener funRegister(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login2Activity.this, RegisterActivity.class);
                startActivity(i);
            }
        };
    }
}
