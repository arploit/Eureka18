package com.example.arpesh.eureka18;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;

/**
 * Created by arpesh on 5/2/18 3:22 AM Eureka18.
 */

public class SignIn extends AppCompatActivity{
    private EditText InputFname, InputLname,InputMobileno, InputEmail, InputPassword ,InputUserName ;
    private Button SignIn;
    private ProgressBar progressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        progressBar = findViewById(R.id.ProgressBar);
        InputUserName = findViewById(R.id.activity_signin_UserName);
                     InputUserName .setTextIsSelectable(true);
                     InputUserName .setFocusableInTouchMode(true);
                     InputUserName .setFocusable(true);
                     InputUserName.setClickable(true);
        InputEmail = findViewById(R.id.activity_signin_Email);
                    InputEmail.setTextIsSelectable(true);
                    InputEmail.setFocusableInTouchMode(true);
                    InputEmail.setFocusable(true);
        InputFname = findViewById(R.id.activity_signin_FirstName);
                    InputFname.setTextIsSelectable(true);
                    InputFname.setFocusableInTouchMode(true);
                    InputFname.setFocusable(true);
        InputLname = findViewById(R.id.activity_signin_LastName);
                    InputLname.setTextIsSelectable(true);
                    InputLname.setFocusableInTouchMode(true);
                    InputLname.setFocusable(true);
        InputMobileno = findViewById(R.id.activity_signin_Mobileno);
                    InputMobileno.setTextIsSelectable(true);
                    InputMobileno.setFocusableInTouchMode(true);
                    InputMobileno.setFocusable(true);
        InputPassword = findViewById(R.id.activity_signin_Password);
                    InputPassword .setFocusable(true);
                    InputPassword .setTextIsSelectable(true);
                    InputPassword .setFocusableInTouchMode(true);

        SignIn = findViewById(R.id.activity_signinButton);

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{sendDataToServer();}
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendDataToServer() throws JSONException {
        String type = "register";

        final String Json = FormatDataASJSon();
        BackgroundWorker ObjBackgroundWorker = new BackgroundWorker(getApplicationContext());
        ObjBackgroundWorker.Views(progressBar);
        ObjBackgroundWorker.execute(type,Json);

    }

    private String FormatDataASJSon() throws JSONException {
       final JSONObject user = new JSONObject();
        String UserName = InputUserName.getText().toString().trim();
        String Fname = InputFname.getText().toString().trim();
        String Lname = InputLname.getText().toString().trim();
        String inputMobileno =InputMobileno.getText().toString().trim();
        String Email = InputEmail.getText().toString().trim();
        String Password = InputPassword.getText().toString().trim();
        int Mobileno = Integer.parseInt(inputMobileno);
        BigInteger mobileNO = BigInteger.valueOf(Mobileno);

        user.put("User_Email",Email);
        user.put("User_Fname",Fname);
        user.put( "User_Lname",Lname);
        user.put("User_MobileNo",mobileNO);
        user.put("User_UserName",UserName);
        user.put("User_Password",Password);
        Log.d("JSp",user.toString(1));

        return user.toString(1);
    }
}
