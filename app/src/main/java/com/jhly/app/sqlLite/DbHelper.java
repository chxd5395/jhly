package com.jhly.app.sqlLite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by r on 2017/7/19.
 */

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table order(id integer primary key autoincrement," +
                "order_id integer(5),"+"time varchar(15),"+"result integer(1))";
        String sql_lv="create table lv(id integer primary key autoincrement," +
                "lv_id integer(5),"+"lv_code varchar(15),"+"lv_vehicle varchar(15),"+"lv_weight integer(5),"+"lv_aim text,"+"lv_scode varchar(15),"+"lv_addr text))";
        String sql_pv="create table pv(id integer primary key autoincrement," +
                "pv_id integer(5),"+"pv_src varchar(10),"+"pv_protein integer(5),"+"pv_begin varchar(15),"+"pv_end varchar(15),"+"pv_total integer(5),"+"pv_used integer(5))";
        String sql_cv="create table cv(id integer primary key autoincrement," +
                "cv_id integer(5),"+"cv_name varchar(5),"+"cv_phone varchar(12))";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
