package com.uou.capstoneproject.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.uou.capstoneproject.R;
import com.uou.capstoneproject.data.match.MatchService;
import com.uou.capstoneproject.data.match.response.GetMatchRoomDetailResult;
import com.uou.capstoneproject.data.push.PushService;
import com.uou.capstoneproject.data.push.request.ApplyPushMatchReq;
import com.uou.capstoneproject.data.push.response.ApplyPushMatchResult;
import com.uou.capstoneproject.view.ApplyPushMatchView;
import com.uou.capstoneproject.view.GetMatchRoomDetailView;

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

        // 인텐트에서 matchIdx 값을 가져온다.
        Intent intent = getIntent();
        matchIdx = intent.getIntExtra("matchIdx", 0);

        // 뷰 초기화
        initView();

        // 뒤로 가기 버튼 리스너를 설정
        backBtnListener();

        // 매칭 버튼 리스너를 설정
        matchingBtnListener();

        // MatchService 인스턴스를 생성
        MatchService matchService = new MatchService();
        matchService.setGetMatchRoomDetailView(this);

        // matchIdx를 이용하여 매치 룸의 상세 정보를 가져온다.
        matchService.getMatchRoomDetail(matchIdx);

    }

    private void backBtnListener() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //매칭 신청 버튼 누르면 AlertDialog로 매칭 신청 여부를 묻는다.
    private void matchingBtnListener() {
        matchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
    }

    //매칭 신청 버튼 누르면 호출되는 메서드
    private void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.matching_title);
        builder.setMessage(R.string.matching_content);
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        postApplyMatch(getJwt(), new ApplyPushMatchReq(matchOwnerUserIdx, matchIdx));
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                });
        builder.show();
    }


    //뷰 초기화
    private void initView() {
        backBtn = findViewById(R.id.room_back_btn);
        profile = findViewById(R.id.room_profile_iv);
        date = findViewById(R.id.room_date_tv);
        place = findViewById(R.id.room_place_tv);
        nickName = findViewById(R.id.room_nickname_tv);
        content = findViewById(R.id.room_content_tv);
        avg = findViewById(R.id.room_avg_tv);
        battle = findViewById(R.id.room_battle_tv);
        cost = findViewById(R.id.room_cost_tv);
        matchingBtn = findViewById(R.id.room_btn);
    }

    @Override
    public void onGetMatchRoomSuccess(GetMatchRoomDetailResult result) {
        // result 날짜 값을 가져와서 date 텍스트뷰에 설정
        date.setText(result.getDate());
        // result 장소 값이 null이 아닌 경우 place 텍스트뷰에 설정
        if (result.getPlace() != null) {
            place.setText(result.getPlace());
        }
        // result 닉네임 값을 가져와서 nickName 텍스트뷰에 설정
        nickName.setText(result.getNickname());
        //time 제외
        // result 내용 값을 가져와서 content 텍스트뷰에 설정
        content.setText(result.getContent());
        // result 타겟 점수 값을 가져와서 avg 텍스트뷰에 설정
        avg.setText(""+result.getTargetScore()+"");
        // result 참가자 수를 가져와서 battle 텍스트뷰에 설정
        battle.setText(""+result.getCount()/2+" vs " + result.getCount()/2+"");
        // result 비용 값을 가져와서 cost 텍스트뷰에 설정
        cost.setText(""+result.getCost()+"");
        // result 매치 소유자의 사용자 Id인 값을 matchOwnerUserIdx 변수에 저장
        matchOwnerUserIdx = result.getMatchUserIdx();
    }

    @Override
    public void onGetMatchRoomFailure() {

    }

    @Override
    public void onApplyPushMatchSuccess(String jwt, ApplyPushMatchResult applyPushMatchResult) {
        if (applyPushMatchResult.getPushIdx() == 0){
            Toast.makeText(getApplicationContext(),"이미 꽉찬 매칭방입니다",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onApplyPushMatchFailure() {

    }

    //SharedPreferences에 있는 jwt를 가져온다.
    private String getJwt(){
        SharedPreferences spf = this.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE);
        return spf.getString("jwt","");
    }

    private void postApplyMatch(String jwt, ApplyPushMatchReq applyPushMatchReq) {
        // PushService 인스턴스를 생성
        PushService pushService = new PushService();
        // ApplyPushMatchView를 현재 클래스로 설정
        pushService.setApplyPushMatchView(this);
        // jwt와 applyPushMatchReq를 이용하여 매치에 신청
        pushService.applyPushMatch(jwt, applyPushMatchReq);
    }
}
