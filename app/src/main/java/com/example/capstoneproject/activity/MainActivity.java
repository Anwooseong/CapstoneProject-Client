package com.example.capstoneproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.capstoneproject.fragment.HomeFragment;
import com.example.capstoneproject.fragment.InfoFragment;
import com.example.capstoneproject.fragment.MatchFragment;
import com.example.capstoneproject.R;
import com.example.capstoneproject.fragment.RecordFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private MatchFragment matchFragment;
    private RecordFragment recordFragment;
    private InfoFragment infoFragment;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBottomNavigation();
    }

    private void initBottomNavigation() { // 뷰 초기화 부분 -> onCreate에 넣어주자.
        homeFragment = new HomeFragment();
        matchFragment = new MatchFragment();
        recordFragment = new RecordFragment();
        infoFragment = new InfoFragment();

        bottomNavigationView = findViewById(R.id.main_bnv_js); // 처음화면
        getSupportFragmentManager().beginTransaction().add(R.id.main_frm_js, homeFragment).commitAllowingStateLoss(); // FrameLayout에 fragment_home.xml 띄우기

        //바텀 네비게이션 뷰 안의 아이템 설정
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) { // item 클릭 시 id값을 가져와서 FrameLayout에 fragment_xxx.xml띄우기
                    case R.id.homeFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js, homeFragment).commitAllowingStateLoss();
                        break;
                    case R.id.matchFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js, matchFragment).commitAllowingStateLoss();
                        break;
                    case R.id.recordFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js, recordFragment).commitAllowingStateLoss();
                        break;
                    case R.id.infoFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js, infoFragment).commitAllowingStateLoss();
                        break;
                }
                return true;
            }
        });
    }

    public void onFragmentChange(int index) {
        if (index == 1) {
            bottomNavigationView.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js, matchFragment).commitAllowingStateLoss();
        }
    }

    private long backPressedTime = 0;

    public void onGoneBottomNavigation() {
        bottomNavigationView.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_frm_js);
        Log.d("TAG", "현재 프래그먼트: "+fragment);
        if (!(fragment instanceof HomeFragment)) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js, homeFragment)
                    .addToBackStack(null)
                    .commit();
            setHomeItem();
            bottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            if (System.currentTimeMillis() > backPressedTime + 1000) {
                backPressedTime = System.currentTimeMillis();
                Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            } else if (System.currentTimeMillis() <= backPressedTime + 1000) {
                finish();
            }
        }

    }

    public void setHomeItem() {
        bottomNavigationView.setSelectedItemId(R.id.homeFragment);
    }
}