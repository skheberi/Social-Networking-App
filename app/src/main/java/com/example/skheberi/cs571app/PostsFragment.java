package com.example.skheberi.cs571app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment {
    static int mk=10;
    HttpClient httpclient;
    HttpPost httppost;
    HttpResponse response;
    List<NameValuePair> nameValuePairs;
    InputStream is;
    String result;
    static String url;
    ArrayList<JSONObject> jsonArr;

    public PostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_posts, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.mainListView);


        try {

            new Thread() {
                @Override
                public void run() {
                    //your code here
                    String iden = getArguments().getString("idttext");

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://sample-env-1.p7hvtdxr5s.us-west-2.elasticbeanstalk.com/");
                    try {


                        // EditText mEdit   = (EditText)findViewById(R.id.searchInput);
                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                        String holder = "";
                        int x=0;
                        if (getArguments().getString("fav")!=null)
                            x = ResultActivityFav.tabLayout.getSelectedTabPosition();
                        else
                        x = ResultActivity.tabLayout.getSelectedTabPosition();
                        if (x == 0)
                            holder = "user";
                        if (x == 1)
                            holder = "page";
                        if (x == 2)
                            holder = "events";
                        if (x == 3)
                            holder = "place";
                        if (x == 4)
                            holder = "group";
                        nameValuePairs.add(new BasicNameValuePair("iden",iden));
                        nameValuePairs.add(new BasicNameValuePair("postType", "group"));
                        nameValuePairs.add(new BasicNameValuePair("postGroup", holder));
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
                        result = sb.toString();
                        System.out.println("Post Response:"+result);

                    } catch (Exception e) {
                        Log.e("log_tag", "Error converting result " + e.toString());
                    }

                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(result);
                       // JSONArray arr = obj.getJSONArray("data");
                        //jsonArr = new ArrayList<JSONObject>();
                        JSONObject imageText = obj.getJSONObject("picture");
                        JSONObject dataText = imageText.getJSONObject("data");
                        // System.out.println("Image Text:" + imageText);
                        String url2=dataText.getString("url");
                        url=url2;
                        if(obj.has("posts")) {
                            JSONObject dat = obj.getJSONObject("posts");
                            JSONArray arr = dat.getJSONArray("data");
                            jsonArr = new ArrayList<JSONObject>();
                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject curr = arr.getJSONObject(i);
                                jsonArr.add(curr);
                                //  System.out.println(jsonArr.get(i));
                            }


                            final ListAdapter myAdapter = new CustomAdapter2(getActivity().getApplicationContext(), jsonArr);


                            listView.post(new Runnable() {
                                public void run() {
                                    listView.setAdapter(myAdapter);


                                }
                            });

                        }
                        else
                        {
                            final TextView textView = (TextView) view.findViewById(R.id.custTxtAbm);
                            final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.topMargin=450;

                            textView.post(new Runnable() {
                                public void run() {

                                    //  System.out.println("Adaper Set");

                                    textView.setLayoutParams(params);




                                }
                            });
                        }
                        // System.out.println(result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }
            }.start();










        } catch (Exception e) {
            e.printStackTrace();
        }








        // Inflate the layout for this fragment
        return view;


    }

}
