package com.example.gdcmns2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {


    EditText u,p;
    String un,pass,db,ip;
    Button login,register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        register = (Button)findViewById(R.id.register_button);
        login = (Button)findViewById(R.id.login_button);
        u = (EditText)findViewById(R.id.username);
        p = (EditText)findViewById(R.id.password);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDRegistration();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDashboard();
            }
        });
    }

    public void ToDashboard(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
//        finish();
    }

    public void ToDRegistration(){
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
//        finish();
    }



}
