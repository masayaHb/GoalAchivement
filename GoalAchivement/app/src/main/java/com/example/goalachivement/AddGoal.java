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

import com.example.goalachivement.Dao.GoalDao;
import com.example.goalachivement.Dto.GoalDto;

import static com.example.goalachivement.DateCulc.checkDate;
import static com.example.goalachivement.DateCulc.getNowDate;

public class AddGoal extends AppCompatActivity {

    private EditText goal;
    private DatePicker datePicker;
    private Spinner spinnerBackColor;
    private Button setting_SetButton;
    private Button setting_BackButton;
    public String backColor;
    public String ymd;


    //アラートダイアログの設定
    private void showAlertDialogOkOnly(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK",null);
        builder.create().show();
    }


    //onCreate(アクティビティ生成時)の処理
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_goal);
        //activity_portfolio_setting.xmlファイル内の各ビュー参照を取得

        goal = (EditText)findViewById(R.id.editTextGoal);
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        spinnerBackColor = (Spinner)findViewById(R.id.spinnerBackColor);
        setting_SetButton = (Button)findViewById(R.id.setting_SetButton);
        setting_BackButton = (Button)findViewById(R.id.setting_BackButton);


        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int mon, int dy) {
                String month = String.format("%02d",mon + 1);
                String day  = String.format("%02d",dy);
                ymd = year +"/" + month + "/" + day;
            }
        });


        //spinnerBackColorにアダプターを設定
        String[] spinnerBackColors = {"white","red","blue","green","yellow","orange","gray"};
        ArrayAdapter  backColorAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,spinnerBackColors);
        spinnerBackColor.setAdapter(backColorAdapter);

        //spinnerBackColorのアイテム選択時のイベント処理を設定
        spinnerBackColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,int i,long l){
                String color = (String)adapterView.getSelectedItem();

                backColor = color;
            }
            @Override
            public  void onNothingSelected(AdapterView<?> adapterView){
            }
        });





        //設定ボタンを押した時の処理
        setting_SetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //目標(goal)の未入力チェック
                String checkGoal = goal.getText().toString();
                if(checkGoal.isEmpty()) {
                    showAlertDialogOkOnly("入力チェック", "目標が未入力です");
                    return;
                }else if(ymd == null){
                    showAlertDialogOkOnly("入力チェック", "カレンダーの日付をクリックしてください");
                }else if(checkDate(getNowDate(),ymd)){
                    //決めた年月日が本日以降かをチェック
                    showAlertDialogOkOnly("入力チェック","今日以降の日付を設定してください");
                    return;

                }else{

                    //登録追加
                    //Dtoオブジェクトの設定
                    GoalDto goalDto = new GoalDto();
                    goalDto.goal =goal.getText().toString();
                    goalDto.goal_date = ymd;
                    goalDto.create_date_goal =getNowDate();
                    goalDto.back_color =backColor;


                    //DB登録
                    long rowId = GoalDao.insert(getApplicationContext(),goalDto);
                    if(rowId > 0){
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        //戻るボタンを押した時の処理
        setting_BackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //戻るボタンを押すとMainActivityに遷移する処理
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
