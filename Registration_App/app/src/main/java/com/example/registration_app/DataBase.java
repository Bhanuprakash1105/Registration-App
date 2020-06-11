package com.example.registration_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;

public class DataBase extends SQLiteOpenHelper {

    public static final String DBname = "RegistrationData";
    private static final String TableStu = "StudentTable";
    private static final String TableFac = "FacultyTable";

    DataBase(Context context) {
        super(context, DBname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TableStu + " (id INTEGER PRIMARY KEY, name VARCHAR, email VARCHAR, branch VARCHAR, fee VARCHAR, courses VARCHAR, semester INTEGER, dob VARCHAR, rollNum VARCHAR, password VARCHAR, mobile VARCHAR, gender VARCHAR)");
        db.execSQL("CREATE TABLE " + TableFac + " (id INTEGER PRIMARY KEY, email VARCHAR, password VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableStu);
        db.execSQL("DROP TABLE IF EXISTS " + TableFac);
        onCreate(db);
    }

    public void update(String id, ContentValues contentValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TableStu, contentValues, "ID = ?",new String[] { id });
    }

    public long insertStudent(ContentValues contentValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TableStu,null ,contentValues);
    }

    public long insertFaculty(ContentValues contentValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TableFac,null ,contentValues);
    }

    public void deleteStu(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableStu, "email = '" + email + "'", null);
    }

    public ArrayList <Object> checkExistsStu(String column_name, String value) {
        SQLiteDatabase db = this.getReadableDatabase();
        String rawQuery =  "SELECT * FROM " + TableStu + " WHERE " + column_name + " = '" + value + "' LIMIT 1";
        Cursor cursor = db.rawQuery(rawQuery, null);
        ArrayList<Object> ret = new ArrayList<>(asList(-1, ""));
        if (cursor == null)
            return ret;
        else {
            long id = -1;
            String temp = "";
            int idColumnIndex = cursor.getColumnIndex("id");
            int passwordColumnIndex = cursor.getColumnIndex("password");
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                id = cursor.getInt(idColumnIndex);
                temp = cursor.getString(passwordColumnIndex);
                cursor.moveToNext();
            }
            ret.set(0, id);
            ret.set(1, temp);
            cursor.close();
            return ret;
        }
    }

    public ArrayList <Object> checkExistsFac(String column_name, String value) {
        SQLiteDatabase db = this.getReadableDatabase();
        String rawQuery =  "SELECT * FROM " + TableFac + " WHERE " + column_name + " = '" + value + "' LIMIT 1";
        Cursor cursor = db.rawQuery(rawQuery, null);
        ArrayList<Object> ret = new ArrayList<>(asList(-1, ""));
        if (cursor == null)
            return ret;
        else {
            long id = -1;
            String temp = "";
            int idColumnIndex = cursor.getColumnIndex("id");
            int passwordColumnIndex = cursor.getColumnIndex("password");
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                id = cursor.getInt(idColumnIndex);
                temp = cursor.getString(passwordColumnIndex);
                cursor.moveToNext();
            }
            ret.set(0, id);
            ret.set(1, temp);
            cursor.close();
            return ret;
        }
    }

    public String checkFee(long idpara) {
        SQLiteDatabase db = this.getReadableDatabase();
        String rawQuery =  "SELECT * FROM " + TableStu + " WHERE id " + " = " + idpara;
        Cursor cursor = db.rawQuery(rawQuery, null);
        int feeColumnIndex = cursor.getColumnIndex("fee");
        String feeStatus = "";
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            feeStatus = cursor.getString(feeColumnIndex);
            cursor.moveToNext();
        }
        cursor.close();
        if (feeStatus == null) { feeStatus = ""; }
        return feeStatus;
    }

    public String checkCourseReg(long idpara) {
        SQLiteDatabase db = this.getReadableDatabase();
        String rawQuery =  "SELECT * FROM " + TableStu + " WHERE id " + " = " + idpara;
        Cursor cursor = db.rawQuery(rawQuery, null);
        int coursesColumnIndex = cursor.getColumnIndex("courses");
        String courses = "";
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            courses = cursor.getString(coursesColumnIndex);
            cursor.moveToNext();
        }
        cursor.close();
        if (courses == null) { courses = ""; }
        return courses;
    }

    public Map<String, String> givePersonalInfo(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String rawQuery =  "SELECT * FROM " + TableStu + " WHERE id " + " = " + id;
        Cursor cursor = db.rawQuery(rawQuery, null);
        cursor.moveToFirst();

        String name = cursor.getString(cursor.getColumnIndex("name"));
        String gender = cursor.getString(cursor.getColumnIndex("gender"));
        String dob = cursor.getString(cursor.getColumnIndex("dob"));
        String mobileNum = cursor.getString(cursor.getColumnIndex("mobile"));
        String email = cursor.getString(cursor.getColumnIndex("email"));

        Map<String, String> values = new HashMap<String, String>();
        values.put("name", name);
        values.put("gender", gender);
        values.put("dob", dob);
        values.put("mobile", mobileNum);
        values.put("email", email);

        cursor.close();
        return values;
    }

    public Map<String, String> giveEveryThing(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String rawQuery =  "SELECT * FROM " + TableStu + " WHERE id " + " = " + id;
        Cursor cursor = db.rawQuery(rawQuery, null);
        cursor.moveToFirst();

        String name = cursor.getString(cursor.getColumnIndex("name"));
        String gender = cursor.getString(cursor.getColumnIndex("gender"));
        String dob = cursor.getString(cursor.getColumnIndex("dob"));
        String mobileNum = cursor.getString(cursor.getColumnIndex("mobile"));
        String email = cursor.getString(cursor.getColumnIndex("email"));
        String branch = cursor.getString(cursor.getColumnIndex("branch"));
        String courses = cursor.getString(cursor.getColumnIndex("courses"));
        String semester = Integer.toString(cursor.getInt(cursor.getColumnIndex("semester")));
        String fee = cursor.getString(cursor.getColumnIndex("fee"));

        Map<String, String> values = new HashMap<String, String>();
        values.put("name", name);
        values.put("gender", gender);
        values.put("dob", dob);
        values.put("mobile", mobileNum);
        values.put("email", email);
        values.put("branch", branch);
        values.put("courses", courses);
        values.put("semester", semester);
        if(fee == null) {
            values.put("fee", "UNPAID");
        } else {
            values.put("fee", fee);
        }

        cursor.close();
        return values;
    }

    public ArrayList < Map<String, String> > giveAllAsked(String branch, Integer semester) {
        SQLiteDatabase db = this.getReadableDatabase();
        String rawQuery =  "SELECT * FROM " + TableStu + " WHERE branch " + " = '" + branch + "'" +" AND semester = " + semester;
        ArrayList < Map <String, String> > ret = new ArrayList < Map <String, String> >();
        Cursor cursor = db.rawQuery(rawQuery, null);
        cursor.moveToFirst();
        int idIndex = cursor.getColumnIndex("id");
        while(!cursor.isAfterLast()) {
            long id = cursor.getInt(idIndex);
            ret.add(giveEveryThing(id));
            cursor.moveToNext();
        }
        cursor.close();
        return ret;
    }

    public boolean eligibleForFac(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String rawQuery =  "SELECT * FROM " + TableStu + " WHERE email = '" + email + "'";
        Cursor cursor = db.rawQuery(rawQuery, null);
        cursor.moveToFirst();
        boolean ret = cursor.isAfterLast();
        cursor.close();
        return ret;
    }
}
