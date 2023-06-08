package com.example.anndata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class donate extends AppCompatActivity {

    EditText Dname,DfItem,Dcktime,Dckdate,Ddesc,Dadress,DContact;
    Button Dsubmit;
    RadioGroup radioGroup;
    RadioButton radioButton;
    ImageView Sedate,Setime,i1;
    String Ftype,_dp;
    final Calendar mycal=Calendar.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        Dname=findViewById(R.id.nameD);
        DfItem=findViewById(R.id.fItem);
        radioGroup=findViewById(R.id.radioGroup);
        Dckdate=findViewById(R.id.cookDate);
        Dcktime=findViewById(R.id.cookTime);
        Ddesc=findViewById(R.id.desc);
        Dadress=findViewById(R.id.Address);
        DContact=findViewById(R.id.contact);
        Dsubmit=findViewById(R.id.submit);
        Sedate=findViewById(R.id.SeDate);
        Setime=findViewById(R.id.SeTime);
        i1=findViewById(R.id.i1);

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),homepage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String _user=intent.getStringExtra("uname");
        //String Ddp=intent.getStringExtra("dp");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        Query getdp = ref.orderByChild("uname").equalTo(_user);
        getdp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    _dp=snapshot.child(_user).child("dp").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Setime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime();
            }
        });

        Sedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });

        Dsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validateName() | !validatePhone() | !validateAdd() | !validateCkdate() | !validateCktime() | !validateFitems() ) {
                    return;
                }
                String name=Dname.getText().toString().trim();
                String Fitem=DfItem.getText().toString().trim();
                String Desc=Ddesc.getText().toString().trim();
                String Cktime=Dcktime.getText().toString().trim();
                String Ckdate=Dckdate.getText().toString().trim();
                String Add=Dadress.getText().toString().trim();
                String Cont=DContact.getText().toString().trim();
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Don");
                DonateHelperClass donateHelperClass=new DonateHelperClass(name,Fitem,Ftype,Desc,Ckdate,Cktime,Add,Cont,_user,_dp);
                reference.child("uname").equalTo(_user);
                reference.child(_user).setValue(donateHelperClass);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Toast.makeText(donate.this, "Donated Successfully.Thank You", Toast.LENGTH_SHORT).show();
                        AlertDialog dialog = new AlertDialog.Builder(donate.this).create();
                        dialog.setTitle("Thanks for your Contribution");
                        dialog.setButton(1,"ok",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(donate.this,homepage.class);
                                intent.putExtra("uname",_user);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });



    }

    public void checkedBt(View view){
        int radioId =radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(radioId);
        Ftype=radioButton.getText().toString();

    }
    private Boolean validateName() {
        String val = Dname.getText().toString();
        String nameregex = "^([a-zA-Z.\\s']{1,50})$";
        if (val.isEmpty()) {
            Dname.setError("Field Cannot be Empty");
            return false;
        } else if (!val.matches(nameregex)) {
            Dname.setError("Name cannot consist Special Characters");
            return false;
        } else {
            Dname.setError(null);
            return true;
        }
    }

    private Boolean validateFitems() {
        String val = DfItem.getText().toString();
        if (val.isEmpty()) {
            DfItem.setError("Field Cannot be Empty");
            return false;
        } else {
            DfItem.setError(null);
            return true;
        }
    }


    private Boolean validateCktime() {
        String val = Dcktime.getText().toString();
        if (val.isEmpty()) {
            Dcktime.setError("Field Cannot be Empty");
            return false;
        } else {
            Dcktime.setError(null);
            return true;
        }
    }
    private Boolean validateCkdate() {
        String val = Dckdate.getText().toString();
        if (val.isEmpty()) {
            Dckdate.setError("Field Cannot be Empty");
            return false;
        } else {
            Dckdate.setError(null);
            return true;
        }
    }

    private Boolean validateAdd() {
        String val = Dadress.getText().toString();
        if (val.isEmpty()) {
            Dadress.setError("Field Cannot be Empty");
            return false;
        } else {
            Dadress.setError(null);
            return true;
        }
    }
    private Boolean validatePhone()
    {
        String val = DContact.getText().toString();
        String phoneregex="^([9]{1})([234789]{1})([0-9]{9})$";
        if(val.isEmpty()){
            DContact.setError("Field Cannot be Empty");
            return  false;
        } else if (val.matches(phoneregex)) {
            DContact.setError("Invalid Phone Number");
            return  false;
        } else if (val.length()!=10) {
            DContact.setError("Phone Number should have 10 digits");
            return  false;

        } else{
            DContact.setError(null);
            return true;
        }
    }

    private  void selectDate(){
        int year =mycal.get(Calendar.YEAR);
        int month =mycal.get(Calendar.MONTH);
        int date =mycal.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(donate.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int date) {
                mycal.set(Calendar.YEAR,year);
                mycal.set(Calendar.MONTH,month);
                mycal.set(Calendar.DATE,date);

                CharSequence Sdate= DateFormat.format("EEEE dd MMM yyy",mycal);
                Dckdate.setText(Sdate);
            }
        },year,month,date);
        datePickerDialog.show();
    }

    private void selectTime() {
        int hour =mycal.get(Calendar.HOUR);
        int minute =mycal.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(donate.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                mycal.set(Calendar.HOUR,hour);
                mycal.set(Calendar.MINUTE,minute);

                CharSequence Stime= DateFormat.format("hh:mm a",mycal);
                Dcktime.setText(Stime);
            }
        },hour,minute,false);
        timePickerDialog.show();
    }
}




