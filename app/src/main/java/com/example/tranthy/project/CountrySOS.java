package com.example.tranthy.project;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;
/**
 * This class is to handle the dialog add manually contacts and input to the contact_list sql database
 */
public class CountrySOS extends Activity {
    Intent myIntent;
    ListView list;
    Integer[] label={R.drawable.ambulance,R.drawable.fire,R.drawable.police};
    String[] title={"Ambulance","Fire Services","National Police"};
   @Override
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       final String country= getIntent().getStringExtra("name");
       setTitle(getIntent().getStringExtra("name"));
       setContentView(R.layout.country_contact);
       list=(ListView)findViewById(R.id.country_contact);
       CountryContactAdapter adapter=new CountryContactAdapter(this,R.layout.sos_item,R.id.label,label,R.id.title_item,title);
       list.setAdapter(adapter);
       list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view,
                                   int position, long id) {
               String phoneNumber="";

              if(country.equalsIgnoreCase("Afghanistan")){
                  switch (position){
                      case 0:
                          phoneNumber="102";
                          break;
                      case 1:
                          phoneNumber="119";
                          break;
                      case 2:
                          phoneNumber="119";
                          break;
                  }
              }//end Afghanistan
               else if(country.equalsIgnoreCase("Bahrain")){
                 phoneNumber="999";
              }
               else if(country.equalsIgnoreCase("Bangladesh")){
                  switch (position){
                      case 0:
                          phoneNumber="199";
                          break;
                      case 1:
                          phoneNumber="9555555";
                          break;
                      case 2:
                          phoneNumber="999";
                          break;
                  }
              }//end Bangladesh
              else if(country.equalsIgnoreCase("Bhutan")){
                  switch (position){
                      case 0:
                          phoneNumber="110";
                          break;
                      case 1:
                          phoneNumber="112";
                          break;
                      case 2:
                          phoneNumber="113";
                          break;
                  }
              }//end Bhutan
              else if(country.equalsIgnoreCase("Brunei")){
                  switch (position){
                      case 0:
                          phoneNumber="991";
                          break;
                      case 1:
                          phoneNumber="995";
                          break;
                      case 2:
                          phoneNumber="993";
                          break;
                  }
              }//end Brunei
              else if(country.equalsIgnoreCase("Cambodia")){
                  switch (position){
                      case 0:
                          phoneNumber="119";
                          break;
                      case 1:
                          phoneNumber="118";
                          break;
                      case 2:
                          phoneNumber="117";
                          break;
                  }
              }//end Cambodia
              else if(country.equalsIgnoreCase("China")){
                  switch (position){
                      case 0:
                          phoneNumber="999";
                          break;
                      case 1:
                          phoneNumber="119";
                          break;
                      case 2:
                          phoneNumber="110";
                          break;
                  }
              }//end China
              else if(country.equalsIgnoreCase("East Timor")){
                phoneNumber="112";
              }//end East Timor
              else if(country.equalsIgnoreCase("India")){
                  switch (position){
                      case 0:
                          phoneNumber="102";
                          break;
                      case 1:
                          phoneNumber="101";
                          break;
                      case 2:
                          phoneNumber="100";
                          break;
                  }
              }//end India
              else if(country.equalsIgnoreCase("Indonesia")){
                  switch (position){
                      case 0:
                          phoneNumber="118";
                          break;
                      case 1:
                          phoneNumber="113";
                          break;
                      case 2:
                          phoneNumber="110";
                          break;
                  }
              }//end Indonesia
              else if(country.equalsIgnoreCase("Iran")){
                  switch (position){
                      case 0:
                          phoneNumber="115";
                          break;
                      case 1:
                          phoneNumber="125";
                          break;
                      case 2:
                          phoneNumber="110";
                          break;
                  }
              }//end Iran
              else if(country.equalsIgnoreCase("Iraq")){
                phoneNumber="0770-443-1286";
              }//end Iraq
              else if(country.equalsIgnoreCase("Israel")){
                  switch (position){
                      case 0:
                          phoneNumber="101";
                          break;
                      case 1:
                          phoneNumber="102";
                          break;
                      case 2:
                          phoneNumber="100";
                          break;
                  }
              }//end Israel
              else if(country.equalsIgnoreCase("Japan")){
                  switch (position){
                      case 0:
                          phoneNumber="119";
                          break;
                      case 1:
                          phoneNumber="119";
                          break;
                      case 2:
                          phoneNumber="110";
                          break;
                  }
              }//end Japan
              else if(country.equalsIgnoreCase("Jordan")){
                 phoneNumber="911";
              }//end Jordan
              else if(country.equalsIgnoreCase("North Korea")){
                  phoneNumber="119";
              }//end North Korea
              else if(country.equalsIgnoreCase("South Korea")){
                  switch (position){
                      case 0:
                          phoneNumber="119";
                          break;
                      case 1:
                          phoneNumber="119";
                          break;
                      case 2:
                          phoneNumber="112";
                          break;
                  }
              }//end South Korea
              else if(country.equalsIgnoreCase("Laos")){
                  switch (position){
                      case 0:
                          phoneNumber="195";
                          break;
                      case 1:
                          phoneNumber="190";
                          break;
                      case 2:
                          phoneNumber="191";
                          break;
                  }
              }//end Laos
              else if(country.equalsIgnoreCase("Malaysia")){
                 phoneNumber="999";
              }//end Malaysia
              else if(country.equalsIgnoreCase("Maldives")){
                phoneNumber="102";
              }//end Maldives
              else if(country.equalsIgnoreCase("Mongolia")){
                  switch (position){
                      case 0:
                          phoneNumber="103";
                          break;
                      case 1:
                          phoneNumber="101";
                          break;
                      case 2:
                          phoneNumber="102";
                          break;
                  }
              }//end Mongolia
              else if(country.equalsIgnoreCase("Myanmar")){
                 phoneNumber="191";
              }//end Myanmar
              else if(country.equalsIgnoreCase("Nepal")){
                  switch (position){
                      case 0:
                          phoneNumber="102";
                          break;
                      case 1:
                          phoneNumber="101";
                          break;
                      case 2:
                          phoneNumber="100";
                          break;
                  }
              }//end Nepal
              else if(country.equalsIgnoreCase("Pakistan")){
                  switch (position){
                      case 0:
                          phoneNumber="115";
                          break;
                      case 1:
                          phoneNumber="16";
                          break;
                      case 2:
                          phoneNumber="15";
                          break;
                  }
              }//end Pakistan
              else if(country.equalsIgnoreCase("The Philipines")){
                  switch (position){
                      case 0:
                          phoneNumber="117";
                          break;
                      case 1:
                          phoneNumber="911";
                          break;
                      case 2:
                          phoneNumber="112";
                          break;
                  }
              }//end Philipines
              else if(country.equalsIgnoreCase("Qatar")){
                 phoneNumber="999";
              }//end Qatar
              else if(country.equalsIgnoreCase("Russia")){
                 phoneNumber="112";
              }//end Russia
              else if(country.equalsIgnoreCase("Saudi Arabia")){
                  switch (position){
                      case 0:
                          phoneNumber="997";
                          break;
                      case 1:
                          phoneNumber="998";
                          break;
                      case 2:
                          phoneNumber="999";
                          break;
                  }
              }//end Saudi Arabia
              else if(country.equalsIgnoreCase("Singapore")){
                  switch (position){
                      case 0:
                          phoneNumber="995";
                          break;
                      case 1:
                          phoneNumber="995";
                          break;
                      case 2:
                          phoneNumber="999";
                          break;
                  }
              }//end Singapore
              else if(country.equalsIgnoreCase("Sri Lanka")){
                  switch (position){
                      case 0:
                          phoneNumber="110";
                          break;
                      case 1:
                          phoneNumber="111";
                          break;
                      case 2:
                          phoneNumber="118";
                          break;
                  }
              }//end Sri Lanka
              else if(country.equalsIgnoreCase("Syria")){
                  switch (position){
                      case 0:
                          phoneNumber="110";
                          break;
                      case 1:
                          phoneNumber="113";
                          break;
                      case 2:
                          phoneNumber="112";
                          break;
                  }
              }//end Syria
              else if(country.equalsIgnoreCase("Taiwan")){
                  switch (position){
                      case 0:
                          phoneNumber="119";
                          break;
                      case 1:
                          phoneNumber="119";
                          break;
                      case 2:
                          phoneNumber="110";
                          break;
                  }
              }//end Taiwan
              else if(country.equalsIgnoreCase("Thailand")){
                  switch (position){
                      case 0:
                          phoneNumber="1669";
                          break;
                      case 1:
                          phoneNumber="199";
                          break;
                      case 2:
                          phoneNumber="191";
                          break;
                  }
              }//end Thailand
              else if(country.equalsIgnoreCase("Turkey")){
                  switch (position){
                      case 0:
                          phoneNumber="112";
                          break;
                      case 1:
                          phoneNumber="110";
                          break;
                      case 2:
                          phoneNumber="155";
                          break;
                  }
              }//end Turkey
              else if(country.equalsIgnoreCase("United Arab Emirates")){
                  switch (position){
                      case 0:
                          phoneNumber="998";
                          break;
                      case 1:
                          phoneNumber="997";
                          break;
                      case 2:
                          phoneNumber="999";
                          break;
                  }
              }//end Arab
              else if(country.equalsIgnoreCase("Vietnam")){
                  switch (position){
                      case 0:
                          phoneNumber="115";
                          break;
                      case 1:
                          phoneNumber="114";
                          break;
                      case 2:
                          phoneNumber="113";
                          break;
                  }
              }//end Vietnam
              else if(country.equalsIgnoreCase("Yemen")){
                  switch (position){
                      case 0:
                          phoneNumber="191";
                          break;
                      case 1:
                          phoneNumber="191";
                          break;
                      case 2:
                          phoneNumber="194";
                          break;
                  }
              }//end Yemen
               Intent i;
               i= new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
               startActivity(i);
           }
       });
   }
}