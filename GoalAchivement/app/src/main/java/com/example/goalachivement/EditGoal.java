package com.example.goalachivement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.goalachivement.Dao.GoalDao;
import com.example.goalachivement.Dto.GoalDto;

import static com.example.goalachivement.DateCulc.checkDate;
import static com.example.goalachivement.DateCulc.getNowDate;

public class EditGoal extends AppCompatActivity {

    private Button detailBackButton;
    private EditText editGoal;
    private TextView editStartDay;
    private DatePicker editFinish;
    private Spinner editSpinnerBackColor;
    private EditText editDetail;
    private Button editButton;
    public String ymd;
    public String backColor;

    //アラートダイアログの設定
    private void showAlertDialogOkOnly(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK",null);
        builder.create().show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goal);

        detailBackButton = findViewById(R.id.detailBackButton);
        editGoal = findViewById(R.id.editGoal);
        editStartDay = findViewById(R.id.editStartDay);
        editFinish = findViewById(R.id.editFinish);
        editSpinnerBackColor = findViewById(R.id.editSpinnerBackColor);
        editDetail = findViewById(R.id.editDetail);
        editButton = findViewById(R.id.editButton);


        Intent intent = getIntent();
        final GoalDto goalDto = (GoalDto)intent.getSerializableExtra("GOALDTO");

        editGoal.setText(goalDto.goal);
        editStartDay.setText(goalDto.create_date_goal);
        editDetail.setText(goalDto.detail);
        //DatePickerに選択日時を渡す
        String pickerYMD = goalDto.goal_date;
        int pYear = Integer.parseInt(pickerYMD.substring(0,4));
        int pMonth = Integer.parseInt(pickerYMD.substring(5,7))-1;
        int pDay = Integer.parseInt(pickerYMD.substring(8,10));
        editFinish.updateDate(pYear,pMonth,pDay);

        //DatePickerのSpinner未使用の時に値を設定する
        if(ymd == null){
            String month = String.format("%02d",pMonth + 1);
            String day  = String.format("%02d",pDay);
            ymd = pYear +"/" + month + "/" + day;
        }


        //背景色設定
        String[] spinnerBackColors = {"white","red","blue","green","yellow","orange","gray"};
        ArrayAdapter backColorAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,spinnerBackColors);
        editSpinnerBackColor.setAdapter(backColorAdapter);

        int default_color = 0;
        String back_color = goalDto.back_color;
        switch (back_color){
            case "white":
                default_color = 0;
                break;
            case "red":
                default_color = 1;
                break;
            case "blue":
                default_color = 2;
                break;
            case "green":
                default_color = 3;
                break;
            case "yellow":
                default_color = 4;
                break;
            case "orange":
                default_color = 5;
                break;
            case "gray":
                default_color = 6;
                break;
        }

        editSpinnerBackColor.setSelection(default_color);

        //背景色取得
        //spinnerBackColorのアイテム選択時のイベント処理を設定
        editSpinnerBackColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,int i,long l){
                String color = (String)adapterView.getSelectedItem();

                backColor = color;
            }
            @Override
            public  void onNothingSelected(AdapterView<?> adapterView){
            }
        });

        //終了日時取得
        editFinish.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int mon, int dy) {
                String month = String.format("%02d",mon + 1);
                String day  = String.format("%02d",dy);
                ymd = year +"/" + month + "/" + day;
            }
        });

        //戻るボタンの設定
        detailBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DetailGoal.class);
                intent.putExtra("GOALDTO",goalDto);
                startActivity(intent);
            }
        });

        //設定ボタンの処理
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //目標の未入力チェック
                String checkGoal = editGoal.getText().toString();
                if(checkGoal.isEmpty()) {
                    showAlertDialogOkOnly("入力チェック", "目標が未入力です");
                    return;
                }else if(checkDate(getNowDate(),ymd)){
                    //決めた年月日が本日以降かをチェック
                    showAlertDialogOkOnly("入力チェック","今日以降の日付を設定してください");
                    return;

                }else {

                    goalDto.goal = editGoal.getText().toString();
                    goalDto.goal_date = ymd;
                    goalDto.back_color = backColor;
                    goalDto.detail = editDetail.getText().toString();
                    int result = GoalDao.update(getApplicationContext(), goalDto);
                    if (result > 0) {
                        Intent intent = new Intent(getApplicationContext(), DetailGoal.class);
                        intent.putExtra("GOALDTO", goalDto);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}