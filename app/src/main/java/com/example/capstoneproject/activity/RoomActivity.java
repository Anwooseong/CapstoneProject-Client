package com.example.capstoneproject.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.capstoneproject.data.getmatchdetail.GetMatchRoomDetailService;
import com.example.capstoneproject.data.getmatchdetail.response.GetMatchRoomDetailResult;
import com.example.capstoneproject.data.push.PushService;
import com.example.capstoneproject.data.push.request.ApplyPushMatchReq;
import com.example.capstoneproject.view.ApplyPushMatchView;
import com.example.capstoneproject.view.GetMatchRoomDetailView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class RoomActivity extends AppCompatActivity implements GetMatchRoomDetailView, ApplyPushMatchView {

    private int matchIdx;
    private ImageView backBtn, profile;
    private TextView date, place, nickName, time, content, avg, battle, cost;
    private AppCompatButton matchingBtn;
    private int matchOwnerUserIdx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        Intent intent = getIntent();
        matchIdx = intent.getIntExtra("matchIdx", 0);
        initView();
        backBtnListener();
        matchingBtnListener();
        GetMatchRoomDetailService getMatchRoomDetailService = new GetMatchRoomDetailService(this);
        getMatchRoomDetailService.getMatchRoomDetail(matchIdx);

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
                        postApplyMatch(getJwt(), new ApplyPushMatchReq(matchOwnerUserIdx, matchIdx));

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
//        time = findViewById(R.id.room_time_tv);
        content = findViewById(R.id.room_content_tv);
        avg = findViewById(R.id.room_avg_tv);
        battle = findViewById(R.id.room_battle_tv);
        cost = findViewById(R.id.room_cost_tv);
        matchingBtn = findViewById(R.id.room_btn);
    }

    @Override
    public void onGetMatchRoomSuccess(GetMatchRoomDetailResult result) {
        //TODO profile 이미지 url 명세서 작성하는데로
        date.setText(result.getDate());
        if (result.getPlace() != null) {
            place.setText(result.getPlace());
        }
        nickName.setText(result.getNickname());
        //time 제외
        content.setText(result.getContent());
        avg.setText(""+result.getTargetScore()+"");
        battle.setText(""+result.getCount()/2+" vs " + result.getCount()/2+"");
        cost.setText(""+result.getCost()+"");
        matchOwnerUserIdx = result.getMatchUserIdx();
    }

    @Override
    public void onGetMatchRoomFailure() {

    }

    @Override
    public void onApplyPushMatchSuccess(String jwt, ApplyPushMatchReq applyPushMatchReq) {
        Log.d("TAG", "onApplyPushMatchSuccess: "+jwt);
        Log.d("TAG", "onApplyPushMatchSuccess: "+applyPushMatchReq.toString());
    }

    @Override
    public void onApplyPushMatchFailure() {

    }
    private String getJwt(){
        SharedPreferences spf = this.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE);
        return spf.getString("jwt","");
    }

    private void postApplyMatch(String jwt, ApplyPushMatchReq applyPushMatchReq) {
        PushService pushService = new PushService();
        pushService.setApplyPushMatchView(this);
        pushService.applyPushMatch(jwt, applyPushMatchReq);
    }
}
