package com.example.doanmh.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.doanmh.MainActivity;
import com.example.doanmh.R;
import com.example.doanmh.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText edUserNameC;
    private EditText edPasswordC;
    private EditText edConfirmPasswordC;
    private EditText edEmailC;
    private EditText edPhoneNumberC;
    private RadioGroup rbSex;
    private  Button btnRegisterRe;
    private ImageButton imbBack;
    private ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    FirebaseDatabase firebaseDatabase;

    private final Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        getSupportActionBar().setTitle("Register");

        //lay du lieu
        anhxadulieu();
        taosukien();
    }
    void anhxadulieu(){
        edUserNameC = findViewById(R.id.edUserNameRe);
        edPasswordC = findViewById(R.id.edPasswordRe);
        edConfirmPasswordC = findViewById(R.id.edconfirm_password);
        edEmailC = findViewById(R.id.edEmail);
        edPhoneNumberC = findViewById(R.id.edPhone);
        rbSex = findViewById(R.id.rgSex);
        imbBack = findViewById(R.id.imbBack);
        btnRegisterRe = findViewById(R.id.btnRegisterRe);
        progressDialog = new ProgressDialog(this);
    }
    void taosukien()
    {
        btnRegisterRe.setOnClickListener(view -> sukienRegister());
        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, Login2Activity.class);
                startActivity(i);
            }
        });
    }

    private void sukienRegister() {
        String userName = edUserNameC.getText().toString().trim();
        String password = edPasswordC.getText().toString().trim();
        String confirmPassword = edConfirmPasswordC.getText().toString().trim();
        String email = edEmailC.getText().toString().trim();
        String phone = edPhoneNumberC.getText().toString().trim();
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        //neu sex = 1 la nam, sex = 0 la nu
        int sex =1 ;
        boolean isValid = checkUserName(userName) && checkPassword(password,confirmPassword);
        progressDialog.show();
        if (isValid){
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser user = auth.getCurrentUser();
                    Toast.makeText(RegisterActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                    //
                    updateUser();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterActivity.this, "Failed to create account", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void updateUser() {
        String uid = auth.getUid();
        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put("email",edEmailC.getText().toString());
        userInfo.put("name",edUserNameC.getText().toString());
        userInfo.put("phone number",edPhoneNumberC.getText().toString());
        userInfo.put("Role","");
        DatabaseReference df = FirebaseDatabase.getInstance().getReference("Users");
        df.child(uid)
                .setValue(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(RegisterActivity.this, "Account created", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Fail", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    //    void sukienRegister(){
//        //lay du lieu
//        String userName = edUserNameC.getText().toString().trim();
//        String password = edPasswordC.getText().toString().trim();
//        String confirmPassword = edConfirmPasswordC.getText().toString().trim();
//        String email = edEmailC.getText().toString().trim();
//        String phone = edPhoneNumberC.getText().toString().trim();
//        //neu sex = 1 la nam, sex = 0 la nu
//        int sex =1 ;
//        boolean isValid = checkUserName(userName) && checkPassword(password,confirmPassword);
//        if(isValid){
//            //neu hop le , tao doi tuong user de luu va share pre
//            User userNew =new User();
//            userNew.setUserName(userName);
//            userNew.setPassword(password);
//            userNew.setEmail(email);
//            userNew.setPhoneNumber(phone);
//            //lay radio button id dang dc checked
//            int sexSelected = rbSex.getCheckedRadioButtonId();
//            if (sexSelected == R.id.rbFemale){
//                sex = 0;
//            }
//            userNew.setSex(sex);
//            // vi user la 1 object nen convert qua string  voi fomat la json de luu vao share pre
//            String userStr = gson.toJson(userNew);
//            editor.putString(Utils.KEY_USER, userStr);
//            editor.commit();
//            //dung toast de show thong bao dang ky thanh cong
//            Toast.makeText(RegisterActivity.this,"Dang ky thanh cong",Toast.LENGTH_LONG).show();
//            finish();
//        }
//
//    }
    private  boolean checkUserName(String userName){
        if (userName.isEmpty()){
            edUserNameC.setError("Vui long nhap ten dang nhap");
            return false;
        }
        if(userName.length() <= 5){
            edUserNameC.setError("Ten dang nhap it nhat phai co 6 ki tu");
            return  false;
        }
        return true;
    }
    private boolean checkPassword(String password, String confirmPassword){
        if(password.isEmpty()){
            edPasswordC.setError("Vui long nhap mat khau");
            return  false;
        }
        if(password.length()<=5) {
            edPasswordC.setError("Mat khau phai lon hon 5 ki tu");
            return false;
        }
        if (!password.equals(confirmPassword)){
            edConfirmPasswordC.setError("Xac nhap mat khau khong hop le");
            return false;
        }
        return true;
    }
}