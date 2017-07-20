package com.jhly.app.sqlLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by r on 2017/7/19.
 */

public class OrderDb {
    private Context context;
    private DbHelper dbHelper;

    public OrderDb(Context context){
        this.context = context;
        dbHelper = new DbHelper(context,"order.db",null,1);
    }

    //插入数据
    public void insert(int id,String time,int result){
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        String sql = "insert into order(id,time,result)values(?,?,?)";
        writableDatabase.execSQL(sql,id,time,result);
    }

    //插入数据
    public void insert(int id,String code,String vehicle,float weight,String aim,String scode,String addr){
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        String sql = "insert into lv(id,name,phone)values(?,?,?)";
        writableDatabase.execSQL(sql);
    }

    //插入数据
    public void insert(int id,String src,int protein,String begin,String end,float total,float used){
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        String sql = "insert into pv(id,name,phone)values(?,?,?)";
        writableDatabase.execSQL(sql);
    }

    //插入数据
    public void insert(int id,String name,String phone){
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        String sql = "insert into cv(id,name,phone)values(?,?,?)";
        writableDatabase.execSQL(sql);
    }

    //查询数据
    public Cursor query(){
        SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
        String sql = "";
        return readableDatabase.rawQuery(sql,new String[]{});
    }
}
