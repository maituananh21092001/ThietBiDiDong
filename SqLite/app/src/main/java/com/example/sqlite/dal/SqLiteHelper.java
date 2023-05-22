package com.example.sqlite.dal;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqlite.model.Income;
import com.example.sqlite.model.Item;
import com.example.sqlite.model.User;

import java.util.ArrayList;
import java.util.List;

public class SqLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME ="AppChiTieu5.db";
    private static int DATABASE_VERSION = 1;


    public SqLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE items (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "category TEXT," +
                "price TEXT," +
                "date TEXT," +
                "user_id INTEGER," +  // Thêm trường user_id với kiểu dữ liệu INTEGER
                "FOREIGN KEY(user_id) REFERENCES User(id)" +  // Thêm khóa ngoại user_id tham chiếu đến trường id của bảng users
                ")";

        db.execSQL(sql);
        String createTableUser = "CREATE TABLE User (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, email text, yourName text,phoneNumber text )";
        db.execSQL(createTableUser);

        String createTableIncome = "CREATE TABLE income (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "salary INTEGER NOT NULL," +
                "month INTEGER NOT NULL," +
                "user_id INTEGER," +
                "type_income TEXT NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES User(id)" +
                ");";
        db.execSQL(createTableIncome);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS items");
        db.execSQL("DROP TABLE IF EXISTS income");
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    //get all order by date dêcnding
    public List<Item> getAll(int userId) {
        List<Item> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String order = "date DESC";
        String whereClause = "user_id=?";
        String[] whereArgs = {String.valueOf(userId)};
        Cursor rs = sqLiteDatabase.query("items",
                null, whereClause, whereArgs,
                null, null, order);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            int user_id = rs.getInt(5);
            list.add(new Item(id,title,price,date,category,this.getUserById(user_id)));
        }
        return list;
    }
    //add
    public long addItem(Item i){
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("category", i.getCategory());
        values.put("price", i.getPrice());
        values.put("date", i.getDate());
        values.put("user_id",i.getUser().getId());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("items",null, values);
    }

    //lay item theo date
    public List<Item> getByDate(String date, int user_id1) {
        List<Item> list = new ArrayList<>();
        String whereClause = "date like ? AND user_id = ?";
        String[] whereArgs = {date,Integer.toString(user_id1)};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rs = sqLiteDatabase.query("items",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date1 = rs.getString(4);
            int user_id = rs.getInt(5);
            list.add(new Item(id,title,price,date1,category,this.getUserById(user_id)));
        }
        return list;
    }


    public void deleteAllItem(int id){
        String sql = "DELETE  FROM items  ";
        SQLiteDatabase st = getWritableDatabase();
        st.execSQL(sql);
    }

    public int updateItem(Item i) {
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("category", i.getCategory());
        values.put("price", i.getPrice());
        values.put("date", i.getDate());
        values.put("user_id",i.getUser().getId());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id = ? ";
        String[] whereArgs = {Integer.toString(i.getId())};
        return sqLiteDatabase.update("items",
                values, whereClause, whereArgs);
    }

    public int deleteItem(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("items",
                whereClause, whereArgs);
    }

    public List<Item> searchByTitle(String key, int user_id) {
        List<Item> list = new ArrayList<>();
        String whereClause = "title like ? AND user_id = ?";
        String[] whereArgs = {"%"+key +"%", Integer.toString(user_id)};
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
            list.add(new Item(id,title,price,date,category,this.getUserById(user_id)));
        }
        return list;
    }

    public List<Item> searchByCategory(String category,int user_id) {
        List<Item> list = new ArrayList<>();
        String whereClause = "category like ? AND user_id = ?";
        String[] whereArgs = {category,Integer.toString(user_id)};
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
            list.add(new Item(id,title,price,date,category,this.getUserById(user_id)));
        }
        return list;
    }

    public List<Item> searchByDateFromTo(String from, String to, int user_id) {
        List<Item> list = new ArrayList<>();
        String whereClause = "date BETWEEN ? AND ? AND user_id = ?";
        String[] whereArgs = {from.trim(),to.trim(),Integer.toString(user_id)};
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
            list.add(new Item(id,title,price,date,category,this.getUserById(user_id)));

        }
        return list;
    }


    // ____ User _____
    public boolean addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("email", user.getEmail());
        values.put("yourName", user.getYourName());
        values.put("phoneNumber", user.getPhoneNumber());

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
                "yourName",
                "phoneNumber"
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
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("phoneNumber"));
            user = new User(id, username, password, email, yourName,phoneNumber);
        }

        cursor.close();
        db.close();
        return user;
    }

    public User getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id", "username", "password", "email", "yourName","phoneNumber"};
        String selection = "id=?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query("User", columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            User user = new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            );
            cursor.close();
            return user;
        }
        return null;
    }
    public User getUserByPhone(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id", "username", "password", "email", "yourName","phoneNumber"};
        String selection = "phoneNumber = ?";
        String[] selectionArgs = {phone};
        Cursor cursor = db.query("User", columns, selection, selectionArgs, null, null, null);
        if (cursor != null&& cursor.moveToFirst()) {
            cursor.moveToFirst();
            User user = new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            );
            cursor.close();
            return user;
        }
        return null;
    }




    public int updateUser(User user){
        ContentValues values = new ContentValues();
        values.put("email",user.getEmail());
        values.put("yourName",user.getYourName());
        values.put("phoneNumber",user.getPhoneNumber());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id = ? ";
        String[] whereArgs = {Integer.toString(user.getId())};
        return sqLiteDatabase.update("User",
                values, whereClause, whereArgs);
    }

    public int updatePassword(User user, String newPass){
        ContentValues values = new ContentValues();

        values.put("password",newPass);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id = ? ";
        String[] whereArgs = {Integer.toString(user.getId())};
        return sqLiteDatabase.update("User",
                values, whereClause, whereArgs);
    }

    public int updateForgotPassword(String phone,String newPass){
        ContentValues values = new ContentValues();

        values.put("password",newPass);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "phoneNumber = ? ";
        String[] whereArgs = {phone};
        return sqLiteDatabase.update("User",
                values, whereClause, whereArgs);
    }




// ------ Incomes ------//

    public void addIncome(Income i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("salary", i.getSalary());
        values.put("month", i.getMonth());
        values.put("user_id", i.getUser().getId());
        values.put("type_income", i.getTypeIncome());
        db.insert("income", null, values);
        db.close();
    }

    public List<Income> getAllIncome(int userId) {
        List<Income> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String whereClause = "user_id=?";
        String[] whereArgs = {String.valueOf(userId)};
        Cursor rs = sqLiteDatabase.query("income",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            int salary = rs.getInt(1);
            int month = rs.getInt(2);
            int user_id = rs.getInt(3);
            String type_income = rs.getString(4);
            list.add(new Income(id,salary,month,this.getUserById(user_id),type_income));
        }
        return list;
    }

    public int getSumIncomeByMonth(int userId, int month) {
        int sumIncome = 0;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] columns = {"salary"};
        String selection = "user_id=? AND month=?";
        String[] selectionArgs = {String.valueOf(userId), String.valueOf(month)};
        Cursor cursor = sqLiteDatabase.query("income", columns, selection, selectionArgs,
                null, null, null);
        while (cursor != null && cursor.moveToNext()) {
            int salary = cursor.getInt(cursor.getColumnIndexOrThrow("salary"));
            sumIncome += salary;
        }
        if (cursor != null) {
            cursor.close();
        }
        return sumIncome;
    }

    public int updateIncome(Income i) {
        ContentValues values = new ContentValues();
        values.put("salary", i.getSalary());
        values.put("month", i.getMonth());
        values.put("user_id", i.getUser().getId());
        values.put("type_income", i.getTypeIncome());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id = ? ";
        String[] whereArgs = {Integer.toString(i.getId())};
        return sqLiteDatabase.update("income",
                values, whereClause, whereArgs);
    }

    public int deleteIncome(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("income",
                whereClause, whereArgs);
    }





}


