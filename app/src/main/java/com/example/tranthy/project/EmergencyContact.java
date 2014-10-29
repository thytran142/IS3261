package com.example.tranthy.project;
/* This class is to handle the message custom content for each person*/
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class EmergencyContact extends Activity {
    ListView list;
    Intent myIntent;
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
              myIntent = new Intent(getApplicationContext(),CountrySOS.class);
              switch(position){
                  case 0:
                        myIntent.putExtra("name","Afghanistan");
                      break;
                  case 1:
                      myIntent.putExtra("name","Bahrain");
                      break;
                  case 2:
                      myIntent.putExtra("name","Bangladesh");
                      break;
                  case 3:
                      myIntent.putExtra("name","Bhutan");
                      break;
                  case 4:
                      myIntent.putExtra("name","Brunei");
                      break;
                  case 5:
                      myIntent.putExtra("name","Cambodia");
                      break;
                  case 6:
                      myIntent.putExtra("name","China");
                      break;
                  case 7:
                      myIntent.putExtra("name","East Timor");
                      break;
                  case 8:
                      myIntent.putExtra("name","India");
                      break;
                  case 9:
                      myIntent.putExtra("name","Indonesia");
                      break;
                  case 10:
                      myIntent.putExtra("name","Iran");
                      break;
                  case 11:
                      myIntent.putExtra("name","Iraq");
                      break;
                  case 12:
                      myIntent.putExtra("name","Israel");
                      break;
                  case 13:
                      myIntent.putExtra("name","Japan");
                      break;
                  case 14:
                      myIntent.putExtra("name","Jordan");
                      break;
                  case 15:
                      myIntent.putExtra("name","North Korea");
                      break;
                  case 16:
                      myIntent.putExtra("name","South Korea");
                      break;
                  case 17:
                      myIntent.putExtra("name","Laos");
                      break;
                  case 18:
                      myIntent.putExtra("name","Malaysia");
                      break;
                  case 19:
                      myIntent.putExtra("name","Maldives");
                      break;
                  case 20:
                      myIntent.putExtra("name","Mongolia");
                      break;
                  case 21:
                      myIntent.putExtra("name","Myanmar");
                      break;
                  case 22:
                      myIntent.putExtra("name","Nepal");
                      break;
                  case 23:
                      myIntent.putExtra("name","Pakistan");
                      break;
                  case 24:
                      myIntent.putExtra("name","The Philipines");
                      break;
                  case 25:
                      myIntent.putExtra("name","Qatar");
                      break;
                  case 26:
                      myIntent.putExtra("name","Russia");
                      break;
                  case 27:
                      myIntent.putExtra("name","Saudi Arabia");
                      break;
                  case 28:
                      myIntent.putExtra("name","Singapore");
                      break;
                  case 29:
                      myIntent.putExtra("name","Sri Lanka");
                      break;
                  case 30:
                      myIntent.putExtra("name","Syria");
                      break;
                  case 31:
                      myIntent.putExtra("name","Taiwan");
                      break;
                  case 32:
                      myIntent.putExtra("name","Thailand");
                      break;
                  case 33:
                      myIntent.putExtra("name","Turkey");
                      break;
                  case 34:
                      myIntent.putExtra("name","United Arab Emirates");
                      break;
                  case 35:
                      myIntent.putExtra("name","Vietnam");
                      break;
                  case 36:
                      myIntent.putExtra("name","Yemen");
                      break;
             }

              startActivity(myIntent);
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


