package com.example.sqlite.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqlite.model.Employee;
import com.example.sqlite.model.Item;

import java.util.ArrayList;
import java.util.List;

public class SqLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME ="NhanVien.db";
    private static int DATABASE_VERSION = 1;

    public SqLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE employees("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name TEXT,"+
                "phone TEXT,"+
                "dob INTEGER,"+
                "gender TEXT," +
                "skills TEXT)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    //get all order by date dêcnding
    public List<Employee> getAll() {
        List<Employee> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String order = "name DESC";
        Cursor rs = sqLiteDatabase.query("employees",
                null, null, null,
                null, null, order);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String name = rs.getString(1);
            String phone = rs.getString(2);
            int dob = rs.getInt(3);
            String gender = rs.getString(4);
            String skills = rs.getString(5);
            list.add(new Employee(id,name,phone,dob,gender,skills));
        }
        return list;
    }

    //add
    public long addItem(Employee e){
        ContentValues values = new ContentValues();
        values.put("name", e.getName());
        values.put("phone", e.getPhone());
        values.put("dob", e.getDob());
        values.put("gender", e.getGender());
        values.put("skills", e.getSkill());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("employees",null, values);
    }

    //lay item theo date



    public void deleteAllItem(int id){
        String sql = "DELETE  FROM employees ";
        SQLiteDatabase st = getWritableDatabase();
        st.execSQL(sql);
    }

    public int updateItem(Employee e) {
        ContentValues values = new ContentValues();
        values.put("name", e.getName());
        values.put("phone", e.getPhone());
        values.put("dob", e.getDob());
        values.put("gender", e.getGender());
        values.put("skills", e.getSkill());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(e.getId())};
        return sqLiteDatabase.update("employees",
                values, whereClause, whereArgs);
    }

    public int deleteItem(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("employees",
                whereClause, whereArgs);
    }

    public List<Item> searchByTitle(String key) {
        List<Item> list = new ArrayList<>();
        String whereClause = "title like ?";
        String[] whereArgs = {"%"+key +"%"};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rs = sqLiteDatabase.query("items",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Item(id,title,price,date,category));
        }
        return list;
    }

    public List<Item> searchByCategory(String category) {
        List<Item> list = new ArrayList<>();
        String whereClause = "category like ?";
        String[] whereArgs = {category};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rs = sqLiteDatabase.query("items",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String title = rs.getString(1);
            String c = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Item(id,title,price,date,c));
        }
        return list;
    }

    public List<Item> searchByDateFromTo(String from, String to) {
        List<Item> list = new ArrayList<>();
        String whereClause = "date BETWEEN ? AND ?";
        String[] whereArgs = {from.trim(),to.trim()};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rs = sqLiteDatabase.query("items",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Item(id,title,price,date,category));
        }
        return list;
    }

    public List<Employee> searchBtSkills(String skills) {
        List<Employee> list = new ArrayList<>();
        String[] skillNames = skills.split(",");
        StringBuilder whereClauseBuilder = new StringBuilder();
        String[] whereArgs = new String[skillNames.length];
        for (int i = 0; i < skillNames.length; i++) {
            if (i > 0) {
                whereClauseBuilder.append(" OR ");
            }
            whereClauseBuilder.append("skills LIKE ?");
            whereArgs[i] = "%" + skillNames[i].trim() + "%";
        }
        String whereClause = whereClauseBuilder.toString();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rs = sqLiteDatabase.query("employees",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String name = rs.getString(1);
            String phone = rs.getString(2);
            int dob = rs.getInt(3);
            String gender = rs.getString(4);
            String skill = rs.getString(5);

            list.add(new Employee(id,name,phone,dob,gender,skill));
        }
        return list;
    }



}
