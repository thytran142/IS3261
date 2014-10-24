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
        else if(pos==3){
            label.setText("Option");

        }
        else if(pos==4){
            label.setText("Message");
        }
        TextView content=(TextView)row.findViewById(contentId);
      if(pos==3){
          if(items[3].equals("NO")) {
              content.setText("No Alerts");
          }else if(items[3].equals("BOTH")) content.setText("Alert through Email & SMS");
          else if(items[3].equals("EMAIL")) content.setText("Alert through Email");
          else if(items[3].equals("SMS")) content.setText("Alert through SMS");

      }else if(pos==4){
          String str=items[4].substring(0,21);
          content.setText(str);
      }
        else
        content.setText(items[pos]);
        return(row);
    }

} 