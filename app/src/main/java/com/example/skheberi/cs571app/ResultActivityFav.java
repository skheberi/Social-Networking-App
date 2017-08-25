package com.example.skheberi.cs571app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

/**
 * Created by SKheberi on 4/12/17.
 */

public class ResultActivityFav extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    static TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdaptor viewPagerAdaptor;
    static int x;
    static  NavigationView navigationView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultfav);
        toolbar=(Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        MainActivity.active=true;
      //  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Bundle bundle = getIntent().getExtras();
        viewPagerAdaptor = new ViewPagerAdaptor(getSupportFragmentManager());
        UserFragmentFav user = new UserFragmentFav();
        PageFragmentFav page = new PageFragmentFav();
        PlaceFragmentFav place = new PlaceFragmentFav();
        EventsFragmentFav events = new EventsFragmentFav();
        GroupFragmentFav group = new GroupFragmentFav();
        viewPagerAdaptor.addFragments(user,"Users");
        viewPagerAdaptor.addFragments(page,"Pages");
        viewPagerAdaptor.addFragments(events,"Events");
        viewPagerAdaptor.addFragments(place,"Places");

        viewPagerAdaptor.addFragments(group,"Groups");

        viewPager.setAdapter(viewPagerAdaptor);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.users);
        tabLayout.getTabAt(1).setIcon(R.drawable.pages);
        tabLayout.getTabAt(2).setIcon(R.drawable.events);
        tabLayout.getTabAt(3).setIcon(R.drawable.places);
        tabLayout.getTabAt(4).setIcon(R.drawable.groups);



//Extract the data…
       // String stuff = bundle.getString("stuff");
        // System.out.println("MYSTUFF"+stuff);
    //    bundle.putString("edttext",stuff);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView2 = (NavigationView) findViewById(R.id.nav_view);
        navigationView2.setNavigationItemSelectedListener(this);
        navigationView2.getMenu().getItem(1).setChecked(true);









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
      //  getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }





    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            MainActivity.navigationView.getMenu().getItem(0).setChecked(true);
            Intent i= new Intent(getApplicationContext(),MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.nav_fav) {

            Intent i = new Intent(ResultActivityFav.this,ResultActivityFav.class);
            startActivity(i);

        } else if (id == R.id.nav_about) {
            Intent i = new Intent(ResultActivityFav.this,AboutMeClass.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        int x = ResultActivityFav.tabLayout.getSelectedTabPosition();
       // viewPager.setCurrentItem(DetailActivity.favTab);

    } */




}
