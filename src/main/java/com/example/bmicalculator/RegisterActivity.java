package com.example.bmicalculator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bmicalculator.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final Pattern passwordPattern =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters, testing length later to get different error message
                    "$");

    private TextView registerUser;
    private EditText etName, etUsername, etPassword, etAge;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    String userID;
    Spinner spinnerGender;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        registerUser = (Button) findViewById(R.id.bRegister);
        registerUser.setOnClickListener(this);

        etName = (EditText) findViewById(R.id.etName);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etAge = (EditText) findViewById(R.id.etAge);

        final Button Return = (Button) findViewById(R.id.bReturn);
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View s) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });

        //drop-down list
        spinnerGender = (Spinner) findViewById(R.id.sGender);

        ArrayAdapter<String> sAdapter = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.genders));
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerGender.setAdapter(sAdapter);

        spinnerGender.setOnItemSelectedListener(new OnItemSelectedListener() { //when something is selected
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int genderFromSpinner = (int) parent.getItemIdAtPosition(position);
                if (genderFromSpinner == 0) { //default "choose gender" selected, warning showing until user selects something
                    TextView errorText = (TextView) spinnerGender.getSelectedView();
                    errorText.setError("Select");
                    spinnerGender.requestFocus();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //also in "if" above
                TextView errorText = (TextView)spinnerGender.getSelectedView();
                errorText.setError("Select");
                spinnerGender.requestFocus();
            }

        });

    }


    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bRegister:
                String name = etName.getText().toString().trim();
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String age = etAge.getText().toString().trim();


                if (username.isEmpty()) {
                    etUsername.setError("Email is required");
                    etUsername.requestFocus();
                    return;
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
                    etUsername.setError("Enter valid email");
                    etUsername.requestFocus();
                    return;
                }
                else if(password.isEmpty()){
                    etPassword.setError("Password is required.");
                    etPassword.requestFocus();
                    return;
                }
                else if (!passwordPattern.matcher(password).matches()) {
                    etPassword.setError("Password must contain an uppercase and a lowercase letter, a number and a special character.");
                    etPassword.requestFocus();
                    return;
                }
                else if (password.length() < 12) {
                        etPassword.setError("Minimum password length is 12 characters");
                        etPassword.requestFocus();
                        return;
                }
                else if(name.isEmpty()){
                    etName.setError("Name is required.");
                    etName.requestFocus();
                    return;
                }
                else if(age.isEmpty()){
                    etAge.setError("Age is required.");
                    etAge.requestFocus();
                    return;
                }
                else { //if every field is correctly filled
                    int spinnerPos = spinnerGender.getSelectedItemPosition();
                    String spinnerText = String.valueOf(spinnerPos); //get current selected gender from spinner
                    registerUser(spinnerText);
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
                break;
        }
    }

    private void registerUser(String spinnerText) {
        String name = etName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String age = etAge.getText().toString().trim();


        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    //create user to firebase
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                            System.out.println(task.getException());
                            if (task.isSuccessful()) {

                                Toast.makeText(RegisterActivity.this, "Registering succeeded", Toast.LENGTH_LONG).show();
                                userID = mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = firestore.collection("users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                //add user's info to firebase
                                user.put("name", name);
                                user.put("email", username);
                                user.put("age", age);
                                user.put("gender", spinnerText); // 1=Male, 2=Female, 3=Other
                                user.put("password", password);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG", "onSuccess: Profile has been created for "+ userID);
                                    }
                                });

                            } else {
                                Toast.makeText(RegisterActivity.this, "Registering failed", Toast.LENGTH_LONG).show();

                            }

                    }
                });

    }
}

