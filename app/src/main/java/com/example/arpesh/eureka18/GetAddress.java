package com.example.arpesh.eureka18;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arpesh on 21/2/18 12:06 AM Eureka18.
 */

public class GetAddress extends AppCompatActivity {


    private TextView mAddress;
    private Button mFetch;
    private ProgressBar progressBar;
    // private Boolean isUpdate;
    private String Aadhar;
    private LocationManager locationManager;
    private LocationListener listener;

    void string(String s) {
        this.Aadhar = s;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getaddress);
        progressBar = findViewById(R.id.GetAddress_ProgressBar);
//        Bundle bundle = getIntent().getExtras();
//        if (bundle!= null) {// to avoid the NullPointerException
//              isUpdate=bundle.getBoolean("update");
//            if(isUpdate)
//            {
//                Aadhar = bundle.getString("Aadhar no");
//                Log.d("Aadhar no in GetAddress",Aadhar);
//
//            }
//        }

        mAddress = findViewById(R.id.GetAddress_textView);
        mFetch = findViewById(R.id.GetAddress_Btn);
        mFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mFetch.setEnabled(false);
                    fetchDataFromServer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fetchDataFromServer() throws JSONException {
        String type = "GetAddress";
        String Json = CreateJSOn();
        AddressBackground addressBackground = new AddressBackground(getApplicationContext());
        addressBackground.Views(mAddress, mFetch, progressBar);
        addressBackground.execute(type, Json);
    }

    private String CreateJSOn() throws JSONException {
        JSONObject AadharObj = new JSONObject();

        return AadharObj.put("aadhar", Aadhar).toString();
    }


    void getAddress() {

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mAddress.append("\n " + location.getLongitude() + " " + location.getLatitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        configure_button();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button() {
        mFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getApplicationContext(),
                                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                    //
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates("gps", 5000, 0, listener);
            }
        });
    }




}




