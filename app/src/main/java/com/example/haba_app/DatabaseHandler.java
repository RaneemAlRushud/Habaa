package com.example.haba_app;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int database_version = 1;
    private static final String database_name = "db_haba";

    private static final String users_table = "tbl_users";
    private static final String request_table = "tbl_requests";
    private static final String column_id = "id";
    private static final String column_name = "name";
    private static final String column_email = "email";
    private static final String column_mobile = "mobile";
    private static final String column_address = "address";
    private static final String column_coordinates = "coordinates";
    private static final String column_type = "user_type";
    private static final String column_password = "password";

    private static final String column_request_type = "request_type";
    private static final String column_request_description = "request_description";
    private static final String column_request_status = "request_status";
    private static final String column_request_location = "request_location";
    private static final String column_request_posted_by = "posted_by";
    private static final String column_accepted_by = "accepted_by";
    private static final String column_notification_status = "notification_status";

    int rowCount;
    private SQLiteDatabase database;
    public DatabaseHandler(Context context){
        super(context,database_name,null,database_version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        database = db;
        String query_users = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",users_table,column_id,column_name,column_email,column_mobile,column_address,column_coordinates,column_type,column_password);
        String query_requests = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",request_table,column_id,column_request_type,column_request_description,column_request_location,column_request_status,column_request_posted_by,column_accepted_by,column_notification_status);
        db.execSQL(query_users);

        db.execSQL(query_requests);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+users_table);
        db.execSQL("DROP TABLE IF EXISTS "+request_table);
        onCreate(db);
    }

    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(column_name, user.getName());
        values.put(column_email, user.getEmail());
        values.put(column_mobile, user.getMobile());
        values.put(column_address, user.getAddress());
        values.put(column_type, user.getType());
        values.put(column_coordinates, user.getCoordinates());
        values.put(column_password, user.getPassword());
        int cursor= (int) db.insert(users_table, null, values);
        db.close();

        if (cursor > 0)
            return true;
        else
            return false;
    }

    public boolean addRequest(Requests requests) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(column_request_type, requests.getRequest_type());
        values.put(column_request_description, requests.getRequest_description());
        values.put(column_request_location, requests.getRequest_location());
        values.put(column_request_status, requests.getRequest_status());
        values.put(column_request_posted_by, requests.getPosted_by());
        values.put(column_notification_status, requests.getNotification_status());
        values.put(column_accepted_by, requests.getAccepted_by());
        int cursor= (int) db.insert(request_table, null, values);
        db.close();

        if (cursor > 0)
            return true;
        else
            return false;
    }

    public boolean checkUser(String email) {
        String[] columns = {column_id};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = column_email + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(users_table, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUser(String email, String password) {
        String[] columns = {column_type};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = column_email + " = ?" + " AND " + column_password + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(users_table, columns, selection, selectionArgs, null, null, null);
        int  count = cursor.getCount();
        cursor.close();
        db.close();
        if (count >0 ) {
            return true;
        }
        return false;

    }

    public ArrayList<User> get_user_information(String email) {

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor curosr = db.rawQuery("SELECT * FROM tbl_users WHERE email = '"+email+"'", null);



        ArrayList<User> users = new ArrayList<>();

        if (curosr.moveToFirst()) {
            do {

                users.add(new User(curosr.getString(0), curosr.getString(1), curosr.getString(2), curosr.getString(3),curosr.getString(4),curosr.getString(5),curosr.getString(6),curosr.getString(7)));
            } while (curosr.moveToNext());

        }
        curosr.close();
        return users;
    }

    public ArrayList<Requests> get_requests_by_user_id(String user_id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_requests WHERE posted_by = '"+user_id+"'", null);
        
        ArrayList<Requests> requests = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {

                requests.add(new Requests(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7)));
            } while (cursor.moveToNext());

        }
        cursor.close();
        return requests;
    }

    public ArrayList<Requests> get_requests(String status) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_requests WHERE request_status = '"+status+"'", null);

      //  Cursor cursor = db.rawQuery("SELECT * FROM " + request_table, null);
        ArrayList<Requests> requests = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {

                requests.add(new Requests(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7)));
            } while (cursor.moveToNext());

        }
        cursor.close();
        return requests;
    }

    public boolean update_Request(String id,String value,String accepted_by,String notif_status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(column_request_status, value);
        values.put(column_accepted_by, accepted_by);
        values.put(column_notification_status, notif_status);
        int count=db.update(request_table, values,  "id = ?", new String[]{id});


        db.close();
        if (count >0 ) {
            return true;
        }
        return false;
    }
    public void update_notification_status(String id,String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(column_notification_status, value);
        db.update(request_table, values,  "id = ?", new String[]{id});
        db.close();
    }

}

