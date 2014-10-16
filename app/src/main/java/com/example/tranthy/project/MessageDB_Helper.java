package com.example.tranthy.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shake on 16/10/2014.
 */
public class MessageDB_Helper extends SQLiteOpenHelper {

    public static final String TABLE_NAME ="message_history";
    public static final String COLUMN_NAME_ID = "_id";
    public static final String COLUMN_NAME_RECEIVER = "name";
    public static final String COLUMN_NAME_MESSAGE = "contact";
    public static final String COLUMN_NAME_TIMESTAMP = "time";
    private static final String SQL_CREATE =
            "CREATE TABLE "+ TABLE_NAME + "(" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME_RECEIVER + " TEXT NOT NULL," +
                    COLUMN_NAME_MESSAGE + " TEXT NOT NULL);";

    private static final String SQL_DELETE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MessageDB";

    public MessageDB_Helper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
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
