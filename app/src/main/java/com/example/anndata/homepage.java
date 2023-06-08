package com.example.anndata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class homepage extends AppCompatActivity {

    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6,cardView7,cardView8;
    ImageView pro,i10;
    private ImageSlider imageSlider;
    String _dp;

    SharedPreferences sharedPreferences;
    private  static  final String SHARED_PREF_NAME="myref";
    private  static  final String KEY_NAME="name";

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        cardView1 = findViewById(R.id.cv1);
        cardView2 = findViewById(R.id.cv2);
        cardView3 = findViewById(R.id.cv3);
        cardView4 = findViewById(R.id.cv4);
        cardView5 = findViewById(R.id.cv5);
        cardView6 = findViewById(R.id.cv6);
        cardView7 = findViewById(R.id.bp);
        cardView8 = findViewById(R.id.savefood);
        pro = (ImageView) findViewById(R.id.prof);
        imageSlider=findViewById(R.id.img_slider);
        i10=findViewById(R.id.i1);
        i10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),aboutus.class);
                startActivity(intent);
            }
        });
        ArrayList<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.donateimg, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.background1, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels,ScaleTypes.FIT);
        cardView7.setVisibility(View.INVISIBLE);
        cardView8.setVisibility(View.INVISIBLE);

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME,null);

        Intent intent=getIntent();
        String Ddp = intent.getStringExtra("dp");
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("uname").equalTo(name);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    _dp=snapshot.child(name).child("dp").getValue(String.class);

                    Picasso.get()
                            .load(_dp)
                            .into(pro);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(homepage.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });



        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(homepage.this,donate.class);
                i1.putExtra("uname",name);
                i1.putExtra("dp",Ddp);
                startActivity(i1);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(homepage.this,recieve.class);
                i2.putExtra("uname",name);
                startActivity(i2);
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.equals("sky")) {
                    Intent i3 = new Intent(homepage.this, admin.class);
                    i3.putExtra("uname", name);
                    startActivity(i3);
                }else {
                    Toast.makeText(homepage.this, "This account doesn't belong to admin", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i4=new Intent(homepage.this,contribution.class);
                i4.putExtra("uname",name);
                startActivity(i4);
            }
        });
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i5=new Intent(homepage.this,partners.class);
                startActivity(i5);
            }
        });
        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i6=new Intent(homepage.this,aboutus.class);
                startActivity(i6);
            }
        });

      pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homepage.this,profile.class);
                intent.putExtra("username",name);
                startActivity(intent);
            }
        });
    }
}