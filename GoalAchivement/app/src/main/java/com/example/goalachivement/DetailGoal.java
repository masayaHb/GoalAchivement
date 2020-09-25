package com.example.goalachivement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goalachivement.Dao.GoalDao;
import com.example.goalachivement.Dto.GoalDto;

public class DetailGoal extends AppCompatActivity {

    private Button detailBackButton;
    private TextView detailGoal;
    private TextView detailStart;
    private TextView detailFinish;
    private TextView pastDay;
    private TextView detail;
    private Button detailEditButton;
    private Button detailDeleteButton;

    //アラートダイアログの設定
    private void showAlertDialogOkOnly(String title, String message, final long deleteId, final String goal){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                //削除処理
                .setPositiveButton("OK",new  DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                deleteAlert(deleteId,goal);
            }
        })
                .setNegativeButton("CANCEL",null);
        builder.create().show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_goal);

        detailBackButton = findViewById(R.id.detailBackButton);
        detailGoal = findViewById(R.id.detailGoal);
        detailStart = findViewById(R.id.detailStart);
        detailFinish = findViewById(R.id.detailFinish);
        pastDay = findViewById(R.id.pastDay);
        detail = findViewById(R.id.detail);
        detailEditButton = findViewById(R.id.detailEditButton);
        detailDeleteButton = findViewById(R.id.detailDeleteButton);

        Intent intent = getIntent();
        final GoalDto goalDto = (GoalDto)intent.getSerializableExtra("GOALDTO");
        DateCulc dc = new DateCulc();
        detailGoal.setText(goalDto.goal);
        detailStart.setText("　開始日　：　" + goalDto.create_date_goal);
        detailFinish.setText("　終了日　：　" + goalDto.goal_date);
        pastDay.setText("　経過日数　：　" + dc.dateDiff(goalDto.create_date_goal,dc.getNowDate()) + "日");
        detail.setText(goalDto.detail);
        GoalDao ga = new GoalDao();
        ga.findAllGoals(getApplicationContext());



        //BACKボタンクリック時の処理
        detailBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        //編集ボタンクリック時の処理
        detailEditButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),EditGoal.class);
                intent.putExtra("GOALDTO",goalDto);
                startActivity(intent);
            }
        });

        //削除ボタンクリック時の処理
        detailDeleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showAlertDialogOkOnly("削除", "この目標を削除しますか？",goalDto._id,goalDto.goal);
                return;
                //削除処理は直下のdeleteAlert()で行う
            }
        });

    }

        public void deleteAlert(long id,String goal){
            int result = GoalDao.delete(getApplicationContext(),id);
            if(result>0) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Toast.makeText(getApplicationContext(), "【" + goal + "】を削除しました", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        }
}