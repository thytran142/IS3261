package com.example.tranthy.project;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CountryListAdapter extends ArrayAdapter<String> {
      Activity context;
    String[] country_name;
     Integer[] flag;
    int flag_id;
    int country_id;
    int layoutId;
     public CountryListAdapter(Activity context, int layoutId,int flag_id,Integer[] flag,int country_id, String[] country_name){
         super(context,R.layout.emergency_contact,country_name);
         this.context=context;
         this.flag=flag;
         this.country_name=country_name;
         this.flag_id=flag_id;
         this.country_id=country_id;
         this.layoutId=layoutId;
     }
     @Override
    public View getView(int position, View convertView, ViewGroup parent){

             LayoutInflater inflater=context.getLayoutInflater();
             View rowView=inflater.inflate(layoutId, null);
             ImageView imageView = (ImageView) rowView.findViewById(flag_id);
             TextView countrytext = (TextView) rowView.findViewById(country_id);
             imageView.setImageResource(flag[position]);
             countrytext.setText(country_name[position]);
         return rowView;
     }
}
