package com.example.buhirecord;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;
import java.util.Date;

public class BuhiRecordDatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "BuhiRecord";
    public static int VERSION = 1;

    public BuhiRecordDatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATABASE = "create table BuhiRecord ("
                + "id integer primary key autoincrement, "
                + "uuid text, "
                + "timestamp integer, "
                + "date text, "
                + "category text, "
                + "amount double)";
        db.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addRecord(BillItem billItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = billItem.getValues();
        db.insert(DB_NAME, null, values);
        db.close();
    }

    public void removeRecord(BillItem billItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_NAME, "uuid = ?", new String[]{String.valueOf(billItem.getUuid())});
        db.close();
    }

    public void updateRecord(BillItem billItem) {
        removeRecord(billItem);
        addRecord(billItem);
    }

    @SuppressLint("Range")
    public List<BillItem> getRecord(String queryDate) {
        List<BillItem> billItemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_NAME, null, "date = ?", new String[]{queryDate}, null, null, null);
        while (cursor.moveToNext()) {
            UUID uuid = UUID.fromString(cursor.getString(cursor.getColumnIndex("uuid")));
            long timestamp = cursor.getInt(cursor.getColumnIndex("timestamp"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String category = cursor.getString(cursor.getColumnIndex("category"));
            double amount = cursor.getDouble(cursor.getColumnIndex("amount"));

            BillItem billItem = new BillItem(uuid, timestamp, date, new BillCategory(category), amount);
            billItemList.add(billItem);
        }
        cursor.close();
        db.close();
        return billItemList;
    }

    @SuppressLint("SimpleDateFormat")
    private Date String2Date(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
