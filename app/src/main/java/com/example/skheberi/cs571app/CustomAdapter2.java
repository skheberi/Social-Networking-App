package com.example.skheberi.cs571app;

/**
 * Created by SKheberi on 4/15/17.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CustomAdapter2 extends ArrayAdapter<JSONObject> {
    String foodtext;
    String url;
    String itemId;
    String msg;
    String datTim;
    public CustomAdapter2(@NonNull Context context, ArrayList<JSONObject> eleList) {
        super(context, R.layout.custom_row2, eleList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflator = LayoutInflater.from(getContext());
        //return super.getView(position, convertView, parent);
        View customView = myInflator.inflate(R.layout.custom_row2,parent,false);

        JSONObject singleItem = getItem(position);

        try {
            if(singleItem.has("message"))
             msg=singleItem.getString("message");
            else
                msg=singleItem.getString("story");
             datTim = singleItem.getString("created_time");



        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView myText = (TextView) customView.findViewById(R.id.custTxt);
        TextView myTextName = (TextView) customView.findViewById(R.id.custNameTxt);
        TextView myTextDate = (TextView) customView.findViewById(R.id.custDateTxt);
        myText.setText(msg);
        myTextName.setText(DetailActivity.namew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+SSSS");
        Date testDate=null;
        try{
            testDate=sdf.parse(datTim);
           // System.out.println("Tdate"+testDate);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

       if(testDate==null)
           return customView;

            String newFormat = formatter.format(testDate);




        myTextDate.setText(newFormat);
        ImageView myImage = (ImageView) customView.findViewById(R.id.custImgView);

        Picasso.with(this.getContext()).load(DetailActivity.urlw).into(myImage);

        return customView;
    }
}