package com.example.arpesh.eureka18;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;

public class AadharCard extends AppCompatActivity {
    private EditText inputAadhar;
    private Button OKay;
    private TextView MobileNo;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aadharcard);
        progressBar = findViewById(R.id.ProgressBar);
        inputAadhar = findViewById(R.id.UniqueID_EditText);
                        inputAadhar.setTextIsSelectable(true);
                        inputAadhar.setFocusableInTouchMode(true);
                        inputAadhar.setFocusable(true);

        MobileNo = findViewById(R.id.UniqueID_MobileNO);
        OKay = findViewById(R.id.UniqueID_BUTTON);

        OKay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    GetAddress getAddress = new GetAddress();
                    getAddress.string(inputAadhar.getText().toString());
                    AskDataToServer();



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        }





    private void AskDataToServer() throws JSONException {
            String type = "Aadhar";
            final String Json = FormatDataAsJson();
            BackgroundWorker backgroundWorker = new BackgroundWorker(getApplicationContext());
            backgroundWorker.textView(MobileNo);
            backgroundWorker.Views(progressBar);
            backgroundWorker.execute(type, Json);


    }

    private String FormatDataAsJson() throws JSONException {
        JSONObject Aadhar = new JSONObject();
        System.out.println(Aadhar.put("aadhar",inputAadhar.getText().toString().trim()).toString());
        return Aadhar.put("aadhar",inputAadhar.getText().toString().trim()).toString();   }
}
