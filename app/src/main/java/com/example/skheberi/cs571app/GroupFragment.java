package com.example.skheberi.cs571app;


        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ListAdapter;
        import android.widget.ListView;

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
public class GroupFragment extends Fragment {
    static int mk=10;
    HttpClient httpclient;
    HttpPost httppost;
    HttpResponse response;
    List<NameValuePair> nameValuePairs;
    InputStream is;
    String result;
    ListView listView;
    ListAdapter myAdapter;
    ArrayList<JSONObject> jsonArr;


    public GroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_group, container, false);
         listView = (ListView) view.findViewById(R.id.mainListView);
        final Button btnNext = (Button) view.findViewById(R.id.btnNext);
        final Button btnPrev = (Button) view.findViewById(R.id.btnPrev);

        try {

            new Thread() {
                @Override
                public void run() {
                    //your code here
                    String Keyword = getArguments().getString("edttext");

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://sample-env-1.p7hvtdxr5s.us-west-2.elasticbeanstalk.com/");
                    try {


                        // EditText mEdit   = (EditText)findViewById(R.id.searchInput);
                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                        nameValuePairs.add(new BasicNameValuePair("Keyword",Keyword));
                        nameValuePairs.add(new BasicNameValuePair("Type", "group"));
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
                        System.out.println(result);

                    } catch (Exception e) {
                        Log.e("log_tag", "Error converting result " + e.toString());
                    }

                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(result);
                        JSONArray arr = obj.getJSONArray("data");
                        jsonArr = new ArrayList<JSONObject>();
                        for(int i=0;i<arr.length();i++)
                        {

                            JSONObject curr = arr.getJSONObject(i);
                            jsonArr.add(curr);
                            System.out.println(jsonArr.get(i));
                        }


                        System.out.println("Size is:"  + jsonArr.size());
                        ArrayList<JSONObject> al = new ArrayList<JSONObject>();
                        final int x= Math.min(10,jsonArr.size());
                        for(int i=0;i<x;i++)
                        {
                            al.add(jsonArr.get(i));
                           // currEnd++;
                        }


                         myAdapter =  new CustomAdapter(getActivity().getApplicationContext(),al,"group");


                        listView.post(new Runnable() {
                            public void run() {
                                if(x<10)
                                    btnNext.setEnabled(false);
                                listView.setAdapter(myAdapter);
                                if(GroupFragment.mk==10)
                                    btnPrev.setEnabled(false);
                               // if(UserFragment.mk==)
                                btnNext.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        int k=GroupFragment.mk;
                                        ArrayList<JSONObject> jarr = new ArrayList<JSONObject>();
                                        if(k==20)
                                        {
                                            int m= Math.min(k+5,jsonArr.size());
                                            System.out.println("m is"+m);
                                            for(int i=k;i<m;i++)
                                            {
                                                jarr.add(jsonArr.get(i));
                                            }

                                        }
                                        else {
                                            int m= Math.min(k+10,jsonArr.size());
                                            for (int i = k; i < m; i++) {
                                                jarr.add(jsonArr.get(i));
                                            }
                                        }
                                        ListAdapter myAdapter2 =  new CustomAdapter(getActivity().getApplicationContext(),jarr,"group");
                                        listView.setAdapter(myAdapter2);
                                        k+=10;
                                        GroupFragment.mk+=10;
                                        if(GroupFragment.mk<=10)
                                            btnPrev.setEnabled(false);
                                        else
                                            btnPrev.setEnabled(true);
                                        if(GroupFragment.mk>=jsonArr.size())
                                            btnNext.setEnabled(false);
                                        else
                                            btnNext.setEnabled(true);


                                    }
                                });

                                btnPrev.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        int k=GroupFragment.mk;
                                        System.out.println("JSON Size value:"  + jsonArr.size());
                                        ArrayList<JSONObject> jarr = new ArrayList<JSONObject>();
                                        if(k==30)
                                        {
                                            for(int i=10;i<20;i++)
                                            {
                                                jarr.add(jsonArr.get(i));
                                            }

                                        }
                                        else {
                                            for (int i = 0; i < 10; i++) {
                                                jarr.add(jsonArr.get(i));
                                            }
                                        }
                                        ListAdapter myAdapter2 =  new CustomAdapter(getActivity().getApplicationContext(),jarr,"group");
                                        listView.setAdapter(myAdapter2);
                                        k-=10;
                                        GroupFragment.mk-=10;
                                        if(GroupFragment.mk<=10)
                                            btnPrev.setEnabled(false);
                                        else
                                            btnPrev.setEnabled(true);
                                        if(GroupFragment.mk>=jsonArr.size())
                                            btnNext.setEnabled(false);
                                        else
                                            btnNext.setEnabled(true);


                                    }
                                });



                            }
                        });


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

    @Override
    public void onResume() {
        super.onResume();
        listView.setAdapter(myAdapter);
    }

}
