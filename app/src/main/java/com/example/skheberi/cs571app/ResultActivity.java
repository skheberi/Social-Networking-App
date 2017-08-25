package com.example.skheberi.cs571app;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * Created by SKheberi on 4/12/17.
 */

public class ResultActivity extends AppCompatActivity {
    Toolbar toolbar;
    static TabLayout tabLayout;
    ViewPager viewPager;

    ViewPagerAdaptor viewPagerAdaptor;
    static int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        Bundle bundle = getIntent().getExtras();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//Extract the data…
        String stuff = bundle.getString("stuff");
        // System.out.println("MYSTUFF"+stuff);
        bundle.putString("edttext", stuff);

        viewPagerAdaptor = new ViewPagerAdaptor(getSupportFragmentManager());
        UserFragment user = new UserFragment();
        PageFragment page = new PageFragment();
        PlaceFragment place = new PlaceFragment();
        GroupFragment group = new GroupFragment();
        EventsFragment event = new EventsFragment();

        user.setArguments(bundle);
        page.setArguments(bundle);
        place.setArguments(bundle);
        group.setArguments(bundle);
        event.setArguments(bundle);


        viewPagerAdaptor.addFragments(user, "Users");
        viewPagerAdaptor.addFragments(page, "Pages");
        viewPagerAdaptor.addFragments(event, "Events");
        viewPagerAdaptor.addFragments(place, "Places");
        viewPagerAdaptor.addFragments(group, "Groups");
        viewPager.setAdapter(viewPagerAdaptor);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.users);
        tabLayout.getTabAt(1).setIcon(R.drawable.pages);
        tabLayout.getTabAt(2).setIcon(R.drawable.events);
        tabLayout.getTabAt(3).setIcon(R.drawable.places);
        tabLayout.getTabAt(4).setIcon(R.drawable.groups);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==android.R.id.home)
        {
            onBackPressed();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id==R.id.fb_share)
        {
            Intent i = new Intent(ResultActivity.this,FacebookActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
/*
    @Override
    protected void onResume() {
        super.onResume();
        viewPager.setAdapter(viewPagerAdaptor);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.users);
        tabLayout.getTabAt(1).setIcon(R.drawable.pages);
        tabLayout.getTabAt(2).setIcon(R.drawable.events);
        tabLayout.getTabAt(3).setIcon(R.drawable.places);
        tabLayout.getTabAt(4).setIcon(R.drawable.groups);
        int x = ResultActivity.tabLayout.getSelectedTabPosition();
      //  viewPager.setCurrentItem(DetailActivity.resultTab);

    } */
}
