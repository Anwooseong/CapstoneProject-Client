package com.uou.capstoneproject.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uou.capstoneproject.R;
import com.uou.capstoneproject.adapter.AlarmAdapter;
import com.uou.capstoneproject.data.users.UserService;
import com.uou.capstoneproject.data.users.response.push.GetPushListResult;
import com.uou.capstoneproject.view.GetPushListView;

import java.util.List;

public class AlarmActivity extends AppCompatActivity implements GetPushListView {

    private RecyclerView recyclerView;
    private AlarmAdapter adapter;
    private ImageView backBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivy_alarm);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getList();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 알림 목록을 가져오는 API 요청
     */
    private void getList() {
        UserService userService = new UserService();
        userService.setPushListView(this);
        userService.getPushList(getJwt());
    }


    /**
     * RecyclerView 초기화
     * result : 알림 목록 데이터
     */
    private void initRecyclerView(List<GetPushListResult> result){
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new AlarmAdapter(result, getApplicationContext(), getUserIdx(), getJwt());
        recyclerView.setAdapter(adapter);
    }


    /**
     * View 초기화
     */
    private void initView() {
        recyclerView = findViewById(R.id.alarm_recyclerview);
        backBtn = findViewById(R.id.alarm_back_btn);
    }

    /**
     * 알림 목록 데이터 API 호출 성공
     */
    @Override
    public void onGetPushListSuccess(List<GetPushListResult> result) {
        initRecyclerView(result);
    }

    /**
     * 알림 목록 데이터 API 호출 실패
     */
    @Override
    public void onGetPushListFailure() {

    }

    /**
     * 사용자의 JWT 토큰을 SharedPreferences에서 가져옴
     * @return JWT 토큰
     */
    private String getJwt(){
        SharedPreferences spf = this.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE);
        return spf.getString("jwt","");
    }

    /**
     * 사용자의 id를 SharedPreferences에서 가져옴
     * @return 사용자 id
     */
    private int getUserIdx(){
        SharedPreferences spf = this.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE);
        return spf.getInt("userIdx", 0);
    }
}
