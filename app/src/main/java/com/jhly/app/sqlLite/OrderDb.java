package com.jhly.app.sqlLite;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.database.sqlite.SQLiteDatabase;

import com.jhly.app.api.Result;

/**
 * Created by r on 2017/7/19.
 */

public class OrderDb {
    private Context context;
    private DbHelper dbHelper;
    private Result mResult;

    public OrderDb(Context context,Result result){
        this.context = context;
        mResult = result;
        dbHelper = new DbHelper(context,"order.db",null,1);
    }

    //插入数据
    public void insert(int id,String time,int result){
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        String sql = "insert into myorder(order_id,time,result) values(?,?,?);";
        writableDatabase.execSQL(sql,new Object[]{id,time,result});
    }

    //插入数据
    public void insert(int id,String code,String vehicle,float weight,String aim,String scode,String addr){
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        String sql = "insert into lv(lv_id,lv_code,lv_vehicle,lv_weight,lv_aim,lv_scode,lv_addr) values(?,?,?,?,?,?,?)";
        writableDatabase.execSQL(sql,new Object[]{id,code,vehicle,weight,aim,scode,addr});
    }

    //插入数据
    public void insert(int id,String src,int protein,String begin,String end,float total,float used){
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        String sql = "insert into pv(pv_id,pv_src,pv_protein,pv_begin,pv_end,pv_total,pv_used) values(?,?,?,?,?,?,?)";
        writableDatabase.execSQL(sql,new Object[]{id,src,protein,begin,end,total,used});
    }

    //插入数据
    public void insert(int id,String name,String phone){
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        String sql = "insert into cv(cv_id,cv_name,cv_phone) values(?,?,?)";
        writableDatabase.execSQL(sql,new Object[]{id,name,phone});
    }

    //查询数据
    public Cursor query(){
        SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
        String sql = "";
        return readableDatabase.rawQuery(sql,new String[]{});
    }
}
