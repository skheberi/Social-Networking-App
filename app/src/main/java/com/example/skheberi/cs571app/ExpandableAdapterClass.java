package com.example.skheberi.cs571app;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by SKheberi on 4/15/17.
 */

public class ExpandableAdapterClass extends BaseExpandableListAdapter {
    List<String> header_titles;
    HashMap<String,List<String>> child_titles;
    Context ctx;

    ExpandableAdapterClass(Context ctx,List<String> header_titles,HashMap<String,List<String>> child_titles)
    {
        this.ctx=ctx;
        this.header_titles=header_titles;
        this.child_titles=child_titles;

    }

    @Override
    public int getGroupCount() {
        return header_titles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(child_titles.get(header_titles.get(groupPosition))!=null)
        return child_titles.get(header_titles.get(groupPosition)).size();
        else
            return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return header_titles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child_titles.get(header_titles.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
       String title = (String) this.getGroup(groupPosition);
        if(convertView==null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.parent_layout,null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.albumHeading);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextSize(18);
        textView.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin=140;
        textView.setLayoutParams(params);
       // textView.setMarginLeft()
       // textView.setBackgroundResource(R.color.lightPurple);
        textView.setText(title);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String title = (String) this.getChild(groupPosition,childPosition);
        if(convertView==null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_layout,null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.albumCont);

        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        Picasso.with(this.ctx).load(title).resize(width,1000).into(imageView);


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
