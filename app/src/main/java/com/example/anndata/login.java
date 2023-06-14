package com.example.anndata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    ImageView logo;
    EditText User,Pass;
    TextView Fpass;
    String proUname,proDp;
    Button Login,Signup;
    SharedPreferences sharedPreferences;
    private  static  final String SHARED_PREF_NAME="myref";
    private  static  final String KEY_NAME="name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logo=findViewById(R.id.logo);
        User=findViewById(R.id.uname);
        Pass=findViewById(R.id.pass);
        Login=findViewById(R.id.UpdateBtn);
        Signup=findViewById(R.id.signupbt);

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME,null);

        if (name!=null)
        {
            startActivity(new Intent(login.this,homepage.class));
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUserName() | !validatePass()){
                    return;
                }
                else {
                    isUser();
                }
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this,register.class);
                startActivity(intent);
            }
        });
    }

    private void isUser() {
        String ueUser=User.getText().toString().trim();
        String uePass=Pass.getText().toString().trim();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("uname").equalTo(ueUser);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    User.setError(null);
                    String PassDB =snapshot.child(ueUser).child("pass").getValue(String.class);
                    if (PassDB.equals(uePass)){
                        Pass.setError(null);
                        proUname =snapshot.child(ueUser).child("uname").getValue(String.class);
                        proDp =snapshot.child(ueUser).child("dp").getValue(String.class);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_NAME,ueUser);
                        editor.apply();
                        Dialog dialog= new Dialog(login.this);
                        dialog.setTitle("Logging In");
                        dialog.show();
                        Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(getApplicationContext(),homepage.class);
                        intent1.putExtra("uname",proUname);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        finish();
                    }
                    else {
                        Pass.setError("Wrong Password");
                    }
                }
                else {

                    User.setError("User Not Found");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(login.this, "data not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean validateUserName()
    {
        String WhiteSpace=" ";
        String val = User.getText().toString();
        if(val.isEmpty()){
            User.setError("Field Cannot be Empty");
            return  false;
        } else{
            User.setError(null);
            return true;
        }
    }

    private Boolean validatePass()
    {
        String val = Pass.getText().toString();
        if(val.isEmpty()){
            Pass.setError("Field Cannot be Empty");
            return  false;
        }  else{
            Pass.setError(null);
            return true;
        }
    }
}