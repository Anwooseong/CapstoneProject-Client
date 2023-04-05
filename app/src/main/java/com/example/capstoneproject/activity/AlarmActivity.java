package com.example.capstoneproject.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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

    private void getList() {
        UserService userService = new UserService();
        userService.setPushListView(this);
        userService.getPushList(getJwt());
    }

    private void initRecyclerView(List<GetPushListResult> result){
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new AlarmAdapter(result, getApplicationContext(), getUserIdx(), getJwt());
        recyclerView.setAdapter(adapter);
    }


    private void initView() {
        recyclerView = findViewById(R.id.alarm_recyclerview);
        backBtn = findViewById(R.id.alarm_back_btn);
    }

    @Override
    public void onGetPushListSuccess(List<GetPushListResult> result) {
        initRecyclerView(result);
    }

    @Override
    public void onGetPushListFailure() {

    }

    private String getJwt(){
        SharedPreferences spf = this.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE);
        return spf.getString("jwt","");
    }
    private int getUserIdx(){
        SharedPreferences spf = this.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE);
        return spf.getInt("userIdx", 0);
    }
}
