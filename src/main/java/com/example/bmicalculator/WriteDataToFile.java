package com.example.bmicalculator;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_APPEND;


public class WriteDataToFile {

    public static void write(String ID, String date, String BMI, Context context) {

        try {
            FileOutputStream fOut = context.openFileOutput("userData.txt",  MODE_APPEND);
            String string = ID+ ";" +date+";"+BMI+ "\n";
            fOut.write(string.getBytes());
            fOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
