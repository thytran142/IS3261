package com.example.tranthy.project;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListItemAdapter extends ArrayAdapter
{
    Activity context;
    String[] items;
    int layoutId;
    int titleId;
   int contentId;

    ListItemAdapter(Activity context, int layoutId, int titleId, String[] items, int contentId )
    {
        super(context, layoutId, items);

        this.context = context;
        this.items = items;
        this.layoutId = layoutId;
        this.titleId = titleId;
       this.contentId=contentId;
    }

    public View getView(int pos, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater=context.getLayoutInflater();
        View row=inflater.inflate(layoutId, null);
        TextView label=(TextView)row.findViewById(titleId);
        if(pos==0){
            label.setText("Name");
        }
        else if(pos==1){
            label.setText("Phone number");
        }else if(pos==2){
            label.setText("Email");
        }
        TextView content=(TextView)row.findViewById(contentId);
        content.setText(items[pos]);
        return(row);
    }

} 