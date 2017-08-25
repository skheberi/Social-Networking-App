package com.example.skheberi.cs571app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class DetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdaptor viewPagerAdaptor;
    static String urlw,namew;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    Menu myMenu;
    String iden;
    MenuItem myMenuItem;
    static String sample="";
    static int resultTab=0;
    static int favTab = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar=(Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

//Extract the data…
        iden = bundle.getString("id");
        urlw=bundle.getString("url");
        namew=bundle.getString("name");
      //  Bundle bundle = getIntent().getExtras();
        System.out.println("Id is:"+ iden);
        bundle.putString("idttext",iden);
        if(bundle.getString("fav")!=null)
            bundle.putString("fav","someVal");
        //Bundle bundle = getIntent().getExtras();
        viewPagerAdaptor = new ViewPagerAdaptor(getSupportFragmentManager());
        PostsFragment posts = new PostsFragment();
        AlbumsFragment albums = new AlbumsFragment();
        albums.setArguments(bundle);
        posts.setArguments(bundle);
        viewPagerAdaptor.addFragments(albums,"Albums");
        viewPagerAdaptor.addFragments(posts,"Posts");
        viewPager.setAdapter(viewPagerAdaptor);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.albums);
        tabLayout.getTabAt(1).setIcon(R.drawable.posts);
        if(ResultActivity.tabLayout!=null)
        resultTab = ResultActivity.tabLayout.getSelectedTabPosition();
        if(ResultActivityFav.tabLayout!=null)
        favTab = ResultActivityFav.tabLayout.getSelectedTabPosition();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        myMenu=menu;
        Bundle bundle = getIntent().getExtras();

       // this.menu = menu;
        System.out.println("Iden is:"+iden);
        String holder="";
        int x=0;
        if(bundle.getString("fav")!=null)
        {
          x = ResultActivityFav.tabLayout.getSelectedTabPosition();
        }
        else {
            x = ResultActivity.tabLayout.getSelectedTabPosition();
        }
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

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<JsonObject> user = new ArrayList<JsonObject>();
        String strJson = sharedPreferences.getString(holder,"0");

        if(strJson!="0") {
            ArrayList<JsonObject> user2 = new ArrayList<JsonObject>();
            String strJson2 = sharedPreferences.getString(holder, "0");

// System.out.println("Retrieved value:"+strJson);
            user2 = gson.fromJson(strJson2, new TypeToken<List<JsonObject>>() {
            }.getType());


            for (JsonObject js : user2) {

                System.out.println("Inside For Loop");
                if (js.get("id").getAsString().equals(iden)) {

                    myMenuItem = menu.findItem(R.id.action_settings);
                    myMenuItem.setTitle("Remove from favorites");


                }


            }

        }

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
           // item.setTitle("Remove from favorites");
            addPreferences();
        }

        if(id==R.id.fb_share)
        {
           shareFacebook();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        String holder="";
        Bundle bundle = getIntent().getExtras();

        // this.menu = menu;
        System.out.println("Iden is:"+iden);
        //String holder="";
        int x=0;
        if(bundle.getString("fav")!=null)
        {
            x = ResultActivityFav.tabLayout.getSelectedTabPosition();
        }
        else {
            x = ResultActivity.tabLayout.getSelectedTabPosition();
        }
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
        myMenuItem = menu.findItem(R.id.action_settings);
        myMenuItem.setTitle("Add to favorites");

        // System.out.println("Holder is ************"+holder);
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<JsonObject> user = new ArrayList<JsonObject>();
        String strJson = sharedPreferences.getString(holder,"0");
      //
       // System.out.println("FoodText:"+foodtext);
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
                    itemIn = iden;

                    // System.out.println("Item Id"+ itemIn);

                    //System.out.println("Retrieved id:"+js.get("id").getAsString());


                } catch (Exception e) {
                    e.printStackTrace();
                }
                // System.out.println("Inside For Loop");
                if(js.get("id").getAsString().equals(itemIn)) {

                    myMenuItem = menu.findItem(R.id.action_settings);
                    myMenuItem.setTitle("Remove from favorites");

                }



            }
        }


        return super.onPrepareOptionsMenu(menu);
    }

    public void addPreferences() {


        Bundle bundle = getIntent().getExtras();
        String holder = "";
        int x =0;
        if(bundle.getString("fav")!=null)
        {
            x = ResultActivityFav.tabLayout.getSelectedTabPosition();
        }
        else {
            x = ResultActivity.tabLayout.getSelectedTabPosition();
        }
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


        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<JsonObject> user = new ArrayList<JsonObject>();
        String strJson = sharedPreferences.getString(holder, "0");
        if (strJson == "0") {
//


            JSONObject temp = CustomAdapter.fullJSON;
            JsonParser jsonParser = new JsonParser();
            JsonObject gsonObject = (JsonObject) jsonParser.parse(temp.toString());
            user.add(gsonObject);

            String myjson = gson.toJson(user);

            // System.out.println("myJson" + myjson);
            editor.putString(holder, myjson);
            editor.apply();
        } else {
            ArrayList<JsonObject> user2 = new ArrayList<JsonObject>();
            String strJson2 = sharedPreferences.getString(holder, "0");

// System.out.println("Retrieved value:"+strJson);
            user2 = gson.fromJson(strJson2, new TypeToken<List<JsonObject>>() {
            }.getType());

            JSONObject temp=null;
            JsonObject gsonObject=null;
            if(bundle.getString("fav")!=null)
            gsonObject = CustomAdapterFav.fullJSON;
            else {
                temp = CustomAdapter.fullJSON;
                JsonParser jsonParser = new JsonParser();
               gsonObject = (JsonObject) jsonParser.parse(temp.toString());
            }

            for (JsonObject js : user2) {
                if (js.get("id").getAsString().equals(iden)) {
                  user2.remove(js);
                    String myjson = gson.toJson(user2);


                    editor.putString(holder, myjson);
                    editor.apply();
                    Toast.makeText(this, "Removed from favorites!",
                            Toast.LENGTH_LONG).show();
                    return;
                }

            }




                user2.add(gsonObject);


            String myjson = gson.toJson(user2);


            editor.putString(holder, myjson);
            editor.apply();


        }


        //   editor.clear();
        Toast.makeText(this, "Added to favorites!",
                Toast.LENGTH_LONG).show();
        //  editor.remove("user");

    }

    public void shareFacebook()
    {


        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());
        shareDialog = new ShareDialog(this);



        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(DetailActivity.this, "You shared this post",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(DetailActivity.this, "Not shared",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {

            }});

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(CustomAdapter.selName)
                    .setContentDescription(
                            "FB SEARCH FROM USC CSCI571")
                    .setImageUrl(Uri.parse(CustomAdapter.selURL))
                    .setContentUrl(Uri.parse("http://cs-server.usc.edu:45678/"))
                    .build();

            Toast.makeText(DetailActivity.this, "Sharing "+ CustomAdapter.selName,
                    Toast.LENGTH_LONG).show();

            shareDialog.show(linkContent);
            // MessageDialog.show(FacebookActivity.this,linkContent);
        }

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
