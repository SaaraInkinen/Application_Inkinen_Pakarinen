package com.example.bmicalculator;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadDataFromFile {

    private static FirebaseAuth mAuth;
    
    public static String read(Context context){

        String datainfo= "";
        mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        String[] info;
        String date ="";
        String bmivalue="";
        String userIDdata ="";

        try {
            InputStream inputStream = context.openFileInput("userData.txt");
            if (inputStream != null) { //reading the log where's every user's history
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String getString ="";
                StringBuilder stringBuilder = new StringBuilder();

                while ((getString = bufferedReader.readLine()) != null) {
                    info = getString.split(";");
                    userIDdata = info[0];

                    String mUserIDdata = userIDdata.replaceAll("\\s","");
                    String mUserID = userID.replaceAll("\\s","");

                    if (mUserIDdata.equals(mUserID)){ //picking only current user's history

                            date = info[1];
                            bmivalue = info[2];

                            stringBuilder.append("\n").append(date+ ": " +bmivalue);
                    }

                }

                inputStream.close();
                datainfo = stringBuilder.toString();

                if (datainfo.equals("")) { //if the user hasn't added any entries yet
                    datainfo = "first add a new entry";
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datainfo;
    }
}
