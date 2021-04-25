package com.example.bmicalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ProgressActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        Context context = this.getApplicationContext();

        //reading log
        String filedata = ReadDataFromFile.read(context);

        //user gets their bmi history to screen
        TextView edit = (TextView) findViewById(R.id.tvEdit);
        edit.setText(filedata);


        final Button Return = (Button) findViewById(R.id.bReturn);
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View t) {
                startActivity(new Intent(ProgressActivity.this, HomeActivity.class));
            }
        });

        final Button Logout = (Button) findViewById(R.id.bLogout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View u) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProgressActivity.this, MainActivity.class));
            }
        });

    }
}