package com.example.tranthy.project;
/* This class is to handle the message custom content for each person*/
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class EmergencyContact extends Activity {
    ListView list;
    String[] country_name={"Vietnam","Singapore"};
    Integer[] flag={R.drawable.contact_red,R.drawable.edit_red};
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_contact);

        list=(ListView)findViewById(R.id.country_list);
        CountryListAdapter adapter=new CountryListAdapter(this,R.layout.country_item,R.id.flag,flag,R.id.country_item,country_name);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(EmergencyContact.this, "You Clicked at " + country_name[+position], Toast.LENGTH_SHORT).show();
            }
        });
 }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.message_setting_actions,menu);
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
}


