package com.example.arpesh.eureka18;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.*;

/**
 * Created by arpesh on 20/2/18 6:40 AM Eureka18.
 */

public class OtpForUser extends AppCompatActivity {
   private EditText mOtp;
    private TextView mNumber;
    private  Button mVerify;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    private String mVerificationID;
    private PhoneAuthProvider.ForceResendingToken mforceResendingToken;
    private int btnType =0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otpforuser);
        mNumber = findViewById(R.id.OtpForUser_MobileNo);
        mOtp = findViewById(R.id.OtpForUser_OTP);
        mVerify = findViewById(R.id.OtpForUser_OTPBtn);
        final String mnumbers = getIntent().getExtras().getString("Mobile no");
        mNumber.setText(mnumbers);
        mOtp.setClickable(true);
        mOtp.setTextIsSelectable(true);
        mOtp.setFocusableInTouchMode(true);
        mOtp.setFocusable(true);
        mAuth = FirebaseAuth.getInstance();
        mVerify.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                if (btnType == 0) {

                    Toast.makeText(getApplicationContext(), "OTP Sent", Toast.LENGTH_SHORT).show();

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            mnumbers,
                            120,
                            TimeUnit.SECONDS,
                            OtpForUser.this,
                            mCallBacks
                    );

                } else {
                  //  mVerify.setText("Enter OTP");
                    Toast.makeText(getApplicationContext(), "Verifying OTP", Toast.LENGTH_SHORT).show();

                    String OTP = mOtp.getText().toString();
                    System.out.println("OTP came" + OTP);


                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationID, OTP);
                    signInWithPhoneAuthCredential(credential);
                }


            }
        });
        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCodeSent(String verificationID,
                                   PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                mVerificationID =
                        verificationID;
                btnType = 1;
                mforceResendingToken = forceResendingToken;
                mVerify.setEnabled(true);
                mVerify.setText(R.string.verify_otp);
                mOtp.setVisibility(View.VISIBLE);
            }
        };}
            private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),"OTP sucessful",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(OtpForUser.this,GetAddress.class);
                                startActivity(intent);
                                finish();

                                }


                            else {
                                Toast.makeText(getApplicationContext(),"OTP Failed",Toast.LENGTH_SHORT).show();

                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                            // The verification code entered was inval}
                                    Toast.makeText(getApplicationContext(),"OTP failed with exception",Toast.LENGTH_SHORT).show();
                            }

                    }
                        }

                    });
        }



}




