package com.example.tranthy.project;
/* This class is to create the account so user can have passcode every time they enter this class*/
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MessageHistory extends Activity {
    private MessageDB db;
    private View background;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        db = new MessageDB(this);
        setContentView(R.layout.message_history);



        ArrayList<String[]> msgHistory = GetMsgHistory();
        //Toast msg to indicate if it is empty


        if(msgHistory.isEmpty()){
            Toast.makeText(this, "Message History is empty", Toast.LENGTH_SHORT).show();
        }
        else{
            listView = (ListView) findViewById(R.id.history_listView);
            listView.setAdapter(new customAdapter(this, msgHistory));
        }

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

    public void clearAll(View view){
        final Intent myIntent = new Intent(this,MessageHistory.class);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Confirmation");
        alertDialog.setMessage("Do you want to clear the history?");
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                db.open();
                db.deleteMsgHistory();
                db.close();
                finish();
                startActivity(myIntent);
            }
        });



        alertDialog.show();


    }

    class customAdapter extends BaseAdapter {

        Context context;
        ArrayList<String[]> data;
        private LayoutInflater inflater = null;

        public customAdapter(Context context, ArrayList<String[]> data) {
            // TODO Auto-generated constructor stub
            this.context = context;
            this.data = data;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View vi = convertView;
            if (vi == null)
                vi = inflater.inflate(R.layout.customlistview, null);
            TextView header = (TextView) vi.findViewById(R.id.header);
            TextView time = (TextView) vi.findViewById(R.id.time);
            header.setText(data.get(position)[1]);
            time.setText(data.get(position)[3]);
            ImageView icon_type = (ImageView) vi.findViewById(R.id.icon_type);
            TextView text = (TextView) vi.findViewById(R.id.text);
            if(data.get(position)[2].substring(0,3).equalsIgnoreCase("SMS")){
                icon_type.setBackgroundResource(R.drawable.sms_icon);
                text.setText(data.get(position)[2].substring(9));
            }
            else if(data.get(position)[2].substring(0,5).equalsIgnoreCase("EMAIL")){
                icon_type.setBackgroundResource(R.drawable.gmail_icon);
                text.setText(data.get(position)[2].substring(11));
            }





            return vi;
        }
    }




}


