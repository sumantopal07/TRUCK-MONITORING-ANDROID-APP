package com.example.trucktrackingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.spark.submitbutton.SubmitButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Main3Activity extends AppCompatActivity {



    public static TextView temp,status,extra;
    private SubmitButton map,action,refresh;
    public Intent intent;
    public static String text1,text2,text3="",text4="",text5="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yahoo);
        temp=findViewById(R.id.temp);
        status=findViewById(R.id.status);
        extra=findViewById(R.id.extra);
        map=findViewById(R.id.trackDelivery);
        action=findViewById(R.id.ACTION);
        refresh=findViewById(R.id.refresh);
        map.setEnabled(false);

        Toast.makeText(Main3Activity.this,"Refresh the App before Tracking in Map",Toast.LENGTH_SHORT).show();
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //button2.setEnabled(false);
                //button3.setEnabled(false);
                //cityCode=new String(city.getText().toString().toLowerCase().trim());
                fetchdata process=new fetchdata();
                process.execute();
                map.setEnabled(true);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(Main3Activity.this,
                                "Data is Refreshed",
                                Toast.LENGTH_LONG).show();
                    }
                }, 3000);
                //button2.setEnabled(true);

                //  fetchdataAgain process1=new fetchdataAgain(p);
                //process1.execute();
            }
        });
        intent=new Intent(this,MapsActivity.class);
        //startActivity(new Intent(MainActivity3.this, MapsActivity.class));

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fetchdataAgain process=new fetchdataAgain();
                process.execute();
//                LogoLauncher  l=new LogoLauncher();
//                l.start();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(Main3Activity.this,
                                text3+"",
                                Toast.LENGTH_LONG).show();
                    }
                }, 3000);
//                Handler h= new Handler();
//                h.postDelayed(new Runnable(){
//
//                    Toast.makeText(Main3Activity,text3, Toast.LENGTH_SHORT).show();
//                },5000);
//                //Toast.makeText(Main3Activity.this,text3,Toast.LENGTH_SHORT).show();
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("X1",text4);
                intent.putExtra("X2",text5);
                startActivity(intent);
            }
        });

    }
//    private class LogoLauncher extends Thread{
//        public void run(){
//            try{
//                sleep(3000);
//            }catch(InterruptedException e){
//                e.printStackTrace();
//            }
//
//            Toast.makeText(Main3Activity.this,text3, Toast.LENGTH_SHORT).show();
//        }
//    }
}
class fetchdata extends AsyncTask<Void,Void,Void> {
    String data ="";
    String dataParsed = "",dataParsed1 = "",dataParsed2 = "",dataParsed3 = "",dataParsed4 = "";
    //String singleParsed ="";
    String q="";
    public fetchdata()
    {

    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://access-denied-nikhiljainjain17.c9users.io/req/app");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONObject JA = new JSONObject(data);
//            JSONObject JO = (JSONObject) JA.get(0);
//            JSONObject JO1 = (JSONObject) JA.get(1);
//            JSONObject JO2 = (JSONObject) JA.get(2);
//            JSONObject JO3 = (JSONObject) JA.get(3);
//            JSONObject JO4 = (JSONObject) JA.get(4);
            dataParsed = JA.get("temperature")+"";
            dataParsed1 = JA.get("status")+"";
            dataParsed2 = JA.get("humdity")+"%";
            dataParsed3 = JA.get("lat_val")+"";
            dataParsed4 = JA.get("lng_val")+"";

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        //MainActivity.qwe.setText(this.singleParsed);
        Main3Activity.temp.setText(this.dataParsed+"°C");
        Main3Activity.status.setText(this.dataParsed1);
        Main3Activity.extra.setText(this.dataParsed2);
        Main3Activity.text4=this.dataParsed3;
        Main3Activity.text5=this.dataParsed4;

    }
}
class fetchdataAgain extends AsyncTask<Void,Void,Void> {
    String data ="";
    //String dataParsed = "",dataParsed1 = "",dataParsed2 = "",dataParsed3 = "",dataParsed4 = "";
    //String singleParsed ="";
    String q="";
    public fetchdataAgain()
    {

    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://access-denied-nikhiljainjain17.c9users.io/req/system/off");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }
            //JSONArray JA = new JSONArray(data);
            JSONObject JA = new JSONObject(data);
            q = JA.get("status")+"";

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


        //MainActivity.qwe.setText(this.singleParsed);
        Main3Activity.text3=this.q;

    }
}

