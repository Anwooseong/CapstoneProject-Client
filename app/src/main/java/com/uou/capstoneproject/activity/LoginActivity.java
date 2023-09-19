package com.uou.capstoneproject.activity;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;
import com.kakao.sdk.user.model.Profile;
import com.uou.capstoneproject.R;
import com.google.android.material.textfield.TextInputEditText;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;


public class LoginActivity extends AppCompatActivity {
    Function2<OAuthToken, Throwable, Unit> kakaoCallback;
    private TextInputEditText loginEmail;
    private TextInputEditText loginPassword;
    private AppCompatButton loginButton, createButton, locationButton;
    private AppCompatImageView kakaoLoginButton;
    private CheckBox loginCb;
    private String token;
    private boolean locationValidate = false;
    private long backPressedTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        // 카카오가 설치되어 있는지 확인 하는 메서드또한 카카오에서 제공 콜백 객체를 이용함


    }


    @Override
    protected void onResume() {
        super.onResume();
        setKakaoLoginCallback(); // 카카오 로그인 콜백
        setCreateButtonListener(); // 이메일 회원가입 버튼 리스터
        setKakaoLoginButtonListener(); // 카카오 로그인 리스너
    }

    /**
     * 뷰 초기화
     */
    private void init() {
        loginEmail = findViewById(R.id.login_id_etv);
        loginPassword = findViewById(R.id.login_pw_etv);
        loginButton = findViewById(R.id.login_btn);
        createButton = findViewById(R.id.login_create_btn);
        kakaoLoginButton = findViewById(R.id.login_kakao_auth);
        loginCb = findViewById(R.id.login_cb);
    }
    private void setKakaoLoginCallback() {
        kakaoCallback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                // 이때 토큰이 전달이 되면 로그인이 성공한 것이고 토큰이 전달되지 않았다면 로그인 실패
                if (oAuthToken != null) {
                    Log.d("KaKao Token : ", oAuthToken.toString());
                    UserApiClient.getInstance().me((user, error) -> {
                        if (error != null) {
                            Log.e(TAG, "사용자 정보 요청 실패", error);
                        } else if (user != null) {
                            Account account = user.getKakaoAccount();
                            if (account != null) {
                                String email = account.getEmail();
                                Profile profile = account.getProfile();

                                if (email != null) {
                                    Log.i(TAG, "이메일: " + email);
                                } else {
                                    Log.i(TAG, "이메일이 없습니다.");
                                }

                                if (profile != null) {
                                    String nickname = profile.getNickname();
                                    Log.i(TAG, "닉네임: " + nickname);
                                    Log.i(TAG,"프로필사진url: "+profile.getProfileImageUrl());
                                } else {
                                    Log.i(TAG, "프로필 정보가 없습니다.");
                                }

                            } else {
                                Log.i(TAG, "카카오 계정 정보가 없습니다.");
                            }
                        }
                        return null;
                    });
                }
                if (throwable != null) {
                    Log.e("KaKao Error : ", throwable.toString());
                }
                return null;
            }
        };
    }

    private void setCreateButtonListener() {
        createButton.setOnClickListener(v ->{
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void setKakaoLoginButtonListener() {
        kakaoLoginButton.setOnClickListener(v -> {
            if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, kakaoCallback);
            } else {
                UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, kakaoCallback);
            }
        });
    }

    //뒤로 2번 눌러 종료
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() <= backPressedTime + 2000) {
            finish();
        }
    }


}
