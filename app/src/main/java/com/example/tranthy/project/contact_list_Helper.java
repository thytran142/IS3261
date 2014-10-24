package com.example.tranthy.project;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
/**
 * This class is created SQL string with table clarified
 */
public class contact_list_Helper extends SQLiteOpenHelper {
    public static final String TABLE_NAME="contacts";
    public static final String COLUMN_NAME_ID="id";
    public static final String COLUMN_NAME_CONTACTNAME="name";
    public static final String COLUMN_NAME_CONTACTNUMBER="phone";
    public static final String COLUMN_NAME_CONTACTEMAIL="email";
    public static final String COLUMN_NAME_OPTION="option";
    public static final String COLUMN_NAME_MESSAGE="message";
    private static final String SQL_CREATE="CREATE TABLE " + TABLE_NAME +"("+
            COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_NAME_CONTACTNAME+ " TEXT NOT NULL, "+
            COLUMN_NAME_CONTACTNUMBER+ " TEXT NOT NULL, "+
            COLUMN_NAME_CONTACTEMAIL+ " TEXT, " +
            COLUMN_NAME_OPTION + " TEXT DEFAULT 'NO ALERT', " +
            COLUMN_NAME_MESSAGE + " TEXT DEFAULT 'Hi, I am currently safe.Message sent from #location');";
    // REMARK* "NO" = NO ALERT, "BOTH" email and SMS, "EMAIL" email only, "SMS" sms only

    private static final String SQL_DELETE="DROP TABLE IF EXISTS "+TABLE_NAME;
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="contactDB";
    public contact_list_Helper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }

}
