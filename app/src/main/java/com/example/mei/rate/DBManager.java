package com.example.mei.rate;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DBHelper dbHelper;//定义dbhelp
    private String TBNAME;
    //操作类

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);//初始化
        TBNAME = DBHelper.TB_NAME;//获取dbhelper中的表名
    }

    public void add(RateItem item){//插入一条
        SQLiteDatabase db = dbHelper.getWritableDatabase();//获取SQLiteDatabase实例，写权限
        ContentValues values = new ContentValues();//看作map
        values.put("curname", item.getCurName());//实体RateItem的汇率名称赋值
        values.put("currate", item.getCurRate());//实体RateItem的汇率值 赋值
        db.insert(TBNAME, null, values);//插入此条
        db.close();//关闭
    }

    public void addAll(List<RateItem> list){//插入一个list的实体到表中
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (RateItem item : list) {
            ContentValues values = new ContentValues();
            values.put("curname", item.getCurName());
            values.put("currate", item.getCurRate());
            db.insert(TBNAME, null, values);
        }
        db.close();
    }

    public void deleteAll(){//删除这个表中的全部内容
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();
    }

    public void delete(int id){//删除id为id的内容
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void update(RateItem item){//更新RateItem item类的id为id的内容
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("curname", item.getCurName());
        values.put("currate", item.getCurRate());
        db.update(TBNAME, values, "ID=?", new String[]{String.valueOf(item.getId())});//？为后面的内容，几个问号几个内容
        db.close();
    }

    public List<RateItem> listAll(){//回调显示全部
        List<RateItem> rateList = null;//新建一个内容为rateItem的list
        SQLiteDatabase db = dbHelper.getReadableDatabase();//获取SQLiteDatabase实例，写权限
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){//游标信息不为空的话，
            rateList = new ArrayList<RateItem>();//初始化list
            while(cursor.moveToNext()){//移动游标
                RateItem item = new RateItem();//新建实体
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));//找到本行列名为ID的数据，放入item的id中
                item.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
                item.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));

                rateList.add(item);//放入内容的item加入到list中
            }
            cursor.close();
        }
        db.close();
        return rateList;

    }

    public RateItem findById(int id){//回调显示id处
        SQLiteDatabase db = dbHelper.getReadableDatabase();//获取SQLiteDatabase实例，读权限
        Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        RateItem rateItem = null;
        if(cursor!=null && cursor.moveToFirst()){//光标移到最前面且不为空
            rateItem = new RateItem();
            rateItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            rateItem.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
            rateItem.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));
            cursor.close();
        }
        db.close();
        return rateItem;
    }
}
