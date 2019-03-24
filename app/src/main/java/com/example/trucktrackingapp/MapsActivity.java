package com.example.trucktrackingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static RequestQueue requestQueue;

    public static String  mapLongitude="",mapLatitude="",address1="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Intent intent=this.getIntent();
        mapLatitude=intent.getExtras().getString("X1");
        mapLongitude=intent.getExtras().getString("X2");
        fetchdata2 process23=new fetchdata2();
        process23.execute();

        double a=Double.parseDouble(mapLatitude);

        //
        double b=Double.parseDouble(mapLongitude);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(12.974346, 79.159604);//12.974346,79.159604
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,15.0f));
        mMap.addMarker(new MarkerOptions().position(sydney).title("LOCATION"));
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(MapsActivity.this,
                        address1+"",
                        Toast.LENGTH_LONG).show();
            }
        }, 3000);
       //Toast.makeText(MapsActivity.this, address1 + "", Toast.LENGTH_LONG).show();
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney,15));
    }
}
class fetchdata2 extends AsyncTask<Void,Void,Void> {
    String data ="";
    //String dataParsed = "",dataParsed1 = "",dataParsed2 = "",dataParsed3 = "",dataParsed4 = "";
    //String singleParsed ="";
    String q="";
    public fetchdata2()
    {

    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?latlng="+MapsActivity.mapLatitude+","+MapsActivity.mapLongitude+"&sensor=true&key=AIzaSyACjEFG5Hufa0S1NlDL1IH0bphLn334Ciw");
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
            JSONArray J1 = (JSONArray)JA.get("results");
            JSONObject J2=(JSONObject)J1.get(0);
            q=(String) J2.get("formatted_address");
            //q = J3+"";

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
        MapsActivity.address1=this.q;

    }
}
//class fetchdata2 extends AsyncTask<Void,Void,Void> {
//    String data ="";
//    String dataParsed = "",dataParsed1 = "",dataParsed2 = "",dataParsed3 = "",dataParsed4 = "";
//    //String singleParsed ="";
//    String q="";
//    public fetchdata2()
//    {
//
//    }
//    @Override
//    protected Void doInBackground(Void... voids) {
//        try {
//            final JsonObjectRequest request= new JsonObjectRequest("https://maps.googleapis.com/maps/api/geocode/json?latlng="+MapsActivity.mapLatitude+","+MapsActivity.mapLongitude+"&sensor=true&key=AIzaSyACjEFG5Hufa0S1NlDL1IH0bphLn334Ciw",null,new Response.Listener<JSONObject>(){
//                @Override
//                public void onResponse(JSONObject response) {
//                    try {
//                         q = response.getJSONArray("results").getJSONObject(0).getString("formatted_address");
//                        //Toast.makeText(MapsActivity.this, address + "", Toast.LENGTH_LONG).show();
//                    }catch(Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//            },new Response.ErrorListener()
//            {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            });
//            MapsActivity.requestQueue.add(request);
//       }
//       catch (Exception e)
//       {}
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Void aVoid) {
//        super.onPostExecute(aVoid);
//
//        MapsActivity.address1=this.q;
//
//
//    }
//}