package com.example.tranthy.project;
/* This class is to hanle the list of contacts user add to sms or email*/
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import android.view.ViewGroup;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.content.DialogInterface;
import android.view.ViewGroup.LayoutParams;
//Import library for the dialog
public class ContactSetting extends FragmentActivity
        implements AddManuallyContactInterface,EditContact.EditContactInterface
{

    contact_list db;
    ArrayList<String> stringList = new ArrayList<String>();
    RelativeLayout mainContact;
    RelativeLayout subContact;
    ListView contactList;
    ArrayAdapter adapter;
    String selected;
    Cursor cursor;
    Intent myIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_setting);
        db=new contact_list(this);
        //get the array of contacts already added
        ArrayList<String[]> contacts=null;
        try{
            contacts=getAllContactsAdded();
        }catch(SQLException e){
            e.printStackTrace();
        }
        for (int i = 0; i < contacts.size(); i++) {
            String[] contact = contacts.get(i);
            insertRow(Integer.parseInt(contact[0]), contact[1], contact[2],contact[3]);
        }
        //populate the arraylist
        getContacts();
        contactList = (ListView) findViewById(R.id.localContact);
        adapter = new ArrayAdapter(this, R.layout.customtextview, stringList);
        contactList.setAdapter(adapter);

         //disable the subcontact view
        mainContact = (RelativeLayout) findViewById(R.id.mainContact);
        subContact = (RelativeLayout) findViewById(R.id.subContact);
        subContact.setVisibility(View.GONE);
        //creating confirmation/alert dialog
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Confirmation");
        alertDialog.setMessage("Do you want to add this contact?");
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String[] contact = selected.split("\\s*:\\s*");
                Toast.makeText(getBaseContext(), contact[0] + " - " +contact[1], Toast.LENGTH_SHORT).show();
            }
        });

        //item listener for the local contact list
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = ((TextView) view).getText().toString();
                alertDialog.show();
            }
        });
    }

    public void showAddManuallyDialog(View view){
        showAddManually();
    }
    private void showAddManually(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        AddManuallyContact addManuallyContact=new AddManuallyContact();
        addManuallyContact.setCancelable(false);
        addManuallyContact.setDialogTitle("Add contact manually");
        addManuallyContact.show(getFragmentManager(),"input dialog");
    }

    public void showEditContact(Long rowId){
        FragmentManager fragmentManager=getSupportFragmentManager();
        EditContact editContact = new EditContact();
        editContact.setCancelable(false);
        Bundle args= new Bundle();
        args.putLong("key", rowId);
        editContact.setArguments(args);
        editContact.setDialogTitle("Edit contact");
        editContact.show(getFragmentManager(), "input dialog");
    }


    public void addContacts(String name, String number, String email) throws SQLException{
        db.open();
        long id = db.insertContacts(name, number,email);
        if (id > 0) {
            Toast.makeText(this, "Add successful.", Toast.LENGTH_LONG).show();

            insertRow(id, name, number,email);
        } else Toast.makeText(this, "Add failed", Toast.LENGTH_LONG).show();
        db.close();
    }
    public ArrayList<String[]> getAllContactsAdded() throws SQLException{
        db.open();
        Cursor c = db.getAllContacts();
        ArrayList<String[]> contacts = new ArrayList<String[]>();
        if (c.moveToFirst()) {
            do {
                String[] contact = {c.getString(0), c.getString(1), c.getString(2),c.getString(3)};
                contacts.add(contact);
            } while (c.moveToNext());

        }
        db.close();
        return contacts;
    }

    //insert contacts added into table
    public void insertRow(final long rowId, final String name, final String number, final String email){
        ViewGroup table=(ViewGroup)findViewById(R.id.table_contact);
        TableRow newRow=new TableRow(table.getContext());
        newRow.setId((int) rowId);
        TextView nameText=new TextView(newRow.getContext());
        TextView numberText=new TextView(newRow.getContext());
         nameText.setText(String.valueOf(name));
        nameText.setLayoutParams(new TableRow.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,0.4f));
        nameText.setGravity(Gravity.CENTER);

        numberText.setText(String.valueOf(number));
        numberText.setLayoutParams(new TableRow.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,0.3f));
        numberText.setGravity(Gravity.CENTER);


        Button btnEdit=new Button(this);
        btnEdit.setText("Edit");
        btnEdit.setTextSize(12);
        btnEdit.setOnClickListener(new View.OnClickListener() {
         @Override
          public void onClick(final View view) {
             showEditContact(rowId);


           }
         });
       // newRow.addView(idText);
        newRow.addView(nameText);
        newRow.addView(numberText);
        newRow.addView(btnEdit);
        table.addView(newRow);

    }
    //delete row in the table when you delete the contact

    public void deleteContact(long rowId) throws SQLException{
            db.open();
            if(db.deleteContact(rowId))
                Toast.makeText(this,"Delete successful",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this,"Delete failed",Toast.LENGTH_SHORT).show();
            db.close();
        }
    @Override
    public void deleteThisContact(long rowId) throws SQLException{
        deleteContact(rowId);
        ViewGroup table=(ViewGroup)findViewById(R.id.table_contact);
        TableRow row=(TableRow)table.findViewById((int) rowId);

        table.removeView(row);
        table.invalidate();

    }
    public void UpdateContact(long rowId,String name, String number,String email) throws SQLException{
             db.open();
             if(db.updateContact(rowId,name,number,email)) {
                 Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();
              }
             else
                 Toast.makeText(this,"Update failed.",Toast.LENGTH_SHORT).show();
             db.close();

         }
    public void UpdateContactName(long rowId, String newName) throws SQLException{
        db.open();
        if(db.updateContactName(rowId, newName)) {
            Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();
           // ViewGroup table=(ViewGroup)findViewById(R.id.table_contact);
          //  TableRow newRow=(TableRow)table.findViewById((int) rowId);
         //   TextView nameText=(TextView)newRow.findViewById(R.id.txt_contact_name);
         //   nameText.setText(newName);
            myIntent=new Intent(this,ContactSetting.class);
            startActivity(myIntent);
        }
        else
            Toast.makeText(this,"Update failed.",Toast.LENGTH_SHORT).show();
        db.close();
    }
    public void UpdateContactNumber(long rowId,String newNumber) throws SQLException{
        db.open();
        if(db.updateContactNumber(rowId, newNumber)) {
            Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();
            //ViewGroup table=(ViewGroup)findViewById(R.id.table_contact);
            //TableRow newRow=(TableRow)table.findViewById((int) rowId);
            //TextView nameText=(TextView)newRow.findViewById(R.id.txt_phone_number);
            //nameText.setText(newNumber);
        }
        else
            Toast.makeText(this,"Update failed.",Toast.LENGTH_SHORT).show();
        db.close();
    }
    public void UpdateContactEmail(long rowId,String newNumber) throws SQLException{
        db.open();
        if(db.updateContactEmail(rowId, newNumber)) {
            Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"Update failed.",Toast.LENGTH_SHORT).show();
        db.close();
    }
    @Override
         public void submitContact(String name, String number, String email) throws SQLException{
             //testing
            // Toast.makeText(this,"Returned from dialog: "+name + ", "+number+", "+email,Toast.LENGTH_SHORT).show();
             addContacts(name,number,email);
         }
    @Override
         public void editContact(long rowId,String name, String number, String email) throws SQLException{

             UpdateContact(rowId,name,number,email);
         }
    @Override
        public void editContactName(long rowId,String newName) throws SQLException {
        UpdateContactName(rowId,newName);
    }
    @Override
        public void editContactNumber(long rowId,String newNumber) throws SQLException

    {UpdateContactNumber(rowId,newNumber);}
    @Override
        public void editContactEmail(long rowId,String newEmail)throws SQLException
    { UpdateContactEmail(rowId,newEmail);}
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
                        if(number.length()>1) {
                            String mergeInfo = name.toUpperCase() + " : " + number;
                            stringList.add(mergeInfo);
                        }
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


