package com.example.bmicalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Button newEntry = (Button) findViewById(R.id.bNewEntry);
        newEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CalculatingBmiActivity.class));
            }
        });

        final Button Progress = (Button) findViewById(R.id.bProgress);
        Progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View s) {
                startActivity(new Intent(HomeActivity.this, ProgressActivity.class));
            }
        });

        final Button Logout = (Button) findViewById(R.id.bLogout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View t) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });
    }


}