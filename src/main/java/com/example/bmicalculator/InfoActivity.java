package com.example.bmicalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class InfoActivity extends AppCompatActivity {

    Context context = null;
    FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        context = InfoActivity.this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //getting users id out of firebase
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        CollectionReference users = firestore.collection("users");
        DocumentReference fields = users.document(userID);

        fields.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            //getting users age and gender out of firebase to be used in reading api
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    Map<String, Object> user = document.getData();
                    if (document.exists()){
                        String birthYear = (String) user.get("age");
                        String gender = (String) user.get("gender");
                        Log.d("Document", document.getData().toString());

                        String data = readJSON(birthYear, gender);

                        //setting return value from api to text view
                        TextView percent = (TextView) findViewById(R.id.tvPercent);
                        percent.setText(data);

                    } else {
                        Log.d("Document", "No data available");
                    }
                }
            }
        });


        final Button Return = (Button) findViewById(R.id.bReturn);
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View t) {
                startActivity(new Intent(InfoActivity.this, HomeActivity.class));
            }
        });

        final Button Logout = (Button) findViewById(R.id.bLogout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View u) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(InfoActivity.this, MainActivity.class));
            }
        });
    }

    public String readJSON(String birthYear, String gender){
        String json = getJSON();
        int Year =  Integer.valueOf(birthYear) - 1978; //index 0 is 1978
        int genderID = Integer.valueOf(gender) - 1; //-1 so that 1=Male -> 0=Male, 2=Female -> 1=Female, 3=Other -> 2=Other
        int g = 0;
        String jsonPercent = "";
        String jsonTemp = "";

        if (json!= null) { //read json
            try {
                JSONObject root = new JSONObject(json);
                JSONArray data = root.getJSONArray("data");
                if (genderID == 0) {
                    g = 0;
                } else if (genderID == 1) {
                    g = 1;
                }

                int min = 1979 - 1978; //first year is 1978, one index before that
                int max = 2019 - 1978; //last year is 2018, one index after that
                if ((min < Year) && (Year < max)) {
                    if (genderID == 2) { //api has only two genders, if registered gender is "other", they see both values
                        JSONObject dataentry1 = (JSONObject) data.get(2 * Year);
                        String jsonTemp1 = dataentry1.getString("values");
                        //turning value from ["number"] to number
                        String jsonPercent1 = jsonTemp1.replace("[\"", "").replace("\"]", "%");

                        JSONObject dataentry2 = (JSONObject) data.get(2 * Year + 1);
                        String jsonTemp2 = dataentry2.getString("values");
                        String jsonPercent2 = jsonTemp2.replace("[\"", "").replace("\"]", "%");

                        jsonPercent = jsonPercent1+ " (male), " +jsonPercent2+ " (female)";

                    } else { //this if user has registered gender as "male" or "female"
                        JSONObject dataentry = (JSONObject) data.get(2 * Year + g);
                        jsonTemp = dataentry.getString("values");
                        jsonPercent = jsonTemp.replace("[\"", "").replace("\"]", "%");
                    }
                } else { //api has data from 1978-2018, if registered birth year is outside that time gap, they don't get to see data from their birth year
                    jsonPercent = "no data, data is from 1978-2018 and you were born in " +birthYear;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonPercent;

    }


    public String getJSON(){
        String response = null;

        try {
            URL url = new URL("https://pxnet2.stat.fi:443/PXWeb/sq/de967aa2-b865-47ed-9edd-b29a933b0811");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            response = sb.toString();
            in.close();


        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}