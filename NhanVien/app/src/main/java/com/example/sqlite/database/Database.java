//package com.example.sqlite.database;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import androidx.annotation.Nullable;
//
//import com.example.sqlite.model.Category;
//import com.example.sqlite.model.Item;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Database extends SQLiteOpenHelper {
//    private final static String DATABASE_NAME="nhom8.db";
//    private final static int DATABSE_VERSION=1;
//    public Database(@Nullable Context context) {
//        super(context, DATABASE_NAME, null, DATABSE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        //tat ca cac bang tao o day
//        //tao 2 bang
//        //tao categories
//        String sql="create table categories(" +
//                "id integer primary key autoincrement," +
//                "name text)";
//        db.execSQL(sql);
//        //tao items
//        sql="create table items(" +
//                "id integer primary key autoincrement," +
//                "name text," +
//                "cid integer," +
//                "price real," +
//                "date text," +
//                "foreign key(cid) references categories(id))";
//        db.execSQL(sql);
//        //tao tiep neu co bang nua
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//
//    }
//    //insert into
//    //cach do
//    public void insertCate(Category c){
//        String sql="insert into categories(name) values(?)";
//        String[] agrs={c.getName()};
//        SQLiteDatabase st=getWritableDatabase();
//        st.execSQL(sql,agrs);
//    }
//    //cach vang
//    public long insertItem(Item t){
//        ContentValues values=new ContentValues();
//        values.put("name",t.getName());
//        values.put("cid",t.getCategory().getId());
//        values.put("price",t.getPrice());
//        values.put("date",t.getDate());
//        SQLiteDatabase st=getWritableDatabase();
//        return st.insert("items",null,values);
//    }
//    //lay tat tu bang categories
//    public List<Category> getCates(){
//        List<Category> list=new ArrayList<>();
//        SQLiteDatabase st=getReadableDatabase();
//        Cursor rs=st.query("categories",null,null,null,
//                null,null,null);
//        while(rs!=null && rs.moveToNext()){
//            list.add(new Category(rs.getInt(0),rs.getString(1)));
//        }
//        return list;
//    }
//    //lay items
//    public List<Item> getItems(){
//        List<Item> list=new ArrayList<>();
//        String sql="select t.id,t.name,t.price,t.date,c.id,c.name " +
//                "from categories c inner join items t " +
//                "on (c.id=t.cid)";
//        SQLiteDatabase st=getReadableDatabase();
//        Cursor rs=st.rawQuery(sql,null);
//        while(rs!=null && rs.moveToNext()){
//            Category c=new Category(rs.getInt(4),rs.getString(5));
//            list.add(new Item(rs.getInt(0),rs.getString(1),rs.getDouble(2),
//                    rs.getString(3),c));
//        }
//        rs.close();
//        return list;
//    }
//    public Item getItemById(int id){
//        String where="id=?";
//        String[] agrs={Integer.toString(id)};
//        SQLiteDatabase st=getReadableDatabase();
//        Cursor rs=st.query("items",null,where,agrs,null,
//                null,null);
//        if(rs!=null && rs.moveToNext()){
//            return new Item(rs.getInt(0),rs.getString(1),rs.getDouble(3),
//                    rs.getString(4),new Category(rs.getInt(2),""));
//
//        }
//        return null;
//    }
//    //update item
//    public int updateItem(Item t){
//        ContentValues values=new ContentValues();
//        values.put("name",t.getName());
//        values.put("cid",t.getCategory().getId());
//        values.put("price",t.getPrice());
//        values.put("date",t.getDate());
//        String where="id=?";
//        String[] agrs={Integer.toString(t.getId())};
//        SQLiteDatabase st=getWritableDatabase();
//        return st.update("items",values,where,agrs);
//    }
//    public int deleteItem(int id){
//        String where="id=?";
//        String[] agrs={Integer.toString(id)};
//        SQLiteDatabase st=getWritableDatabase();
//        return st.delete("items",where,agrs);
//    }
//    public List<Item> searchItemBykey(String key){
//        List<Item> list=new ArrayList<>();
//        String sql="select t.id,t.name,t.price,t.date,c.id,c.name " +
//                "from categories c inner join items t " +
//                "on (c.id=t.cid) where t.name like ? or c.name like ?";
//        String[] agrs={"%"+key+"%","%"+key+"%"};
//        SQLiteDatabase st=getReadableDatabase();
//        Cursor rs=st.rawQuery(sql,agrs);
//        while(rs!=null && rs.moveToNext()){
//            Category c=new Category(rs.getInt(4),rs.getString(5));
//            list.add(new Item(rs.getInt(0),rs.getString(1),rs.getDouble(2),
//                    rs.getString(3),c));
//        }
//        rs.close();
//        return list;
//    }
//    public List<Item> getItemByfromPricetoPrice(double from,double to){
//        List<Item> list=new ArrayList<>();
//        String where="price between ? and ?";
//        String[] agrs={Double.toString(from),Double.toString(to)};
//        String orderby="price desc";
//        SQLiteDatabase st=getReadableDatabase();
//        Cursor rs=st.query("items",null,where,agrs,null,
//                null,orderby);
//        while(rs!=null && rs.moveToNext()){
//            list.add( new Item(rs.getInt(0),rs.getString(1),rs.getDouble(3),
//                    rs.getString(4),new Category(rs.getInt(2),"")));
//
//        }
//        return list;
//    }
//    public List<Item> searchByKey(String key){
//        List<Item> list=new ArrayList<>();
//        String where="name like ? or date like ?";
//        String[] agrs={"%"+key+"%","%"+key+"%"};
//        String orderby="date desc";
//        SQLiteDatabase st=getReadableDatabase();
//        Cursor rs=st.query("items",null,where,agrs,null,
//                null,orderby);
//        while(rs!=null && rs.moveToNext()){
//            list.add( new Item(rs.getInt(0),rs.getString(1),rs.getDouble(3),
//                    rs.getString(4),new Category(rs.getInt(2),"")));
//
//        }
//        return list;
//    }
//}
