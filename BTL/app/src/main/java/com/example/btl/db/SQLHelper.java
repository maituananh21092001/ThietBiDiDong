package com.example.btl.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.btl.model.User;

public class SQLHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mydatabase.db";
    public static final int DATABASE_VERSION = 1;



    public SQLHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableUser = "CREATE TABLE User (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, email text, yourName text )";
        db.execSQL(createTableUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }
    public boolean addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("email", user.getEmail());
        values.put("yourName", user.getYourName());

        long result = db.insert("User", null, values);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"id"};
        String selection = "username=? and password=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query("User", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public User getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        String[] projection = {
                "id",
                "username",
                "password",
                "email",
                "yourName"
        };

        String selection = "username = ?";
        String[] selectionArgs = { username };

        Cursor cursor = db.query(
                "User",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String yourName = cursor.getString(cursor.getColumnIndexOrThrow("yourName"));
            user = new User(id, username, password, email, yourName);
        }

        cursor.close();
        db.close();
        return user;
    }
}
