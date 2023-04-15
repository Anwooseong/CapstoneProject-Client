package com.example.capstoneproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstoneproject.R;
import com.example.capstoneproject.common.SharedPreferencesManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (SharedPreferencesManager.getLoginJwtInfo(this).get("jwt").equals("")) {
            moveLogin(1);
        }else {
            moveMain(1);
        }

    }

    private void moveLogin(int sec) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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
