package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private MatchFragment matchFragment;
    private RecordFragment recordFragment;
    private InfoFragment infoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBottomNavigation();
    }

    private void initBottomNavigation(){ // 뷰 초기화 부분 -> onCreate에 넣어주자.
        homeFragment = new HomeFragment();
        matchFragment = new MatchFragment();
        recordFragment = new RecordFragment();
        infoFragment = new InfoFragment();

        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bnv_js); // 처음화면
        getSupportFragmentManager().beginTransaction().add(R.id.main_frm_js, homeFragment).commitAllowingStateLoss(); // FrameLayout에 fragment_home.xml 띄우기

        //바텀 네비게이션 뷰 안의 아이템 설정
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){ // item 클릭 시 id값을 가져와서 FrameLayout에 fragment_xxx.xml띄우기
                    case R.id.homeFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js,homeFragment).commitAllowingStateLoss();
                        break;
                    case R.id.matchFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js,matchFragment).commitAllowingStateLoss();
                        break;
                    case R.id.recordFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js,recordFragment).commitAllowingStateLoss();
                        break;
                    case R.id.infoFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js,infoFragment).commitAllowingStateLoss();
                        break;
                }
                return true;
            }
        });
    }
}