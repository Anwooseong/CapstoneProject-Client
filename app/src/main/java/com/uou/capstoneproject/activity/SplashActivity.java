package com.uou.capstoneproject.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.uou.capstoneproject.R;
import com.uou.capstoneproject.common.SharedPreferencesManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        moveLogin(1);
//        // 위치 권한 확인
//        boolean coarseLocationGranted = ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED;
//
//        boolean fineLocationGranted = ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED;

//        //자동로그인 등록X
//        if (SharedPreferencesManager.getLoginJwtInfo(this).get("jwt").equals("")) {
//
//            //위치 권한 X(위치 권한 버튼 표시)
//            if (!coarseLocationGranted && !fineLocationGranted) {
//                moveLoginAndLocation(1);
//            }
//            //위치 권한 O(위치 권한 버튼 숨김)
//            else {
//                moveLogin(1);
//            }
//        }
//        //자동로그인 등록
//        else {
//            //위치 권한 X(위치 권한 버튼 표시)
//            if (!coarseLocationGranted && !fineLocationGranted) {
//                moveLoginAndLocation(1);
//            }
//            //위치 권한 O(메인화면으로)
//            else {
//                moveMain(1);
//            }
//        }
    }


    private void moveLogin(int sec) {

        // 일정 시간 후에 실행되는 코드 블록
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // LoginActivity로 이동하는 인텐트 생성
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                //intent.putExtra("permission", true); // 인텐트에 추가 정보 전달
                startActivity(intent); // LoginActivity 시작
                finish(); // 현재 액티비티 종료
            }
        }, 1000L * sec); // 일정 시간(초 단위) 지연 후 실행
    }

//    private void moveLoginAndLocation(int sec) {
//
//        // 일정 시간 후에 실행되는 코드 블록
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // LoginActivity로 이동하는 인텐트 생성
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                intent.putExtra("permission", false); // 인텐트에 추가 정보 전달
//                startActivity(intent); // LoginActivity 시작
//                finish(); // 현재 액티비티 종료
//            }
//        }, 1000L * sec); // 일정 시간(초 단위) 지연 후 실행
//    }
//
//    private void moveMain(int sec) {
//
//        // 일정 시간 후에 실행되는 코드 블록
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // MainActivity로 이동하는 인텐트 생성
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent); // MainActivity\ 시작
//                finish(); // 현재 액티비티 종료
//            }
//        }, 1000L * sec); // 일정 시간(초 단위) 지연 후 실행
//    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
