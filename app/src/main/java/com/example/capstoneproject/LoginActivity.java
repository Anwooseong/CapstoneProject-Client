package com.example.capstoneproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;


public class LoginActivity extends AppCompatActivity{

    private TextInputEditText loginId;
    private TextInputEditText loginPassword;
    private AppCompatButton loginButton, createButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 로그인 버튼 -> 메인 액티비티 이동
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        // 회원가입 버튼 -> 회원가입 액티비티 이동
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, FirstSignUpActivity.class);
                startActivity(intent);
                Log.d("회원가입 버튼", "onClick: 클릭되었습니다.");
            }
        });
    }

    private void login() {
        if(loginId.getText().toString().isEmpty()){
            Toast.makeText(this,"아이디를 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        if (loginPassword.getText().toString().isEmpty()){
            Toast.makeText(this,"비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    // 뷰 초기화
    private void init(){
        loginId = findViewById(R.id.login_id_etv);
        loginPassword = findViewById(R.id.login_pw_etv);
        loginButton = findViewById(R.id.login_btn);
        createButton = findViewById(R.id.login_create_btn);
    }


    //뒤로 2번 눌러 종료
    private long backpressedTime = 0;
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backpressedTime + 2000) {
            backpressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() <= backpressedTime + 2000) {
            finish();
        }
    }
}
