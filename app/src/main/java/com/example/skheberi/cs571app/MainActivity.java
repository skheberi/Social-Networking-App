package com.example.skheberi.cs571app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.R.attr.name;
import static com.example.skheberi.cs571app.R.styleable.View;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button btnClear, btnSubmit;
    HttpClient httpclient;
    HttpPost httppost;
    HttpResponse response;
    List<NameValuePair> nameValuePairs;
    static String lat="";
    static String lang="";
    InputStream is;
    LocationManager locationManager;
    LocationListener locationListener;
    static NavigationView navigationView;
    static boolean active=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnClear = (Button)  findViewById(R.id.btnClear);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 8);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // System.out.println("Hello");
                Intent i = new Intent(MainActivity.this, ResultActivity.class);
                TextView textView = (TextView) findViewById(R.id.searchInput);
                String getrec = textView.getText().toString();

                if (getrec.length() == 0) {
                    Toast.makeText(MainActivity.this, "Please enter a keyword!",
                            Toast.LENGTH_LONG).show();
                } else {
//Create the bundle
                    Bundle bundle = new Bundle();

//Add your data to bundle
                    //get = getArguments().getString("edttext");
                    try {
                        getrec = URLEncoder.encode(getrec,"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    bundle.putString("stuff", getrec);

//Add the bundle to the intent
                    i.putExtras(bundle);

//Fire that second activity
                    startActivity(i);



                }
            }
        });



        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // System.out.println("Hello");

                TextView textView = (TextView) findViewById(R.id.searchInput);
                textView.setText("");

//Create the bundle


//Fire that second activity


              /*  try {

                    new Thread() {
                        @Override
                        public void run() {
                            //your code here
                            HttpClient httpClient = new DefaultHttpClient();
                            HttpPost httppost = new HttpPost("http://sample-env-1.p7hvtdxr5s.us-west-2.elasticbeanstalk.com/");
                            try {


                                EditText mEdit   = (EditText)findViewById(R.id.searchInput);
                                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                                nameValuePairs.add(new BasicNameValuePair("Keyword", mEdit.getText().toString()));
                                nameValuePairs.add(new BasicNameValuePair("Type", "user"));
                                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                                HttpResponse response = httpClient.execute(httppost);
                                HttpEntity entity = response.getEntity();
                               is = entity.getContent();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            try {
                                BufferedReader reader = new BufferedReader(new InputStreamReader(is,   "iso-8859-1"), 8);
                                StringBuilder sb = new StringBuilder();
                                sb.append(reader.readLine() + "\n");

                                String line = "0";
                                while((line = reader.readLine()) != null){
                                    sb.append(line + "\n");
                                }
                                is.close();
                                String result = sb.toString();
                                System.out.println(result);

                            } catch (Exception e) {
                                Log.e("log_tag", "Error converting result " + e.toString());
                            }


                        }
                    }.start();








                } catch (Exception e) {
                    e.printStackTrace();
                }  */
            }
        });




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);


        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location != null) {
            showMyAddress(location);
        }

        final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                showMyAddress(location);
            }

            public void onProviderDisabled(String arg0) {
                // TODO Auto-generated method stub

            }

            public void onProviderEnabled(String arg0) {
                // TODO Auto-generated method stub

            }

            public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                // TODO Auto-generated method stub

            }
        };

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
       // lala = (TextView) findViewById(R.id.textView1);




    }


    public void showMyAddress(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Geocoder myLocation = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> myList;
        try {
            myList = myLocation.getFromLocation(latitude, longitude, 1);
            if(myList.size() == 1) {
               lat=String.valueOf(myList.get(0).getLatitude());
                lang = String.valueOf(myList.get(0).getLatitude());
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
           addPreferences();
        }

        if(id==R.id.fb_share)
        {
            Intent i = new Intent(MainActivity.this,FacebookActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_fav) {

            Intent i = new Intent(MainActivity.this,ResultActivityFav.class);
            startActivity(i);

        } else if (id == R.id.nav_about) {
            Intent i = new Intent(MainActivity.this,AboutMeClass.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addPreferences()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();



        Toast.makeText(this, "Added to favorites!",
                Toast.LENGTH_LONG).show();
    }
}
