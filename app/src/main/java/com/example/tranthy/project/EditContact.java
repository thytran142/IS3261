package com.example.tranthy.project;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
 import android.content.DialogInterface;
 import android.support.v4.app.FragmentManager;
 import android.text.Editable;
 import android.text.InputType;
 import android.view.LayoutInflater;
 import android.view.ViewGroup;
 import android.view.Window;
 import android.widget.ArrayAdapter;
 import android.widget.EditText;
 import android.view.WindowManager.LayoutParams;
 import android.widget.Button;
 import android.view.View;
 import android.os.Bundle;
 import android.text.TextWatcher;
 import android.widget.TextView;
 import android.widget.ListView;
 import android.widget.Toast;
 import android.widget.AdapterView;
 import java.sql.SQLException;
 import android.database.Cursor;
 import android.widget.AdapterView.OnItemClickListener;
import android.content.Intent;
/**
  * This class is to handle the dialog add manually contacts and input to the contact_list sql database
  */
 public class EditContact extends DialogFragment {
     static String dialogTitle;
     ListView listView;
     contact_list db;
    Button btn_delete;
     Button btn_back;

     //Interface containing methods to be implemented by calling activity
     public interface EditContactInterface{
         //add functions here to be called from dialog

         void editContactName(long rowId,String name) throws SQLException;
         void editContactNumber(long rowId,String number) throws SQLException;
         void editContactEmail(long rowId,String email) throws SQLException;
         void deleteThisContact(long rowId) throws SQLException;
         void editContactOption(long rowId, String option) throws SQLException;
         void editContactMessage(long rowId, String message) throws SQLException;
         boolean hasEmail(long rowId) throws SQLException;
         String getMessage(long rowId) throws SQLException;
     }
     //Empty constructor required
     public EditContact(){}
     public void setDialogTitle(String title){
         dialogTitle=title;
     }
     private boolean isEmpty(EditText input){
         if(input.getText().toString().trim().length()>0){
             return false;
         }else return true;
     }
     public String[] GetContact (long rowId) throws SQLException{
         db.open();
         Cursor c=db.getContact(rowId);
         String[] contact=new String[3];
         if(c.moveToFirst()){
             String[]temp={c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5)};
             contact=temp;
         }
         else
             Toast.makeText(getActivity(),"No contact found",Toast.LENGTH_LONG).show();
         db.close();
         return contact;
     }
     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
         View view= inflater.inflate(R.layout.editcontact,container);
 //View rowView=inflater.inflate(R.layout.list_item,container);
 //TextView label=(TextView)rowView.findViewById(R.id.item_title);
 // TextView content=(TextView)rowView.findViewById(R.id.item_content);
         super.onCreate(savedInstanceState);
 //Get the edit text and button views
         Bundle mArgs=getArguments();
         final Long rowId=mArgs.getLong("key");
 //get listView from xml
         listView=(ListView)view.findViewById(R.id.editList);
 //defined array values to show in ListView
         db=new contact_list(getActivity());
         String[] values=null;
         try{
             values=GetContact(rowId);
         }catch(SQLException e){
             e.printStackTrace();
         }
 //defined a new adapter
         ArrayAdapter editAdapter=new ListItemAdapter(getActivity(), R.layout.list_item,R.id.item_title, values,R.id.item_content);
         listView.setAdapter(editAdapter);
 // ListView Item Click Listener
         listView.setOnItemClickListener(new OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> parent, final View view,
                                                                     int position, long id) {
                                                 // ListView Clicked item index
                                                 int itemPosition = position;
                                                 if (itemPosition == 0) {
                                                     AlertDialog.Builder alertDialog1;
                                                     alertDialog1 = new AlertDialog.Builder(getActivity());
                                                     // Setting Dialog Title
                                                     alertDialog1.setTitle("Name");
                                                     // Setting Positive "Yes" Btn
                                                     final EditText nameInput = new EditText(getActivity());
                                                     String itemValue = (String) listView.getItemAtPosition(position);
                                                     nameInput.setText(itemValue);
                                                     alertDialog1.setView(nameInput);
                                                     alertDialog1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                         public void onClick(DialogInterface dialog, int which) {
                                                             //need validation here
                                                             String newName = nameInput.getText().toString();
                                                             if (isEmpty(nameInput)) {
                                                                 //blank
                                                                 Toast.makeText(getActivity(), "The name field cannot be empty", Toast.LENGTH_SHORT).show();
                                                             } else {
                                                                 //save the value from input in TextEdit to ListView item position 1
                                                                 TextView name = (TextView) view.findViewById(R.id.item_content);
                                                                 name.setText(newName);
                                                                 EditContactInterface activity = (EditContactInterface) getActivity();
                                                                 try {
                                                                     activity.editContactName(rowId, newName);

                                                                 } catch (SQLException e) {
                                                                     e.printStackTrace();
                                                                 }
                                                             }
                                                         }
                                                     });
                                                     // Setting Negative "NO" Btn
                                                     alertDialog1.setNegativeButton("Cancel",
                                                             new DialogInterface.OnClickListener() {
                                                                 public void onClick(DialogInterface dialog, int which) {
                                                                     dialog.cancel();
                                                                 }
                                                             });
                                                     // Showing Alert Dialog
                                                     alertDialog1.show();
                                                 }//end position 0
                                                 else if (position == 1) {
                                                     AlertDialog.Builder alertDialog2;
                                                     alertDialog2 = new AlertDialog.Builder(getActivity());
                                                     // Setting Dialog Title
                                                     alertDialog2.setTitle("Phone number");
                                                     // Setting Positive "Yes" Btn
                                                     final EditText phoneInput = new EditText(getActivity());
                                                     phoneInput.setInputType(InputType.TYPE_CLASS_PHONE);
                                                     String itemValue = (String) listView.getItemAtPosition(position);
                                                     phoneInput.setText(itemValue);
                                                     alertDialog2.setView(phoneInput);
                                                     alertDialog2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                         public void onClick(DialogInterface dialog, int which) {
                                                             //need validation here
                                                             String newNumber = phoneInput.getText().toString();
                                                             if (isEmpty(phoneInput)) {
                                                                 //blank
                                                                 Toast.makeText(getActivity(), "The phone number field cannot be empty", Toast.LENGTH_SHORT).show();
                                                             } else {
                                                                 //save the value from input in TextEdit to ListView item position 1
                                                                 TextView phone = (TextView) view.findViewById(R.id.item_content);
                                                                 phone.setText(newNumber);
                                                                 EditContactInterface activity = (EditContactInterface) getActivity();
                                                                 try {
                                                                     activity.editContactNumber(rowId, newNumber);

                                                                 } catch (SQLException e) {
                                                                     e.printStackTrace();
                                                                 }
                                                             }
                                                         }
                                                     });
                                                     // Setting Negative "NO" Btn
                                                     alertDialog2.setNegativeButton("Cancel",
                                                             new DialogInterface.OnClickListener() {
                                                                 public void onClick(DialogInterface dialog, int which) {
                                                                     dialog.cancel();
                                                                 }
                                                             });
                                                     // Showing Alert Dialog
                                                     alertDialog2.show();
                                                 }//end position1
                                                 else if (position == 2) {
                                                     AlertDialog.Builder alertDialog3;
                                                     alertDialog3 = new AlertDialog.Builder(getActivity());
                                                     // Setting Dialog Title
                                                     alertDialog3.setTitle("Email");
                                                     // Setting Positive "Yes" Btn
                                                     final EditText emailInput = new EditText(getActivity());
                                                     emailInput.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                                                     String itemValue = (String) listView.getItemAtPosition(position);
                                                     emailInput.setText(itemValue);
                                                     alertDialog3.setView(emailInput);
                                                     alertDialog3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                         public void onClick(DialogInterface dialog, int which) {
                                                             //need validation here
                                                             String newEmail = emailInput.getText().toString();

                                                             //save the value from input in TextEdit to ListView item position 1
                                                             TextView email = (TextView) view.findViewById(R.id.item_content);
                                                             email.setText(newEmail);
                                                             EditContactInterface activity = (EditContactInterface) getActivity();
                                                             try {
                                                                 activity.editContactEmail(rowId, newEmail);

                                                             } catch (SQLException e) {
                                                                 e.printStackTrace();
                                                             }
                                                         }
                                                     });
                                                     // Setting Negative "NO" Btn
                                                     alertDialog3.setNegativeButton("Cancel",
                                                             new DialogInterface.OnClickListener() {
                                                                 public void onClick(DialogInterface dialog, int which) {
                                                                     dialog.cancel();
                                                                 }
                                                             });
                                                     // Showing Alert Dialog
                                                     alertDialog3.show();
                                                 }//end position2
                                                 else if (position == 3) {
                                                     CharSequence options[] = new CharSequence[]{"No Alerts", "Alert through Email & SMS", "Alert through Email", "Alert through SMS"};
                                                     final AlertDialog.Builder alertDialog4;
                                                     alertDialog4 = new AlertDialog.Builder(getActivity());
                                                     // Setting Dialog Title
                                                     alertDialog4.setTitle("Pick an Alert option");
                                                     String itemValue = (String) listView.getItemAtPosition(position);
                                                     alertDialog4.setItems(options, new DialogInterface.OnClickListener() {
                                                         @Override
                                                         public void onClick(DialogInterface dialog, int which) {
                                                             TextView option = (TextView) view.findViewById(R.id.item_content);
                                                             if (which == 0) {
                                                                 option.setText("No Alerts");
                                                                 EditContactInterface activity = (EditContactInterface) getActivity();
                                                                 try {
                                                                     activity.editContactOption(rowId, "NO");
                                                                 } catch (SQLException e) {
                                                                     e.printStackTrace();
                                                                 }
                                                             } else if (which == 1) {
                                                                 //check if has the email or not
                                                                 EditContactInterface activity = (EditContactInterface) getActivity();
                                                                 boolean hasEmail = true;
                                                                 try {
                                                                     hasEmail = activity.hasEmail(rowId);

                                                                 } catch (SQLException e) {
                                                                     e.printStackTrace();
                                                                 }
                                                                 if (hasEmail) {
                                                                     option.setText("Alert through Email & SMS");
                                                                     try {
                                                                         activity.editContactOption(rowId, "BOTH");
                                                                     } catch (SQLException e) {
                                                                         e.printStackTrace();
                                                                     }
                                                                 } else {
                                                                     //don't have email so I need to alert user to enter the email
                                                                     new AlertDialog.Builder(getActivity()).setTitle("Error").setMessage("There is no email from this contact to send the alert to them.")
                                                                             .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                 public void onClick(DialogInterface dialog, int which) {
                                                                                     //do nothing
                                                                                 }
                                                                             }).show();
                                                                 }
                                                             } else if (which == 2) {
                                                                 //check if has the email or not
                                                                 EditContactInterface activity = (EditContactInterface) getActivity();
                                                                 boolean hasEmail = true;
                                                                 try {
                                                                     hasEmail = activity.hasEmail(rowId);

                                                                 } catch (SQLException e) {
                                                                     e.printStackTrace();
                                                                 }
                                                                 if (hasEmail) {
                                                                     option.setText("Alert through Email");
                                                                     try {
                                                                         activity.editContactOption(rowId, "EMAIL");
                                                                     } catch (SQLException e) {
                                                                         e.printStackTrace();
                                                                     }
                                                                 } else {
                                                                     //don't have email so I need to alert user to enter the email
                                                                     new AlertDialog.Builder(getActivity()).setTitle("Error").setMessage("There is no email from this contact to send the alert to them.")
                                                                             .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                 public void onClick(DialogInterface dialog, int which) {
                                                                                     //do nothing
                                                                                 }
                                                                             }).show();
                                                                 }

                                                             } else if (which == 3) {
                                                                 //check if has the email or not
                                                                 EditContactInterface activity = (EditContactInterface) getActivity();
                                                                 boolean hasEmail = true;
                                                                 option.setText("Alert through SMS");
                                                                 try {
                                                                     activity.editContactOption(rowId, "SMS");
                                                                 } catch (SQLException e) {
                                                                     e.printStackTrace();
                                                                 }
                                                             }//end which ==3

                                                         }//end onClick
                                                     });
                                                     alertDialog4.show();

                                                 }//end position3
                                                 else {
                                                     if (position == 4) {
                                                         AlertDialog.Builder alertDialog5;
                                                         alertDialog5 = new AlertDialog.Builder(getActivity());
                                                         alertDialog5.setTitle("Customize message");
                                                         final EditText messageInput = new EditText(getActivity());
                                                         EditContactInterface activity = (EditContactInterface) getActivity();
                                                         String oldMessage="";
                                                         try {
                                                             oldMessage = activity.getMessage(rowId);
                                                         } catch (SQLException e) {
                                                             e.printStackTrace();
                                                         }
                                                         messageInput.setText(oldMessage);
                                                         alertDialog5.setView(messageInput);
                                                         alertDialog5.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                                             public void onClick(DialogInterface dialog, int which){

                                                                 String newMessage= messageInput.getText().toString();
                                                                 if(isEmpty(messageInput)){
                                                                     new AlertDialog.Builder(getActivity()).setTitle("Error").setMessage("The message content cannot be empty.")
                                                                             .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                 public void onClick(DialogInterface dialog, int which) {
                                                                                     //do nothing
                                                                                 }
                                                                             }).show();
                                                                 }else{
                                                                     EditContactInterface activity = (EditContactInterface) getActivity();
                                                                     try {
                                                                          activity.editContactMessage(rowId,newMessage);
                                                                     } catch (SQLException e) {
                                                                         e.printStackTrace();
                                                                     }
                                                                     //cut the first 20 characters to put in the list view
                                                                     String str=newMessage.substring(0,21);
                                                                     TextView message = (TextView) view.findViewById(R.id.item_content);
                                                                     message.setText(str);
                                                                 }
                                                             }
                                                         });
                                                         alertDialog5.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                                                             public void onClick(DialogInterface dialog, int which){
                                                                dialog.cancel();
                                                             }

                                                         });
                                                         alertDialog5.show();

                                                     }//end position4
                                                 }

                                             }//on item click for list view
                                         });
                 btn_back = (Button) view.findViewById(R.id.btn_back);
                 btn_back.setOnClickListener(new View.OnClickListener() {
                     public void onClick(View view) {
                         dismiss();
                     }
                 });
                 btn_delete = (Button) view.findViewById(R.id.btn_delete);
                 btn_delete.setOnClickListener(new View.OnClickListener() {
                     public void onClick(View view) {
                         //confirm before delete
                         AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(getActivity());
                         // Setting Dialog Title
                         alertDialog2.setTitle("Confirmation");
                         // Setting Dialog Message
                         alertDialog2.setMessage("Are you sure you want delete this contact?");
                         // Setting Positive "Yes" Btn
                         alertDialog2.setPositiveButton("YES",
                                 new DialogInterface.OnClickListener() {
                                     public void onClick(DialogInterface dialog, int which) {
                                         //yes to delete the contact


                                         EditContactInterface activity = (EditContactInterface) getActivity();
                                         try {
                                             activity.deleteThisContact(rowId);
                                             dismiss();
                                         } catch (SQLException e) {
                                             e.printStackTrace();
                                         }


                                     }
                                 });
                         // Setting Negative "NO" Btn
                         alertDialog2.setNegativeButton("NO",
                                 new DialogInterface.OnClickListener() {
                                     public void onClick(DialogInterface dialog, int which) {
                                         dialog.cancel();
                                     }
                                 });
                         // Showing Alert Dialog
                         alertDialog2.show();
                         //write delete function

                     }
                 });
                 getDialog().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                 getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                 return view;
             }
         }
