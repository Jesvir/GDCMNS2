package com.example.gdcmns2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class Registration extends AppCompatActivity {

    EditText f_name,l_name,_email,_dob,_username,_password,_cpassword;
    Button btn_submit, btn_cancel,selectDate;

    Calendar myCalendar;
    int day,month,year;
    DatabaseReference ref;
    long maxid=0;
    User user;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        loadingBar = new ProgressDialog(this);
        f_name = (EditText) findViewById(R.id.fname);
        l_name = (EditText) findViewById(R.id.lname);
        _email = (EditText) findViewById(R.id.email);
        _dob = (EditText) findViewById(R.id.dob);
        selectDate = (Button) findViewById(R.id.datePick);
        _username = (EditText) findViewById(R.id.username);
        _password = (EditText) findViewById(R.id.password);
        _cpassword = (EditText) findViewById(R.id.confirm_password);
        btn_submit = (Button) findViewById(R.id.submitBtn);
        btn_cancel = (Button) findViewById(R.id.cancelBtn);
        myCalendar = Calendar.getInstance();
        day = myCalendar.get(Calendar.DAY_OF_MONTH);
        month = myCalendar.get(Calendar.MONTH);
        month = month;
        year = myCalendar.get(Calendar.YEAR);
        _dob.setText(month + "/" + day + "/" + year);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(Registration.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month;
                        _dob.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },year,month,day);
                dpd.show();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        user = new User();
        ref = FirebaseDatabase.getInstance().getReference().child("User");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxid=(dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToLogin();
            }
        });



    }

    public  void CreateNewAccount(){
//        user.setFirstname(f_name.getText().toString().trim());
//        user.setLastname(l_name.getText().toString().trim());
//        user.setEmail(_email.getText().toString().trim());
//        user.setUsername(_username.getText().toString().trim());
//        user.setDateOfBirth(_dob.getText().toString().trim());
//        user.setPassword(_password.getText().toString().trim());
//        String pass1 = _password.getText().toString().trim();
//        String pass2 = _cpassword.getText().toString().trim();
//
//
//        if (f_name.length() == 0 || l_name.length() == 0 || _email.length() == 0 || _username.length() == 0 ||  _password.length() == 0 || _cpassword.length() == 0){
//            Toast.makeText(Registration.this,"Empty Fields found!",Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        else if ( _password.length() <=5 ){
//            Toast.makeText(Registration.this,"Password required 6 characters!",Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        else if (!pass1.equals(pass2)){
//            Toast.makeText(Registration.this,"Password don't match ",Toast.LENGTH_LONG).show();
//            return;
//        }
//        else{
//            ref.child(String.valueOf(maxid+1)).setValue(user);
//            //ref.push().setValue(user);
//            Toast.makeText(Registration.this,"Registered Successfully!",Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(Registration.this, MainActivity.class);
//            startActivity(intent);
//        }

        String firstname = f_name.getText().toString();
        String lastname = l_name.getText().toString();
        String email = _email.getText().toString();
        String username = _username.getText().toString();
        String dob = _dob.getText().toString();
        String password = _password.getText().toString();
        String cpassword = _cpassword.getText().toString();

        if(TextUtils.isEmpty(firstname)){
            Toast.makeText(Registration.this,"Firstname should not be empty!",Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(lastname)){
            Toast.makeText(Registration.this,"Lastname should not be empty!",Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(email)){
            Toast.makeText(Registration.this,"Email should not be empty!",Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(username)){
            Toast.makeText(Registration.this,"Username should not be empty!",Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(Registration.this,"Password should not be empty!",Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(cpassword)){
            Toast.makeText(Registration.this,"Please confirm your Password!",Toast.LENGTH_LONG).show();
            return;
        }
        else if(!password.equals(cpassword)){
            Toast.makeText(Registration.this,"Password did'nt match!",Toast.LENGTH_LONG).show();
            return;
        }
        else {
            loadingBar.setTitle("Creating new Account!");
            loadingBar.setMessage("Please wait...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(Registration.this,"Registered Successfuly",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        ToLogin();
                    }
                    else{
                        String message = task.getException().getMessage();
                        Toast.makeText(Registration.this,"Error Occured: " + message,Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }

                }
            });
        }
    }

    public void ToLogin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }




}

