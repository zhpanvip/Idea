package com.cypoem.idea.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhpan on 2016/10/27.
 */

public class DbHelper extends SQLiteOpenHelper {



    public DbHelper(Context context){
        super(context,"ect.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists history(id integer primary key autoincrement,item varchar(60))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion){
            //  更新数据库
        }
    }
}
