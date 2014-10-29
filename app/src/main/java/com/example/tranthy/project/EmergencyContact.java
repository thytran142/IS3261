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
    String[] country_name={"Afghanistan","Bahrain","Bangladesh","Bhutan",
            "Brunei","Cambodia","China","East Timor"
            ,"India","Indonesia","Iran","Iraq","Israel",
            "Japan","Jordan","North Korea","South Korea",
            "Laos","Malaysia","Maldives","Mongolia","Myanmar",
            "Nepal","Pakistan","The Philipines","Qatar",
            "Russia","Saudi Arabia","Singapore","Sri Lanka",
            "Syria","Taiwan","Thailand","Turkey","United Arab Emirates","Vietnam","Yemen"};
    Integer[] flag={R.drawable.af,R.drawable.ba,R.drawable.bang,R.drawable.bhutan,
            R.drawable.brunei,R.drawable.cambodia,R.drawable.china,R.drawable.timor,
            R.drawable.india,R.drawable.indo,R.drawable.iran,R.drawable.iraq,R.drawable.isarel,
            R.drawable.japan,R.drawable.jordan,R.drawable.north_korea,R.drawable.south_korea,
            R.drawable.laos,R.drawable.malaysia,R.drawable.maldives,R.drawable.mongo,R.drawable.myanmar,
            R.drawable.nepal,R.drawable.pakistan,R.drawable.philipine,R.drawable.qatar,
            R.drawable.russia,R.drawable.saudi,R.drawable.singapore,R.drawable.sri_lanka,
            R.drawable.syria, R.drawable.taiwan,R.drawable.thailand,R.drawable.turkey,R.drawable.arab,
            R.drawable.vietnam,R.drawable.yemen
    };
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


