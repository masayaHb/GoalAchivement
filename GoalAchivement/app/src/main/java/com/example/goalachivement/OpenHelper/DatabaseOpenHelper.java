package com.example.goalachivement.OpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    //データベース名の指定
    private static final String DB_NAME = "portfolio_goal.db";

    //データベースのバージョン指定
    private static final int DB_VERSION = 1;

    public DatabaseOpenHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        //テーブル作成
        //goals テーブル
        sqLiteDatabase.execSQL("create table goals(" +
                "_id integer primary key autoincrement," +
                "goal text," +
                "goal_date text," +
                "back_color text ," +
                "create_date_goal text ," +
                "detail text)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int oldVersion,int newVersion){
        //データベースに変更が生じた場合の処理
    }
}
