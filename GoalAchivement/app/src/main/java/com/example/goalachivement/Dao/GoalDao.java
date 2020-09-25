package com.example.goalachivement.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.goalachivement.Dto.GoalDto;
import com.example.goalachivement.OpenHelper.DatabaseOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class GoalDao {
    public static String TABLE_NAME_GOAL="goals";//テーブル名
    public static String COLUMN_ID ="_id"; //ID
    public static String COLUMN_GOAL ="goal";//目標
    public static String COLUMN_GOAL_DATE ="goal_date"; //目標年月日
    public static String COLUMN_BACK_COLOR ="back_color";//目標ボタンの背景色
    public static String COLUMN_CREATE_DATE_GOAL ="create_date_goal";//目標の作成日と開始日
    public static String COLUMN_DETAIL = "detail";//詳細

    //登録処理(goals テーブル)
    public static  long insert(Context context, GoalDto goalDto){
        DatabaseOpenHelper helper = new DatabaseOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        long rowId = 0;
        ContentValues goalsValues = new ContentValues();
        goalsValues.put(COLUMN_GOAL,goalDto.goal);
        goalsValues.put(COLUMN_GOAL_DATE,goalDto.goal_date);
        goalsValues.put(COLUMN_BACK_COLOR,goalDto.back_color);
        goalsValues.put(COLUMN_CREATE_DATE_GOAL,goalDto.create_date_goal);


        rowId = db.insert(TABLE_NAME_GOAL,null,goalsValues);

        return rowId;
    }


    //目標と指定した日時と作成日と色の取得
    public static List<GoalDto> findGoalDayColor(Context context){
        DatabaseOpenHelper helper = new DatabaseOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "select _id,goal,goal_date,back_color,create_date_goal,detail from goals";
        Cursor cursor = db.rawQuery(sql,null);

        List<GoalDto> goalsList = new ArrayList<>();
        while(cursor.moveToNext()){
            GoalDto goalDto = new GoalDto();
            goalDto._id = cursor.getLong(0);
            goalDto.goal = cursor.getString(1);
            goalDto.goal_date = cursor.getString(2);
            goalDto.back_color = cursor.getString(3);
            goalDto.create_date_goal = cursor.getString(4);
            goalDto.detail = cursor.getString(5);
            goalsList.add(goalDto);
        }

        cursor.close();
        return goalsList;
    }

    //全件検索(確認用)
    public static void findAllGoals(Context context) {
        DatabaseOpenHelper helper = new DatabaseOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "select * from goals";
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {

            Log.d("_id", String.valueOf(cursor.getLong(0)));
            Log.d("goal", cursor.getString(1));
            Log.d("goal_date", cursor.getString(2));
            Log.d("back_color", cursor.getString(3));
            Log.d("create_date_goal", cursor.getString(4));
            Log.d("detail", cursor.getString(4));
        }
        cursor.close();
    }



    //削除処理
    public static int delete(Context context,long id){
        DatabaseOpenHelper helper = new DatabaseOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        return db.delete(TABLE_NAME_GOAL,COLUMN_ID + "=?",new String[]{String.valueOf(id)});
    }

    //更新処理
    public static int update(Context context,GoalDto goalDto){
        DatabaseOpenHelper helper = new DatabaseOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_GOAL,goalDto.goal);
        values.put(COLUMN_BACK_COLOR,goalDto.back_color);
        values.put(COLUMN_CREATE_DATE_GOAL,goalDto.create_date_goal);
        values.put(COLUMN_GOAL_DATE,goalDto.goal_date);
        values.put(COLUMN_DETAIL,goalDto.detail);

        return db.update(TABLE_NAME_GOAL,values,COLUMN_ID + " = ?",new String[]{String.valueOf(goalDto._id)});
    }
}
