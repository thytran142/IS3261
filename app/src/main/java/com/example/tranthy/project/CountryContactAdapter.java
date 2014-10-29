package com.example.tranthy.project;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class CountryContactAdapter extends ArrayAdapter<String> {
    Activity context;
    String[] title;
    Integer[] label;
    int title_id;
    int label_id;
    int layoutId;
    public CountryContactAdapter(Activity context, int layoutId,int label_id,Integer[] label,int title_id, String[] title){
        super(context,R.layout.country_contact,title);
        this.context=context;
        this.label=label;
        this.title=title;
        this.label_id=label_id;
        this.title_id=title_id;
        this.layoutId=layoutId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(layoutId, null);
        ImageView imageView = (ImageView) rowView.findViewById(label_id);
        TextView titletext = (TextView) rowView.findViewById(title_id);

        imageView.setImageResource(label[position]);
        titletext.setText(title[position]);
        return rowView;
    }
}
