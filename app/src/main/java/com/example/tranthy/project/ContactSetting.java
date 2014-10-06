package com.example.tranthy.project;
/* This class is to hanle the list of contacts user add to sms or email*/
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.app.ActionBar;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.example.tranthy.project.AddManuallyContact.AddManuallyContactInterface;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

//Import library for the dialog
public class ContactSetting extends FragmentActivity
        implements AddManuallyContactInterface
{


    ArrayList<String> stringList = new ArrayList<String>();
    RelativeLayout mainContact;
    RelativeLayout subContact;
    ListView contactList;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.contact_setting);
        //populate the arraylist
        getContacts();
        contactList = (ListView) findViewById(R.id.contactslist);
        adapter = new ArrayAdapter(this, android.R.layout.simple_selectable_list_item, stringList);
        contactList.setAdapter(adapter);
        //disable the subcontact view
        mainContact = (RelativeLayout)findViewById(R.id.mainContact);
        subContact = (RelativeLayout)findViewById(R.id.subContact);
        subContact.setVisibility(View.GONE);

    }
    public void showAddManuallyDialog(View view){
        showAddManually();
    }
    private void showAddManually(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        AddManuallyContact addManuallyContact=new AddManuallyContact();
        addManuallyContact.setCancelable(false);
        addManuallyContact.setDialogTitle("Add Manually");
        addManuallyContact.show(getFragmentManager(),"input dialog");
    }


    @Override
    public void onFinishInputDialog(String inputText){
        //testing
        Toast.makeText(this,"Returned from dialog: "+inputText,Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.contact_setting_actions,menu);
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
    //initial step to query all the contacts with phone number and store in arraylist
    public void getContacts(){
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {

                        String number = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        //testing toast message
                        //Toast.makeText(this,"ID : "+id+ ", NAME : "+name+ ", NUMBER : "+number ,Toast.LENGTH_SHORT).show();

                        String mergeInfo = name.toUpperCase() + " : "+ number;
                        stringList.add(mergeInfo);
                    }
                    pCur.close();
                }


            }
        }



    }
    //switch between view
    public void showSubContact(View view){

        mainContact.setVisibility(View.GONE);
        subContact.setVisibility(View.VISIBLE);
    }

    public void showMainContact(View view){
        subContact.setVisibility(View.GONE);
        mainContact.setVisibility(View.VISIBLE);

    }


}


