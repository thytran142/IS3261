package com.example.tranthy.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.sql.Timestamp;

/**
 * Created by Shake on 16/10/2014.
 */
public class MessageDB {
    final Context context;
    MessageDB_Helper DBHelper;
    SQLiteDatabase db;

    public MessageDB(Context ctx){
        this.context = ctx;
        DBHelper = new MessageDB_Helper(this.context);

    }

    public MessageDB open() throws SQLiteException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        DBHelper.close();
    }

    public long insertMsgHistory(String receiver,String message, String time){
        ContentValues initialValues = new ContentValues();
        initialValues.put(MessageDB_Helper.COLUMN_NAME_RECEIVER,receiver);
        initialValues.put(MessageDB_Helper.COLUMN_NAME_MESSAGE,message);
        initialValues.put(MessageDB_Helper.COLUMN_NAME_TIMESTAMP,time);
        return db.insert(MessageDB_Helper.TABLE_NAME,null,initialValues);
    }

    public Cursor getAllMsgHistory(){
        return db.query(MessageDB_Helper.TABLE_NAME,new String[] {
                        MessageDB_Helper.COLUMN_NAME_ID,
                        MessageDB_Helper.COLUMN_NAME_RECEIVER,
                        MessageDB_Helper.COLUMN_NAME_MESSAGE,
                        MessageDB_Helper.COLUMN_NAME_TIMESTAMP},
                null,null,null,null,null);
    }

    public Cursor getMsgHistory(long rowId) throws SQLiteException {
        Cursor mCursor =
                db.query(true, MessageDB_Helper.TABLE_NAME,
                        new String[]{
                                MessageDB_Helper.COLUMN_NAME_ID,
                                MessageDB_Helper.COLUMN_NAME_RECEIVER,
                                MessageDB_Helper.COLUMN_NAME_MESSAGE,
                                MessageDB_Helper.COLUMN_NAME_TIMESTAMP},
                        MessageDB_Helper.COLUMN_NAME_ID + "=" + rowId,
                        null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void deleteMsgHistory(){
        db.delete(MessageDB_Helper.TABLE_NAME,null,null);
    }

    public boolean updateMsgHistory(long rowId,String receiver,String message, String time){
        ContentValues initialValues = new ContentValues();
        initialValues.put(MessageDB_Helper.COLUMN_NAME_RECEIVER,receiver);
        initialValues.put(MessageDB_Helper.COLUMN_NAME_MESSAGE,message);
        initialValues.put(MessageDB_Helper.COLUMN_NAME_TIMESTAMP,time);
        return db.update(MessageDB_Helper.TABLE_NAME,initialValues,
                MessageDB_Helper.COLUMN_NAME_ID + "=" + rowId,null) > 0;

    }




}
