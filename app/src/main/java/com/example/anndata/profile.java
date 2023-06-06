package com.example.anndata;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.Random;

public class profile extends AppCompatActivity {

    de.hdodenhof.circleimageview.CircleImageView pDp,addP;
    TextView pName,pUser;
    EditText pFulln,pEmail,pPhone;
    ImageView LogOut;
    Bitmap bitmap;
    Uri filepath;
    Button save;

    String _user,Dname,Duser,Demail,Dphone,Ddp,url;
    SharedPreferences sharedPreferences;
    private  static  final String SHARED_PREF_NAME="myref";
    private  static  final String KEY_NAME="name";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        pDp=findViewById(R.id.dp);
        addP=findViewById(R.id.addp);
        pName=findViewById(R.id.fullN);
        pUser=findViewById(R.id.userN);
        pFulln=findViewById(R.id.fullname);
        pEmail=findViewById(R.id.email);
        pPhone=findViewById(R.id.phone);
        LogOut=findViewById(R.id.logout);
        save=findViewById(R.id.sv);

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        Intent intent = getIntent();
        _user=intent.getStringExtra("username");
        showAllUser();

        save.setVisibility(View.INVISIBLE);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");
                reference.child(_user).child("dp").setValue(url.toString());
                Intent intent = new Intent(profile.this,homepage.class);
                intent.putExtra("dp",Ddp);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Toast.makeText(profile.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(profile.this);
                builder.setTitle("Are You Sure ?");
                builder.setMessage("You Want To Log Out !");
                builder.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        Toast.makeText(profile.this, "Logged Out Successfully.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(profile.this,login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(profile.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });







        pDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(profile.this,Image.class);
                intent1.putExtra("uname",Duser);
                startActivity(intent1);
            }
        });
        addP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(profile.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){

                }
                Dexter.withActivity(profile.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent =new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image"),1);
                            }
                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                            }
                        }).check();
            }
        });
    }

    public void showAllUser() {

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("uname").equalTo(_user);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    Dname =snapshot.child(_user).child("name").getValue(String.class);
                    Duser  =snapshot.child(_user).child("uname").getValue(String.class);
                    Demail =snapshot.child(_user).child("email").getValue(String.class);
                    Dphone =snapshot.child(_user).child("phone").getValue(String.class);
                    Ddp =snapshot.child(_user).child("dp").getValue(String.class);

                    Picasso.get()
                                    .load(Ddp)
                                            .into(pDp);
                    pPhone.setText(Dphone);
                    pEmail.setText(Demail);
                    pUser.setText("@"+Duser);
                    pName.setText("Hey, "+Dname);
                    pFulln.setText(Dname);

                }else {
                    Toast.makeText(profile.this, "Data Not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(profile.this, "Database Error", Toast.LENGTH_SHORT).show();

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1 && resultCode==RESULT_OK){
            filepath=data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                pDp.setImageBitmap(bitmap);
                upload();
            }catch(Exception ex){
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void upload() {
        ProgressDialog dialog= new ProgressDialog(this);
        dialog.setTitle("Uploading File");
        dialog.show();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference uploader = storage.getReference("img"+""+new Random().nextInt(1000)+""+_user);
        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                             uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                 @Override
                                 public void onSuccess(Uri uri) {
                                     Toast.makeText(profile.this, "File Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                     dialog.dismiss();
                                     url=uri.toString().trim();
                                     save.setVisibility(View.VISIBLE);
                                 }
                             });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent = (100* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        dialog.setMessage("Uploaded : "+(int)percent+"%");
                        Toast.makeText(profile.this, "UPLOADING", Toast.LENGTH_SHORT).show();
                    }
                });
    }




}