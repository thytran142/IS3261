package com.example.tranthy.project;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import java.sql.SQLException;

/**
 * Contact_list class to maintain the database through operations such as data insertion, delete and read
 */
public class contact_list {
    final Context context;
    contact_list_Helper DBHelper;
    SQLiteDatabase db;

    public contact_list(Context ctx){
        this.context=ctx;
        DBHelper=new contact_list_Helper(this.context);
    }
    //open the database
    public contact_list open() throws SQLException{
        db=DBHelper.getWritableDatabase();
        return this;
    }
    //close the database
    public void close(){
        DBHelper.close();
    }

    //Insert a contact into the database
    public long insertContacts(String name, String number,String email){
        ContentValues initialValues=new ContentValues();
        initialValues.put(contact_list_Helper.COLUMN_NAME_CONTACTNAME,name);
        initialValues.put(contact_list_Helper.COLUMN_NAME_CONTACTNUMBER,number);
        initialValues.put(contact_list_Helper.COLUMN_NAME_CONTACTEMAIL,email);
        return db.insert(contact_list_Helper.TABLE_NAME,null,initialValues);
    }
    //retrieves all the contacts
    public Cursor getAllContacts(){
        return db.query(contact_list_Helper.TABLE_NAME,new String[]{
                contact_list_Helper.COLUMN_NAME_ID,
                contact_list_Helper.COLUMN_NAME_CONTACTNAME,
                contact_list_Helper.COLUMN_NAME_CONTACTNUMBER,
                contact_list_Helper.COLUMN_NAME_CONTACTEMAIL,
                contact_list_Helper.COLUMN_NAME_OPTION,
                contact_list_Helper.COLUMN_NAME_MESSAGE},null,null,null,null,null);
        }
    //retrieve a particular contact
    public Cursor getContact(long rowId) throws SQLException {
        Cursor mCursor = db.query(true, contact_list_Helper.TABLE_NAME, new String[]{
                contact_list_Helper.COLUMN_NAME_ID,
                contact_list_Helper.COLUMN_NAME_CONTACTNAME,
                contact_list_Helper.COLUMN_NAME_CONTACTNUMBER,
                contact_list_Helper.COLUMN_NAME_CONTACTEMAIL,
                contact_list_Helper.COLUMN_NAME_OPTION,
                contact_list_Helper.COLUMN_NAME_MESSAGE
        }, contact_list_Helper.COLUMN_NAME_ID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //delete a particular contact
    public boolean deleteContact(long rowId){
        return db.delete(contact_list_Helper.TABLE_NAME,contact_list_Helper.COLUMN_NAME_ID+"="+rowId,null)>0;
    }
    //update a contact
    public boolean updateContact(long rowId,String name,String number,String email,String option, String message){
        ContentValues initialValues= new ContentValues();
        initialValues.put(contact_list_Helper.COLUMN_NAME_CONTACTNAME,name);
        initialValues.put(contact_list_Helper.COLUMN_NAME_CONTACTNUMBER,number);
        initialValues.put(contact_list_Helper.COLUMN_NAME_CONTACTEMAIL,email);
        initialValues.put(contact_list_Helper.COLUMN_NAME_OPTION,option);
        initialValues.put(contact_list_Helper.COLUMN_NAME_MESSAGE,message);
        return db.update(contact_list_Helper.TABLE_NAME,initialValues,contact_list_Helper.COLUMN_NAME_ID+"="+rowId,null)!=0;
    }
    //update a contact name
    public boolean updateContactName(long rowId,String newName){
        ContentValues initialValues= new ContentValues();
        initialValues.put(contact_list_Helper.COLUMN_NAME_CONTACTNAME,newName);
        return db.update(contact_list_Helper.TABLE_NAME,initialValues,contact_list_Helper.COLUMN_NAME_ID+"="+rowId,null)!=0;
    }
    public boolean updateContactNumber(long rowId,String newNumber){
        ContentValues initialValues= new ContentValues();
        initialValues.put(contact_list_Helper.COLUMN_NAME_CONTACTNUMBER,newNumber);
        return db.update(contact_list_Helper.TABLE_NAME,initialValues,contact_list_Helper.COLUMN_NAME_ID+"="+rowId,null)!=0;
    }
    public boolean updateContactEmail(long rowId,String newEmail){
        ContentValues initialValues= new ContentValues();
        initialValues.put(contact_list_Helper.COLUMN_NAME_CONTACTEMAIL,newEmail);
        return db.update(contact_list_Helper.TABLE_NAME,initialValues,contact_list_Helper.COLUMN_NAME_ID+"="+rowId,null)!=0;
    }
    public boolean updateOption(long rowId,String option){
        ContentValues initialValues= new ContentValues();
        initialValues.put(contact_list_Helper.COLUMN_NAME_OPTION,option);
        return db.update(contact_list_Helper.TABLE_NAME,initialValues,contact_list_Helper.COLUMN_NAME_ID+"="+rowId,null)!=0;
    }
    public boolean updateMessage(long rowId,String message){
        ContentValues initialValues= new ContentValues();
        initialValues.put(contact_list_Helper.COLUMN_NAME_MESSAGE,message);
        return db.update(contact_list_Helper.TABLE_NAME,initialValues,contact_list_Helper.COLUMN_NAME_ID+"="+rowId,null)!=0;
    }
}
