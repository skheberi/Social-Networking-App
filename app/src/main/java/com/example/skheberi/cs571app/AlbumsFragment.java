package com.example.skheberi.cs571app;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
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
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumsFragment extends Fragment {
    static int mk=10;
    HttpClient httpclient;
    HttpPost httppost;
    HttpResponse response;
    List<NameValuePair> nameValuePairs;
    InputStream is;
    String result;
    static String url;
    ArrayList<JSONObject> jsonArr;

    public AlbumsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_albums, container, false);
      //  final ListView listView = (ListView) view.findViewById(R.id.mainListView);


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
                        nameValuePairs.add(new BasicNameValuePair("iden",iden));
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
                        nameValuePairs.add(new BasicNameValuePair("albumType",holder));
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
                        System.out.println("Album Response:"+result);

                    } catch (Exception e) {
                        Log.e("log_tag", "Error converting result " + e.toString());
                    }

                    JSONObject obj = null;
                    JSONObject obj2 = null;
                    try {
                        obj2 = new JSONObject(result);
                               if(obj2.has("albums")) {
                                   obj = obj2.getJSONObject("albums");
                                   List<String> headings = new ArrayList<String>();
                                   HashMap<String, List<String>> childList = new HashMap<String, List<String>>();
                                   JSONArray arr = obj.getJSONArray("data");
                                   for (int i = 0; i < arr.length(); i++) {
                                       String name = arr.getJSONObject(i).getString("name");
                                       headings.add(name);
                                       JSONObject photos=null;
                                       if(arr.getJSONObject(i).has("photos")) {
                                           photos = arr.getJSONObject(i).getJSONObject("photos");
                                           JSONArray arr2 = photos.getJSONArray("data");
                                           List<String> urlss = new ArrayList<String>();
                                           for (int j = 0; j < arr2.length(); j++) {
                                               String url = arr2.getJSONObject(j).getString("url");
                                               urlss.add(url);
                                           }
                                           childList.put(headings.get(i), urlss);
                                       }
                                       else
                                           childList.put(headings.get(i),null);
                                   }
                      /*  //jsonArr = new ArrayList<JSONObject>();
                        JSONObject imageText = obj.getJSONObject("picture");
                        JSONObject dataText = imageText.getJSONObject("data");
                        // System.out.println("Image Text:" + imageText);
                        String url2=dataText.getString("url");
                        url=url2;
                        JSONObject dat = obj.getJSONObject("posts");
                        JSONArray arr = dat.getJSONArray("data");
                        jsonArr = new ArrayList<JSONObject>();
                        for(int i=0;i<arr.length();i++)
                        {

                            JSONObject curr = arr.getJSONObject(i);
                            jsonArr.add(curr);
                            //  System.out.println(jsonArr.get(i));
                        }
                            */
                                   final ExpandableListView expandableListView;
                                   expandableListView = (ExpandableListView) view.findViewById(R.id.expListView);
                                 //  expandableListView.setIndicatorBounds(50,50);
                                   // List<String> headings = new ArrayList<String>();
                      /*  List<String> l1 = new ArrayList<String>();
                        List<String> l2 = new ArrayList<String>();
                        List<String> l3 = new ArrayList<String>();
                        List<String> l4 = new ArrayList<String>();
                        List<String> l5 = new ArrayList<String>();


                        l1.add("one");
                        l1.add("two");
                        l2.add("one");
                        l2.add("three");
                        l3.add("three");
                        l3.add("four");
                        l4.add("four");
                        l5.add("five");

                        childList.put(headings.get(0),l1);
                        childList.put(headings.get(1),l2);
                        childList.put(headings.get(2),l3);   */

                                   final ExpandableAdapterClass expandableAdapterClass = new ExpandableAdapterClass(getActivity().getApplicationContext(), headings, childList);


                                   expandableListView.post(new Runnable() {
                                       public void run() {
                                           expandableListView.setAdapter(expandableAdapterClass);
                                           //  System.out.println("Adaper Set");






                                       }
                                   });

                               }
                                else
                                {
                                    final TextView textView = (TextView) view.findViewById(R.id.custTxtAbm);
                                   // textView.setTypeface(textView.getTypeface(),Typeface.BOLD);
                                  //  textView.setText("No albums available to display");
                                    final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    params.topMargin=450;

                                    textView.post(new Runnable() {
                                        public void run() {

                                            //  System.out.println("Adaper Set");

                                            textView.setLayoutParams(params);




                                        }
                                    });

                                    //return view;


                                }






                        // System.out.println(result);
                    } catch (Exception e) {


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
