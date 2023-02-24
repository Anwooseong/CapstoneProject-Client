package com.example.capstoneproject.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.capstoneproject.R;
import com.example.capstoneproject.data.auth.AuthService;
import com.example.capstoneproject.data.auth.request.User;
import com.example.capstoneproject.data.auth.response.LoginResponse;
import com.example.capstoneproject.data.auth.response.DuplicateResponse;
import com.example.capstoneproject.data.auth.response.SignUpResponse;
import com.example.capstoneproject.view.DuplicateView;
import com.example.capstoneproject.view.SignUpView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity implements SignUpView, DuplicateView {

    private TextInputLayout signUpIdLayout, signUpPwLayout, signUpCheckPwLayout, signUpNameLayout, signUpNickNameLayout;
    private TextInputEditText signUpId;
    private AppCompatButton checkIdBtn;
    private TextInputEditText signUpPassword, signUpCheckPassword, signUpName, signUpNickName;
    private AppCompatButton signupBtn;
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
                if (userId.equals("")) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                validateAPI(); //아이디 중복 확인 API 통신
            }
        });

        // 회원가입 버튼 -> 성공시 로그인 화면으로 이동
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

    }

    private void validateAPI() {
        AuthService authService = new AuthService();
        authService.setDuplicateView(this);
        authService.duplicate(duplicateUser());
    }

    private User duplicateUser() {
        String id = signUpId.getText().toString();
        return new User(id);
    }

    private void signUp() {
        //아이디 빈 텍스트
        if (signUpId.getText().toString().isEmpty()) {
            Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        //비밀번호 빈 텍스트
        if (signUpPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        //비밀번호 일치하는지 확인
        if (!signUpPassword.getText().toString().equals(signUpCheckPassword.getText().toString())) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        //이름 빈 텍스트
        if (signUpName.getText().toString().isEmpty()) {
            Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        //닉네임 빈 텍스트
        if (signUpNickName.getText().toString().isEmpty()) {
            Toast.makeText(this, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        //중복확인 체크 했는지 확인
        if (!validate) {
            Toast.makeText(this, "아이디 중복확인을 눌러주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        //회원가입 API 호출
        AuthService authService = new AuthService();
        authService.setSignUpView(this);
        authService.signUp(createUser());
        Log.d("회원가입", "회원가입 성공 : 로그인 화면으로 이동");
    }

    private User createUser() {
        String id = signUpId.getText().toString();
        String password = signUpPassword.getText().toString();
        String name = signUpName.getText().toString();
        String nickName = signUpNickName.getText().toString();

        return new User(id, password, name, nickName);
    }

    // 뷰 초기화
    private void init() {
        signUpIdLayout = findViewById(R.id.signup_id_layout);
        signUpPwLayout = findViewById(R.id.signup_pw_layout);
        signUpCheckPwLayout = findViewById(R.id.signup_check_pw_layout);
        signUpNameLayout = findViewById(R.id.signup_name_layout);
        signUpNickNameLayout = findViewById(R.id.signup_nickname_layout);
        signUpId = findViewById(R.id.signup_id_etv);
        checkIdBtn = findViewById(R.id.signup_check_id_btn);
        signUpPassword = findViewById(R.id.signup_pw_etv);
        signUpCheckPassword = findViewById(R.id.signup_check_pw_etv);
        signUpName = findViewById(R.id.signup_name_etv);
        signUpNickName = findViewById(R.id.signup_nickname_etv);
        signupBtn = findViewById(R.id.signup_btn);
    }

    @Override
    public void onSignUpSuccess() {
        finish();
    }

    @Override
    public void onSignUpFailure(SignUpResponse response) {
        switch (response.getCode()) {
            //TODO code 다시 수정해야함 임시로 넣은거
            case 2010:
                signUpIdLayout.setError(response.getMessage());
                signUpIdLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            signUpIdLayout.setError(null);
                        }
                    }
                });
                break;
            case 2011:
                signUpPwLayout.setError(response.getMessage());
                signUpPwLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            signUpPwLayout.setError(null);
                        }
                    }
                });
                signUpCheckPassword.setError(response.getMessage());
                signUpCheckPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            signUpCheckPassword.setError(null);
                        }
                    }
                });
                break;
            case 2019:
                signUpNickNameLayout.setError(response.getMessage());
                signUpNickNameLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            signUpNickNameLayout.setError(null);
                        }
                    }
                });
                break;
            //비밀번호 암호화 실패나 데이터 베이스 연결 실패시 호출
            default:
                Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCheckedSuccess() {
        validate = true;  //중복 확인 완료
        Toast.makeText(this, "사용가능한 아이디입니다.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCheckedFailure(DuplicateResponse resp) {
        validate = false;
        Toast.makeText(this, "아이디가 중복됩니다.", Toast.LENGTH_SHORT).show();
    }
}
