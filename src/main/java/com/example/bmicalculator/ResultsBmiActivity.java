package com.example.bmicalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ResultsBmiActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_bmi);

        //gets intents from calculating view
        Intent intentTransfer = getIntent();
        String weight = (String)intentTransfer.getSerializableExtra("inputWeight");
        String height = (String)intentTransfer.getSerializableExtra("inputHeight");
        String date = (String)intentTransfer.getSerializableExtra("inputDate");

        //calculating BMI
        String returnBMI = Calculator.calculate(weight, height);
        //getting the analysis with BMI (normal, overweight...)
        String bmi = Analyse.analysis(returnBMI);

        //shows results on screen
        TextView Bmi = (TextView) findViewById(R.id.tvBMI);
        Bmi.setText(returnBMI);
        TextView analyse = (TextView) findViewById(R.id.tvAnalyse);
        analyse.setText(bmi);

        mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();

        //user id, date and bmi to log -> goes to WriteDataToFile
        Context context = this.getApplicationContext();
        WriteDataToFile.write(userID, date, returnBMI, context);

        final Button Info = (Button) findViewById(R.id.bMoreInfo);
        Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultsBmiActivity.this, InfoActivity.class));
            }
        });

        final Button Return = (Button) findViewById(R.id.bReturn);
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View t) {
                startActivity(new Intent(ResultsBmiActivity.this, CalculatingBmiActivity.class));
            }
        });

        final Button Logout = (Button) findViewById(R.id.bLogout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View u) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ResultsBmiActivity.this, MainActivity.class));
            }
        });
    }


}

