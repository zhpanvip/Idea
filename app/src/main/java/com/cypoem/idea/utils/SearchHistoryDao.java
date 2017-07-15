package com.cypoem.idea.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cypoem.idea.module.bean.SearchHistoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengshihao on 2016/10/27.
 */

public class SearchHistoryDao {


    DbHelper helper;

    public SearchHistoryDao(Context context) {
        helper = new DbHelper(context);
    }

    //添加历史记录
    public boolean insertHistory(SearchHistoryBean history) {
        SQLiteDatabase db = helper.getReadableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("item", history.getItem());

            db.insert("history", null, values);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;
    }

    //更新历史记录
    public boolean updateHistory(SearchHistoryBean history) {
        SQLiteDatabase db = helper.getReadableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("item", history.getItem());
            db.update("history", values, "item = ?", new String[]{history.getId() + ""});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;
    }

    //  通过ID删除历史
    public boolean delHistoryById(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        try {
            db.delete("history", "id=?", new String[]{id + ""});
            return true;
        } catch (Exception e) {
            e.printStackTrace();//打印错误堆栈信息
        } finally {
            db.close();
        }
        return false;
    }

    //  通过item删除历史
    public boolean delHistoryByItem(String item) {
        SQLiteDatabase db = helper.getReadableDatabase();
        try {
            db.delete("history", "item=?", new String[]{item});

            return true;
        } catch (Exception e) {
            e.printStackTrace();//打印错误堆栈信息
        } finally {
            db.close();
        }
        return false;
    }

    //根据id查询历史记录
    public SearchHistoryBean findById(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("history", null, "id=?", new String[]{id + ""}, null, null, null);
        SearchHistoryBean history = null;
        if (cursor.moveToNext()) {
            history = new SearchHistoryBean();
            history.setId(id);
            history.setItem(cursor.getString(cursor.getColumnIndex("item")));

        }
        return history;
    }

    //  查询是否已经存在该item
    public boolean isItemExists(String item) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("history", null, "item=?", new String[]{item}, null, null, null);
        int count = cursor.getCount();
        if (count > 0) {
            return true;
        } else {
            return false;

        }
    }

    //查询所有历史记录
    public List<SearchHistoryBean> findAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("history", null, null, null, null, null, "id desc");
        List<SearchHistoryBean> list = new ArrayList<>();
        SearchHistoryBean history = null;
        while (cursor.moveToNext()) {
            history = new SearchHistoryBean();
            history.setId(cursor.getInt(cursor.getColumnIndex("id")));
            history.setItem(cursor.getString(cursor.getColumnIndex("item")));
            list.add(history);
        }
        cursor.close();
        return list;
    }
    //查询所有历史记录
    public List<SearchHistoryBean> findLimit(int limit) {
        SQLiteDatabase db = helper.getReadableDatabase();
        //Cursor cursor = db.query("history", null, null, null, null, null, "id desc","limit 8");
        Cursor cursor = db.rawQuery("select * from history order by id desc limit ?", new String[]{limit+""});
        List<SearchHistoryBean> list = new ArrayList<>();
        SearchHistoryBean history;
        while (cursor.moveToNext()) {
            history = new SearchHistoryBean();
            history.setId(cursor.getInt(cursor.getColumnIndex("id")));
            history.setItem(cursor.getString(cursor.getColumnIndex("item")));
            list.add(history);
        }
        cursor.close();
        return list;
    }

    //  删除所有历史记录
    public boolean delAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        db.execSQL("delete from history");

        return false;
    }


}
