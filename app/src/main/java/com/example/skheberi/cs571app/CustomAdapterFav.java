package com.example.skheberi.cs571app;

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

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by SKheberi on 4/12/17.
 */

public class CustomAdapterFav extends ArrayAdapter<JsonObject> {
    String foodtext;
    String url;
    String itemId;
    String type;
    static String selURL;
    static String selName;
    static JsonObject fullJSON;


    public CustomAdapterFav(@NonNull Context context, ArrayList<JsonObject> eleList) {
        super(context,R.layout.custom_row, eleList);
        this.type=type;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflator = LayoutInflater.from(getContext());
        View customView = myInflator.inflate(R.layout.customfav_row,parent,false);
        JsonObject singleFoodItem = getItem(position);

        if (singleFoodItem != null) {
            foodtext = singleFoodItem.get("name").getAsString();
            JsonObject pic = singleFoodItem.getAsJsonObject("picture");
            JsonObject dataText = pic.getAsJsonObject("data");
            url=dataText.get("url").getAsString();
        }


        TextView myText = (TextView) customView.findViewById(R.id.custTxt);
        ImageView myImage = (ImageView) customView.findViewById(R.id.custImgView);
        myText.setText(foodtext);
        ImageButton favImage = (ImageButton) customView.findViewById(R.id.favImgView);
        ImageButton detImage = (ImageButton) customView.findViewById(R.id.detImgView);

        favImage.setImageResource(R.drawable.favorites_on);
        detImage.setImageResource(R.drawable.details);

        myImage.setImageResource(R.drawable.facebook);
        //  Picasso.with(getContext()).setLoggingEnabled(true);
        Picasso.with(this.getContext()).load(url).into(myImage);

        detImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // System.out.println("Helloji");
                Bundle b = new Bundle();
                Intent i = new Intent(getContext(),DetailActivity.class);
                try {

                    System.out.println("Tab:" + type);
                   fullJSON = getItem(position);
                    CustomAdapter.selName = getItem(position).get("name").getAsString();
                    CustomAdapter.selURL =  getItem(position).getAsJsonObject("picture").getAsJsonObject("data").get("url").getAsString();
                    b.putString("id",getItem(position).get("id").getAsString());
                    b.putString("name",CustomAdapter.selName);
                    b.putString("url",CustomAdapter.selURL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                b.putString("fav","fav");

                i.putExtras(b);

                getContext().startActivity(i);
            }
        });

       return customView;
    }

}
