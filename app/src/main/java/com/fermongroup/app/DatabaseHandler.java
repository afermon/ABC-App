package com.fermongroup.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 01/01/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FGAppDB";
    private static final String TABLE_LOGS = "logs";
    private static final String KEY_ID = "id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_INFO = "information";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGS_TABLE = "CREATE TABLE " + TABLE_LOGS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TYPE + " INTEGER,"
                + KEY_INFO + " TEXT" + ")";
        db.execSQL(CREATE_LOGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGS);
        onCreate(db);
    }

    public void addLog(LogReg logReg) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, logReg.getType());
        values.put(KEY_INFO, logReg.getInformation());
        db.insert(TABLE_LOGS, null, values);
        db.close();
    }

    public LogReg getLog(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LOGS, new String[] { KEY_ID,
                        KEY_TYPE, KEY_INFO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        LogReg logReg = new LogReg(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)), cursor.getString(2));
        return logReg;
    }

    public List<LogReg> getAllLogs() {
        List<LogReg> logRegList = new ArrayList<LogReg>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                LogReg logReg = new LogReg();
                logReg.setID(Integer.parseInt(cursor.getString(0)));
                logReg.setType(Integer.parseInt(cursor.getString(1)));
                logReg.setInformation(cursor.getString(2));
                logRegList.add(logReg);
            } while (cursor.moveToNext());
        }
        return logRegList;
    }

    public int getLogsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    public int updateLog(LogReg logReg) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, logReg.getType());
        values.put(KEY_INFO, logReg.getInformation());
        return db.update(TABLE_LOGS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(logReg.getID()) });
    }

    public void deleteLog(LogReg logReg) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOGS, KEY_ID + " = ?",
                new String[] { String.valueOf(logReg.getID()) });
        db.close();
    }

    public void deleteAllLogs(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOGS,null,null);
        db.close();

    }

}