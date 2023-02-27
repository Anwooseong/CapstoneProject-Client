package com.example.capstoneproject.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.capstoneproject.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class RoomActivity extends AppCompatActivity {

//    private TextView imsi;
    private ImageView backBtn, profile;
    private TextView date, place, nickName, time, content, avg, battle, cost;
    private AppCompatButton matchingBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        Intent intent = getIntent();
//        imsi = findViewById(R.id.imsi_tv);
//        imsi.setText(intent.getStringExtra("imsi"));
        initView();
        backBtnListener();
        matchingBtnListener();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }



    private void backBtnListener() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void matchingBtnListener() {
        matchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();

            }
        });
    }

    private void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.matching_title);
        builder.setMessage(R.string.matching_content);
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO 매칭 신청 API
                        Toast.makeText(getApplicationContext(),"예를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }



    private void initView() {
        backBtn = findViewById(R.id.room_back_btn);
        profile = findViewById(R.id.room_profile_iv);
        date = findViewById(R.id.room_date_tv);
        place = findViewById(R.id.room_place_tv);
        nickName = findViewById(R.id.room_nickname_tv);
        time = findViewById(R.id.room_time_tv);
        content = findViewById(R.id.room_content_tv);
        avg = findViewById(R.id.room_avg_tv);
        battle = findViewById(R.id.room_battle_tv);
        cost = findViewById(R.id.room_cost_tv);
        matchingBtn = findViewById(R.id.room_btn);
    }

}
