package com.example.tranthy.project;
/* This class is to create the account so user can have passcode every time they enter this class*/
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.app.ActionBar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageHistory extends Activity {
    private MessageDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        db = new MessageDB(this);
        setContentView(R.layout.message_history);
        ArrayList<String[]> msgHistory = GetMsgHistory();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.create_account_actions,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public ArrayList<String[]> GetMsgHistory() {
//--get all msg history---
        db.open();
        Cursor c = db.getAllMsgHistory();
        ArrayList<String[]> msgHistory = new ArrayList<String[]>();
        if (c.moveToFirst()){
            do {
                String[] history = {c.getString(0),c.getString(1),c.getString(2),c.getString(3)};
                msgHistory.add(history);
            } while (c.moveToNext());
        }
        db.close();
        return msgHistory;
    }

    public void insertRow(Long rowId,String receiver,String message, String time){
        ViewGroup table = (ViewGroup)findViewById(R.id.table);
        TableRow newRow = new TableRow(table.getContext());
        TextView idText = new TextView(newRow.getContext());
        TextView receiverText = new TextView(newRow.getContext());
        TextView messageText = new TextView(newRow.getContext());
        TextView timeText = new TextView(newRow.getContext());

        newRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        idText.setText(String.valueOf(rowId));
        idText.setLayoutParams(
                new TableRow.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,0.1f));
        idText.setGravity(Gravity.CENTER);

        receiverText.setText(String.valueOf(receiver));
        receiverText.setLayoutParams(
                new TableRow.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,0.2f));
        receiverText.setGravity(Gravity.CENTER);

        messageText.setText(String.valueOf(message));
        messageText.setLayoutParams(
                new TableRow.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,0.4f));
        messageText.setGravity(Gravity.CENTER);

        timeText.setText(String.valueOf(time));
        timeText.setLayoutParams(
                new TableRow.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,0.3f));
        timeText.setGravity(Gravity.CENTER);

        newRow.addView(idText);
        newRow.addView(receiverText);
        newRow.addView(messageText);
        newRow.addView(timeText);
        table.addView(newRow);

    }




}


