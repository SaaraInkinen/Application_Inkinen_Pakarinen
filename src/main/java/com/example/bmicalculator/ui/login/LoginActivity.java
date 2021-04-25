package com.example.bmicalculator.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bmicalculator.HomeActivity;
import com.example.bmicalculator.MainActivity;
import com.example.bmicalculator.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        EditText inputUsername = (EditText) findViewById(R.id.etUsername);
        EditText inputPassword = (EditText)findViewById(R.id.etPassword);


        final Button Return = (Button) findViewById(R.id.bReturn);
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View s) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        final Button Results = (Button) findViewById(R.id.bLogin);
        Results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String iUsername = inputUsername.getText().toString();
                String iPassword = inputPassword.getText().toString();

                auth.signInWithEmailAndPassword(iUsername, iPassword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    //sign in to firebase
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Log in failed", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        }
                    }
                });
            }
        });
    }
}