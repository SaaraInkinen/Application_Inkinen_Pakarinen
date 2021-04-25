package com.example.bmicalculator;

public class Calculator {

    public static String calculate(String w,String h) {
        double index;
        double weight = Double.parseDouble(w);
        double height = Double.parseDouble(h);
        
        index = (weight / (height * height));
        double BMI1 = Math.round(index * 100.0) / 100.0;
        String bmi = String.valueOf(BMI1);
        return bmi;
    }

   
}
