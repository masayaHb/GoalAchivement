package com.example.goalachivement;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateCulc {
    public static String datePattern = "yyyy/MM/dd";

    //現在の日時を取得するメソッド
    public static String getNowDate(){
        Date date = new Date(); // 今日の日付
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        String nowDate = dateFormat.format(date);
        return nowDate;
    }

    //日付の差分を求めるメソッド
    public static int dateDiff(String dateFromString, String dateToString) {
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        Date dateTo = null;
        Date dateFrom = null;

        // Date型に変換
        try {
            dateFrom = sdf.parse(dateFromString);
            dateTo = sdf.parse(dateToString);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        // 差分の日数を計算する
        long dateTimeTo = dateTo.getTime();
        long dateTimeFrom = dateFrom.getTime();
        //1000(分m) * 60(1分) * 60(１時間) * 24(1日)
        long dayDiff = ( dateTimeTo - dateTimeFrom  ) / (1000 * 60 * 60 * 24 );

        return (int) dayDiff;
    }

    //選択した日時が今日以降かを判定(add_goal用:選択日時をDatePickerからとるから)
    public static boolean checkDate(String dateFromString, String dateToString){
        Date dateFrom = new Date(dateFromString);
        Date dateTo = new Date(dateToString);
        boolean check;
        if(dateFrom.compareTo(dateTo) >= 0){
            check = true;
        }else{
            check = false;
        }

        return check;

    }

}

