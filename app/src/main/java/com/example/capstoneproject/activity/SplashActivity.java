package com.example.capstoneproject.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.capstoneproject.R;
import com.example.capstoneproject.common.SharedPreferencesManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        boolean coarseLocationGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;

        boolean fineLocationGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;

        //자동로그인 등록X
        if (SharedPreferencesManager.getLoginJwtInfo(this).get("jwt").equals("")) {

            //위치 권한 X(위치 권한 버튼 보이게)
            if (!coarseLocationGranted && !fineLocationGranted) {
                moveLoginAndLocation(1);
            }
            //위치 권한 O(위치 권한 버튼 안보이게)
            else {
                moveLogin(1);
            }
        }
        //자동로그인 등록
        else {
            //위치 권한 X(위치 권한 버튼 보이게)
            if (!coarseLocationGranted && !fineLocationGranted) {
                moveLoginAndLocation(1);
            }
            //위치 권한 O(메인화면으로)
            else {
                moveMain(1);
            }
        }
    }

    private void moveLogin(int sec) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("permission", true);
                startActivity(intent);
                finish();
            }
        }, 1000L * sec);
    }

    private void moveLoginAndLocation(int sec) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("permission", false);
                startActivity(intent);
                finish();
            }
        }, 1000L * sec);
    }

    private void moveMain(int sec) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000L * sec);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
