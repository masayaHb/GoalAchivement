package com.example.goalachivement.Dto;

import java.io.Serializable;

public class GoalDto implements Serializable {
    public long _id; //ID
    public String goal;//目標
    public String goal_date; //目標年月日
    public String back_color;//目標ボタンの背景色
    public String create_date_goal;//目標の作成日、開始日
    public String detail;//詳細
}

