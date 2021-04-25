package com.example.bmicalculator;

public class Analyse {

    public static String analysis(String bmi) {
        float BMI = Float.valueOf(bmi);
        String analyse;
        if (BMI < 16.0) {
            analyse = "it is considered severely thin";
        } else if (BMI >= 16 && BMI <= 17) {
            analyse = "it is considered moderate thin";
        } else if (BMI >= 17 && BMI <= 18.5) {
            analyse = "it is considered mildly thin";
        } else if (BMI >= 18.5 && BMI <= 25) {
            analyse = "it is considered normal.";
        } else if (BMI >= 25 && BMI <= 30) {
            analyse = "it is considered overweight";
        } else if (BMI >= 30 && BMI <= 35) {
            analyse = "it is considered obese class 1";
        } else if (BMI >= 35 && BMI <= 40) {
            analyse = "it is considered obese class 2";
        } else if (BMI >= 40) {
            analyse = "it is considered obese class 3";
        } else
            analyse = "Error";

        return analyse;
    }
}
