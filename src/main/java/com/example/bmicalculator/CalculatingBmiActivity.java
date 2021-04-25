package com.example.bmicalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class CalculatingBmiActivity extends AppCompatActivity {

    EditText inputWeight;
    EditText inputHeight;
    EditText inputDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculating_bmi);

        final Button Results = (Button) findViewById(R.id.bResults);
        inputWeight = (EditText)findViewById(R.id.etWeight);
        inputHeight = (EditText)findViewById(R.id.etHeight);
        inputDate = (EditText)findViewById(R.id.etDate);

        Results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String iWeight = inputWeight.getText().toString();
                String iHeight = inputHeight.getText().toString();
                String iDate = inputDate.getText().toString();

                //puts error to first empty slot from up to down
                if(iHeight.isEmpty()){
                    inputHeight.setError("Height is required.");
                    inputHeight.requestFocus();
                    return;
                } else if(iWeight.isEmpty()){
                    inputWeight.setError("Weight is required.");
                    inputWeight.requestFocus();
                    return;
                } else if(iDate.isEmpty()){
                    inputDate.setError("Date is required.");
                    inputDate.requestFocus();
                    return;
                } else {

                    //Intents to transfer inputs from this view to results
                    Intent intentTransfer = new Intent(CalculatingBmiActivity.this, ResultsBmiActivity.class);
                    intentTransfer.putExtra("inputWeight", iWeight);
                    intentTransfer.putExtra("inputHeight", iHeight);
                    intentTransfer.putExtra("inputDate", iDate);

                    startActivity(intentTransfer);

                }

            }
        });

        final Button Return = (Button) findViewById(R.id.bReturn);
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View s) {
                startActivity(new Intent(CalculatingBmiActivity.this, HomeActivity.class));
            }
        });

        final Button Logout = (Button) findViewById(R.id.bLogout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View t) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(CalculatingBmiActivity.this, MainActivity.class));
            }
        });
    }

}