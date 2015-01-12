package com.jeevangadgets.jeevanalarm;

import com.jeevangadgets.jeevanalarm.AlarmDBContract.AlarmList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlarmDBHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "AlarmDB.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String SQL_CREATE_ALARMLIST_SCRIPT = 
			"CREATE TABLE " + AlarmList.TABLE_NAME + "(" + 
			AlarmList._ID + " INTEGER PRIMARY KEY, " + 
			AlarmList.COLUMN_NAME_ALARM_NAME + " TEXT, " + 
			AlarmList.COLUMN_NAME_ALARM_ON + " DATETIME, " + 
			AlarmList.COLUMN_NAME_REPEAT_COUNT + " INTEGER, " + 
			AlarmList.COLUMN_NAME_REPEAT_FREQUENCY + " TEXT" + 
			")";
	
	private static final String SQL_DELETE_ALARMLIST_SCRIPT = 
			"DROP TABLE IF EXISTS " + AlarmList.TABLE_NAME;
	
	public AlarmDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(SQL_CREATE_ALARMLIST_SCRIPT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ALARMLIST_SCRIPT);
        onCreate(db);		
	}

	@Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
