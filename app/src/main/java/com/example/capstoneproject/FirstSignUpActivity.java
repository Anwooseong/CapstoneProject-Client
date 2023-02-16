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
import com.google.android.material.textfield.TextInputLayout;

public class FirstSignUpActivity extends AppCompatActivity {

    private TextInputEditText signUpId;
    private AppCompatButton checkIdBtn;
    private TextInputEditText signUpPassword;
    private TextInputEditText signUpCheckPassword;
    private AppCompatButton secondSignupBtn;
    private boolean validate = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //중복확인 버튼
        checkIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = signUpId.getText().toString();
                if (validate) {
                    return; //검증 완료
                }
                if (userId.equals("")) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                //TODO 검증 단계(api 호출)

                validate = true;
            }
        });
        // 다음 버튼 -> 두번째 회원가입 단계 액티비티 이동
        secondSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondSignup();
            }
        });

    }

    private void secondSignup() {
        if(signUpId.getText().toString().isEmpty()){
            Toast.makeText(this,"아이디를 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        if (signUpPassword.getText().toString().isEmpty()){
            Toast.makeText(this,"비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        //중복확인 체크 했는지 확인
        if (!validate) {
            Toast.makeText(this,"아이디 중복확인을 눌러주세요.",Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO 두번째 회원가입 화면
        //Intent intent = new Intent(getApplicationContext(), SecoundSignUpActivity.class);
        //startActivity(intent);
        //finish();
        Log.d("회원가입", "secondSignup: 다음단계로 이동");
    }

    // 뷰 초기화
    private void init(){
        signUpId = findViewById(R.id.signup_id_etv);
        checkIdBtn = findViewById(R.id.signup_check_id_btn);
        signUpPassword = findViewById(R.id.signup_pw_etv);
        signUpCheckPassword = findViewById(R.id.signup_check_pw_etv);
        secondSignupBtn = findViewById(R.id.signup_next_btn);
    }
}
