package com.example.anndata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class register extends AppCompatActivity {

    EditText Fname,User,Email,Phone,Pass,Conpass;
    Button LoginBt,SignupBt;
    FirebaseDatabase rootNode;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Fname=findViewById(R.id.fullname);
        User=findViewById(R.id.uname);
        Email=findViewById(R.id.email);
        Phone=findViewById(R.id.phone);
        Pass=findViewById(R.id.pass);
        Conpass=findViewById(R.id.con_pass);
        LoginBt=findViewById(R.id.login_bt);
        SignupBt=findViewById(R.id.sign_upbt);

        String email = Email.getText().toString().trim();
        String password = Pass.getText().toString().trim();
        String user = User.getText().toString().trim();



        SignupBt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            reference=FirebaseDatabase.getInstance().getReference("Users");
            Query checkUser = reference.orderByChild("uname").equalTo(user);
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        User.setError("User Already Exists");
                    }else {
                        User.setError(null);
                    }
                    String name = Fname.getText().toString();
                    String username = User.getText().toString().trim();
                    String email = Email.getText().toString();
                    String phone = Phone.getText().toString();
                    String password = Pass.getText().toString();
                    String dp = "https://firebasestorage.googleapis.com/v0/b/anndata-e73e4.appspot.com/o/img700?alt=media&token=1600cf48-164f-43c0-8a6b-e061cd99fe92";
                    if(!validateUserName() | !validateName() | !validateEmail() | !validatePhone() | !validatePass() | !validateConPass()){
                        return;
                    }
                    UserHelperClass helperClass = new UserHelperClass(name, username, email, phone, password, dp);
                    reference.child(username).setValue(helperClass);
                    Toast.makeText(register.this, "Registered Successfully.Please Login Again To Continue", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(register.this, login.class);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    });

        LoginBt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(register.this,login.class);
            startActivity(intent);
        }
    });
}


    private Boolean validateName()
    {
        String val = Fname.getText().toString();
        String nameregex="^([a-zA-Z.\\s']{1,50})$";
        if(val.isEmpty()){
            Fname.setError("Field Cannot be Empty");
            return  false;
        } else if (!val.matches(nameregex)) {
            Fname.setError("Name cannot consist Special Characters");
            return false;
        } else{
            Fname.setError(null);
            return true;
        }
    }
    private Boolean validateUserName()
    {
        String val = User.getText().toString();
        if(val.isEmpty()){
            User.setError("Field Cannot be Empty");
            return  false;
        } else if (val.length()>=16) {
            User.setError("Username must be less than 16 digits");
            return false;
        } else if (val.contains(" ")) {
            User.setError("WhiteSpaces Are Not Allowed");
            return  false;
        } else{
            User.setError(null);
            return true;
        }
    }

    private Boolean validateEmail()
    {
        String val = Email.getText().toString();
        String emailPattern="^[A-Za-z0-9](([_\\.\\-]?[a-zA-Z0-9]+)*)@([A-Za-z0-9]+)(([\\.\\-]?[a-zA-Z0-9]+)*)\\.([A-Za-z]{2,})$";
        if(val.isEmpty()){
            Email.setError("Field Cannot be Empty");
            return  false;
        } else if (!val.matches(emailPattern)) {
            Email.setError("Invalid Email Adress");

            return  false;
        } else{
            Email.setError(null);
            return true;
        }
    }
    private Boolean validatePhone()
    {
        String val = Phone.getText().toString();
        String phoneregex="^([9]{1})([234789]{1})([0-9]{8})$";
        if(val.isEmpty()){
            Phone.setError("Field Cannot be Empty");
            return  false;
        } else if (val.matches(phoneregex)) {
            Phone.setError("Invalid Phone Number");
            return  false;
        } else if (val.length()!=10) {
            Phone.setError("Phone Number should have 10 digits");
            return  false;

        } else{
            Phone.setError(null);
            return true;
        }
    }
    private Boolean validatePass()
    {
        String val = Pass.getText().toString();
        String passregex="^(?=[^\\d_].*?\\d)\\w(\\w|[!@#$%]){7,20}";
        if(val.isEmpty()){
            Pass.setError("Field Cannot be Empty");
            return  false;

        } else if (!val.matches(passregex)) {
            Pass.setError("Password is too weak");
            return  false;
        } else{
            Pass.setError(null);
            return true;
        }
    }
    private Boolean validateConPass()
    {
        String val = Conpass.getText().toString();
        if(val.isEmpty()){
            Conpass.setError("Field Cannot be Empty");
            return  false;
        } else if (!val.equals(Pass.getText().toString())) {
            Conpass.setError("Password Mismatch");
            return  false;

        } else{
            Conpass.setError(null);
            return true;
        }
    }

}