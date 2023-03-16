package com.example.capstoneproject.activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;
import com.example.capstoneproject.adapter.AlarmAdapter;
import com.example.capstoneproject.data.users.UserService;
import com.example.capstoneproject.data.users.response.push.GetPushListResult;
import com.example.capstoneproject.view.GetPushListView;

import java.util.List;

public class AlarmActivity extends AppCompatActivity implements GetPushListView {

    private RecyclerView recyclerView;
    private AlarmAdapter adapter;

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
    }

    private void getList() {
        //TODO 알림 리스트 조회 API 호출
        UserService userService = new UserService();
        userService.setPushListView(this);
        userService.getPushList(getJwt());
    }

    private void initRecyclerView(List<GetPushListResult> result){
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new AlarmAdapter(result, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    private String getJwt(){
        SharedPreferences spf = this.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE);
        return spf.getString("jwt","");
    }

    private void initView() {
        recyclerView = findViewById(R.id.alarm_recyclerview);
    }

    @Override
    public void onGetPushListSuccess(List<GetPushListResult> result) {
        initRecyclerView(result);
    }

    @Override
    public void onGetPushListFailure() {

    }
}
