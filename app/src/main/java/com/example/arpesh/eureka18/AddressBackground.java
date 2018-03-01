package com.example.arpesh.eureka18;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by arpesh on 21/2/18 1:17 AM Eureka18.
 */

public class AddressBackground extends AsyncTask<String,Void , String> {

    protected  Context context;
    private   String Address = "";
    private   String State = "";
    private   String Pincode = "";
    private TextView textView1 ;
    private ProgressBar progressBar1;
    private Button btn ;

    AddressBackground(Context ctx ){
        context = ctx;
    }
    void Views(TextView textView , Button btn , ProgressBar progressBar){
        this.textView1 = textView;
        this.btn = btn;
        this.progressBar1 = progressBar;
    }


    @Override
    protected void onPreExecute() {
        progressBar1.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        if(strings[0].equals("GetAddress")){
            String GetAddress_URl = "https://eureka18.000webhostapp.com/aadhar_detail.php";
            String data="";

            try {
                URL url = new URL(GetAddress_URl);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                httpURLConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
                httpURLConnection.connect();
                DataOutputStream dataOutputStream
                        = new DataOutputStream(httpURLConnection.getOutputStream());

                dataOutputStream.writeBytes(strings[1]);
                dataOutputStream.flush();



                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int inputStreamData = inputStreamReader.read();
                Log.d("inputstream", String.valueOf(inputStreamData));
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                    System.out.println("Mobile no" +data);}
//                JSONArray myListsAll= new JSONArray(myjsonstring);
//                for(int i=0;i<myListsAll.length();i++){
//                    JSONObject jsonobject= (JSONObject) myListsAll.get(i);
//                    String id=jsonobject("nid");
//                    String value1=jsonobject.optString("field_mc_bacheliers_value");
//                    String value2=jsonobject.optString("field_mc_defi_collectif_value");
                    JSONArray jsonarray = new JSONArray(data);
                JSONObject jsonobject = (JSONObject) jsonarray.get(0);
                Address = jsonobject.optString("address");
                State = jsonobject.optString("state");
                Pincode = jsonobject.optString("pin_code");
                Log.d("Address",Address);
                Log.d("State",State);
                Log.d("Pincode",Pincode);
                 data = Address +" "+State+"-"+Pincode;

                dataOutputStream .close();
                inputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return data;

        }
        return null;
    }



    @Override
    protected void onPostExecute(String result) {
        if(result.length()>6){
            progressBar1.setVisibility(View.INVISIBLE);
            textView1.setText(result);

            btn.setEnabled(true);
            btn.setText("Correct");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetAddress getAddress = new GetAddress();
                    getAddress.getAddress();

                }
            });

        }
    }


}
