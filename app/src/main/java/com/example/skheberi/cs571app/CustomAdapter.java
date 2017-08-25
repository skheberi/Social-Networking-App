package com.example.skheberi.cs571app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SKheberi on 4/12/17.
 */

public class CustomAdapter extends ArrayAdapter<JSONObject> {
    String foodtext;
    String url;
    String itemId;
    String type;
    static String selURL;
    static String selName;
    static JSONObject fullJSON;



    public CustomAdapter(@NonNull Context context, ArrayList<JSONObject> eleList,String type) {
        super(context,R.layout.custom_row, eleList);
        this.type=type;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflator = LayoutInflater.from(getContext());
        View customView = myInflator.inflate(R.layout.custom_row,parent,false);

       JSONObject singleFoodItem = getItem(position);
        try {

            foodtext = singleFoodItem.getString("name");
            itemId = singleFoodItem.getString("id");
            JSONObject imageText = singleFoodItem.getJSONObject("picture");
            JSONObject dataText = imageText.getJSONObject("data");
           // System.out.println("Image Text:" + imageText);
            url=dataText.getString("url");
           // System.out.println(url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView myText = (TextView) customView.findViewById(R.id.custTxt);
        ImageView myImage = (ImageView) customView.findViewById(R.id.custImgView);
        myText.setText(foodtext);
        final ImageButton favImage = (ImageButton) customView.findViewById(R.id.favImgView);
        ImageButton detImage = (ImageButton) customView.findViewById(R.id.detImgView);


        String holder="";
        int x = ResultActivity.tabLayout.getSelectedTabPosition();
        if(x==0)
            holder="user";
        if(x==1)
            holder="page";
        if(x==2)
            holder="events";
        if(x==3)
            holder="place";
        if(x==4)
            holder="group";
        favImage.setImageResource(R.drawable.favorites_off);

       // System.out.println("Holder is ************"+holder);
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<JsonObject> user = new ArrayList<JsonObject>();
        String strJson = sharedPreferences.getString(type,"0");
        System.out.println("FoodText:"+foodtext);
       // System.out.println("Holder:"+strJson);

        if(strJson!="0") {
            ArrayList<JsonObject> user2 = new ArrayList<JsonObject>();
            String strJson2 = strJson;

// System.out.println("Retrieved value:"+strJson);
            user2=gson.fromJson(strJson2, new TypeToken<List<JsonObject>>(){}.getType());


            for(JsonObject js:user2)
            {
                String itemIn="";


                try {
                  itemIn = getItem(position).getString("id");

                   // System.out.println("Item Id"+ itemIn);

                    //System.out.println("Retrieved id:"+js.get("id").getAsString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // System.out.println("Inside For Loop");
                if(js.get("id").getAsString().equals(itemIn)) {

                    favImage.setImageResource(R.drawable.favorites_on);

                }



            }
        }

       // favImage.setImageResource(R.drawable.favorites_off);
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
                    selName = getItem(position).getString("name");
                    selURL =  getItem(position).getJSONObject("picture").getJSONObject("data").getString("url");
                    b.putString("id",getItem(position).getString("id"));
                    b.putString("name",getItem(position).getString("name"));
                    b.putString("url",getItem(position).getJSONObject("picture").getJSONObject("data").getString("url"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                i.putExtras(b);
              //  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(i);
            }
        });
       return customView;

    }

}
