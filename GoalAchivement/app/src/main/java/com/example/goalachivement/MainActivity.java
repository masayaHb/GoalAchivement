package com.example.goalachivement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.goalachivement.Dao.GoalDao;
import com.example.goalachivement.Dto.GoalDto;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listViewMain;
    private Button addButtonMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButtonMain = (Button) findViewById(R.id.addButtonMain);

        //ListViewを取得
        listViewMain = (ListView) findViewById(R.id.listViewMain);
        //ListViewにアダプターを設定
        listViewMain.setAdapter(createAdapter());


        //追加ボタンクリック時の処理
        addButtonMain.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),AddGoal.class);
                startActivity(intent);
            }
        });



    }

    public MainArrayAdapter createAdapter(){
        //アダプターに渡すデータをDBから取得
        List<GoalDto> mainList = GoalDao.findGoalDayColor(getApplicationContext());
        //アダプター生成
        MainArrayAdapter adapter = new MainArrayAdapter(this,0,mainList);
        return adapter;
    }



}