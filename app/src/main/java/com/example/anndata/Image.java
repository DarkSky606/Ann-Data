package com.example.anndata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Image extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView=findViewById(R.id.imageView);
        Intent intent=getIntent();
        String user=intent.getStringExtra("uname");

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("uname").equalTo(user);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Idp=snapshot.child(user).child("dp").getValue(String.class);

                Picasso.get()
                        .load(Idp)
                        .into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Image.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


    }
}