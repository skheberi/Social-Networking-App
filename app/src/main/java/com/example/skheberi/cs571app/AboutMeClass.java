package com.example.skheberi.cs571app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by SKheberi on 4/19/17.
 */

public class AboutMeClass extends AppCompatActivity {
    Toolbar toolbar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ImageView img = (ImageView)findViewById(R.id.custImgView);
        img.setImageResource(R.drawable.idccc);
        TextView txtName = (TextView) findViewById(R.id.nameText);
        txtName.setText("Sarabjit Singh Kheberi");

        TextView txtId = (TextView) findViewById(R.id.idText);
        txtId.setText("9128617831");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        MainActivity.navigationView.getMenu().getItem(0).setChecked(true);
        if(MainActivity.active)
            ResultActivityFav.navigationView2.getMenu().getItem(1).setChecked(true);
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
            Intent i = new Intent(AboutMeClass.this,FacebookActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
