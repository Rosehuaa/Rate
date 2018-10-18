package com.example.mei.rate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "myrate.db";
    public static final String TB_NAME = "tb_rates";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);//必须通过super 调用父类的构造函数
    }

    //数据库的构造函数，传递三个参数的...

    //数据库的构造函数，传递一个参数的， 数据库名字和版本号都写死了
    public DBHelper(Context context) {
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    // 回调函数，第一次创建时才会调用此函数，创建一个数据库
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TB_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,CURNAME TEXT,CURRATE TEXT)");
        //创建一个名称为TB_NAME的值的表，表头为id为主键自增，汇率名称，汇率的值
    }

    @Override
    //回调函数，当你构造DBHelper的传递的Version与之前的Version调用此函数
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //更新版本时对数据库的操作方法 从版本i 到版本i1

    }
}
