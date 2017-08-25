package com.example.skheberi.cs571app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;
import android.content.SharedPreferences;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SKheberi on 4/18/17.
 */

public class GroupFragmentFav extends Fragment {
    static int mk = 10;
    HttpClient httpclient;
    HttpPost httppost;
    HttpResponse response;
    List<NameValuePair> nameValuePairs;
    InputStream is;
    String result;
    ArrayList<JSONObject> jsonArr;
    ArrayList<JsonObject> user;
    ListAdapter myAdapter;
    ListView listView;

    public GroupFragmentFav() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_groupfav, container, false);
    listView = (ListView) view.findViewById(R.id.mainListView);

        try {

            Gson gson = new Gson();
            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
             //  editor.remove("group");
              // editor.apply();
           user = new ArrayList<JsonObject>();

            String strJson = sharedPreferences.getString("group","0");

// System.out.println("Retrieved value:"+strJson);
            user=gson.fromJson(strJson, new TypeToken<List<JsonObject>>(){}.getType());
         myAdapter =  new CustomAdapterFav(getActivity().getApplicationContext(),user);
            listView.setAdapter(myAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }



        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // editor.remove("user");
        //   editor.apply();
        user = new ArrayList<JsonObject>();

        String strJson = sharedPreferences.getString("group", "0");
        if (!strJson.equals("0")) {
// System.out.println("Retrieved value:"+strJson);
            user = gson.fromJson(strJson, new TypeToken<List<JsonObject>>() {
            }.getType());
            myAdapter = new CustomAdapterFav(getActivity().getApplicationContext(), user);

            listView.setAdapter(new CustomAdapterFav(getActivity().getApplicationContext(), user));
        }
    }
}