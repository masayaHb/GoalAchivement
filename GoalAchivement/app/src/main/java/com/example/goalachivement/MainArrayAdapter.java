package com.example.goalachivement;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.goalachivement.Dto.GoalDto;

import java.util.List;

import static com.example.goalachivement.DateCulc.getNowDate;


public class MainArrayAdapter extends ArrayAdapter<GoalDto> {

    public MainArrayAdapter(@NonNull Context context, int resource, @NonNull List<GoalDto> goalList){
        super(context,resource,goalList);
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent){
        //データを初めて表示する場合
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_main,null);
        }
        //各行のデータを取得
        final GoalDto goalDto = getItem(position);
        //各行のViewを取得
        Button goalButtonMain = (Button) convertView.findViewById(R.id.goalButtonMain);
        TextView restDayMain = (TextView) convertView.findViewById(R.id.restDayMain);


        //ボタンにテキストをセット
        goalButtonMain.setText(goalDto.goal);
        //ボタンに選択した色をセット
        String color = goalDto.back_color;
        switch (color) {
            case "white":
                goalButtonMain.setBackgroundResource(R.drawable.button_white);
                break;
            case "red":
                goalButtonMain.setBackgroundResource(R.drawable.button_red);
                break;
            case "blue":
                goalButtonMain.setBackgroundResource(R.drawable.button_blue);
                break;
            case "green":
                goalButtonMain.setBackgroundResource(R.drawable.button_green);
                break;
            case "yellow":
                goalButtonMain.setBackgroundResource(R.drawable.button_yellow);
                break;
            case "orange":
                goalButtonMain.setBackgroundResource(R.drawable.button_orange);
                break;
            case "gray":
                goalButtonMain.setBackgroundResource(R.drawable.button_gray);
                break;
        }



        //ListViewのボタンクリック時の処理
        goalButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),DetailGoal.class);
                intent.putExtra("GOALDTO",goalDto);
                getContext().startActivity(intent);
            }
        });

        //残り日数を表示
        String nowDate = getNowDate();
        DateCulc dc = new DateCulc();
        String restDay = String.valueOf(dc.dateDiff(nowDate,goalDto.goal_date));
        restDayMain.setText(restDay);
        return convertView;


    }


}
