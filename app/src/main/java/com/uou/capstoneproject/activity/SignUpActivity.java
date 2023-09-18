package com.uou.capstoneproject.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.uou.capstoneproject.R;
import com.uou.capstoneproject.async.api.auth.AuthRetrofitInterface;
import com.uou.capstoneproject.async.api.auth.email.request.PostAuthEmailReq;
import com.uou.capstoneproject.async.api.auth.email.request.PostSignUpWithEmailReq;
import com.uou.capstoneproject.data.NetworkModule;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignUpActivity extends AppCompatActivity{
    AuthRetrofitInterface authService;
    String validCode;
    private EditText emailEditText, emailValidationEditText; // 이메일 입력, 이메일 인증번호 입력
    private AppCompatButton validationNumberSendBtn; // 인증코드 전송
    private AppCompatButton validationNumberCheckBtn; // 인증코드 확인 체크
    private ConstraintLayout emailValidationLayout; // 인증번호 입력
    private EditText passwordEditText, passwordAgainEditText; // 비밀번호 입력, 재입력
    private EditText nameEditText, nickNameEditText; // 이름 입력, 닉네임 입력
    private AppCompatButton signUpBtn; // 회원가입 버튼


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        authService = NetworkModule.getRetrofit().create(AuthRetrofitInterface.class);
        initView(); // 뷰 초기화
    }
    private void initView(){
        emailEditText = findViewById(R.id.signup_email_et);
        validationNumberSendBtn = findViewById(R.id.signup_email_validation_btn);
        emailValidationEditText = findViewById(R.id.signup_email_validation_number_et);
        validationNumberCheckBtn = findViewById(R.id.signup_email_validation_number_btn);
        emailValidationLayout = findViewById(R.id.signup_hidden_email_validation_cl);
        passwordEditText = findViewById(R.id.signup_password_et);
        passwordAgainEditText = findViewById(R.id.signup_password_validation_et);
        nameEditText = findViewById(R.id.signup_name_et);
        nickNameEditText = findViewById(R.id.signup_nickname_et);
        signUpBtn = findViewById(R.id.signup_btn);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onResume() {
        super.onResume();

        // 1. 인증코드전송 버튼 누름
        validationNumberSendBtn.setOnClickListener(v ->{
            // 2. 인증번호 전송 api 호출 (비동기)
            sendAuthCode();
            // 3. 인증번호 적는 EditText 보이도록
            emailValidationLayout.setVisibility(View.VISIBLE);
        });
        // 4. 인증번호 확인버튼 눌렀을때 체킹
        validationNumberCheckBtn.setOnClickListener(v ->{
            String userInputCode = emailValidationEditText.getText().toString();
            if(userInputCode.equals(validCode)){
                validationNumberCheckBtn.setClickable(false);
                validationNumberCheckBtn.setText("완료");
                validationNumberCheckBtn.setBackgroundResource(R.drawable.solid_button4);
                Toast.makeText(getApplicationContext(),"인증에 성공했습니다",Toast.LENGTH_SHORT).show();
            }else{
                validCode="";
                validationNumberCheckBtn.setText("재전송");
                Toast.makeText(getApplicationContext(),"인증에 실패했습니다",Toast.LENGTH_SHORT).show();

                // 5. 재전송 버튼 누를 때 인증번호 전송
                sendAuthCode();
            }
        });

        // 회원가입 버튼 누름
        signUpBtn.setOnClickListener(v ->{
            if(!passwordAgainEditText.getText().toString().equals(passwordAgainEditText.getText().toString())){
                Toast.makeText(getApplicationContext(),"비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                return;
            }
            // 회원가입 api 호출 (비동기)
            sendUserInfo();
            // LoginActivity로 이동
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        });

    }
    @SuppressLint("CheckResult")
    private void sendUserInfo() {
        authService.postSignUpWithEmail(getUserInfo())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            try {
                                if(response.getCode() == 200){
                                    assert response.getResult() != null;
                                    Toast.makeText(getApplicationContext(),"회원가입 성공",Toast.LENGTH_SHORT).show();
                                    Log.d("Response", response.getResult().getUserIdx().toString());
                                } else {
                                    Log.e("Response Error", response.getMessage());
                                }
                            } catch (Exception e) {
                                Log.e("Response Error", "An error occurred while processing the response", e);
                            }
                        },
                        throwable -> {
                            // Handle the error
                            throwable.printStackTrace();
                        }
                );
    }

    private PostSignUpWithEmailReq getUserInfo(){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String nickname = nickNameEditText.getText().toString();
        return new PostSignUpWithEmailReq(email,password,name,nickname);
    }

    // 인증번호 재전송 메서드
    @SuppressLint("CheckResult")
    private void sendAuthCode() {
        authService.postEmailAuthNumber(getSendEmailRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            try {
                                if(response.getCode() == 200){
                                    assert response.getResult() != null;
                                    validCode = response.getResult().getAuthCode();
                                    Log.d("Response", response.getResult().getAuthCode());
                                } else {
                                    Log.e("Response Error", response.getMessage());
                                }
                            } catch (Exception e) {
                                Log.e("Response Error", "An error occurred while processing the response", e);
                            }
                        },
                        throwable -> {
                            // Handle the error
                            throwable.printStackTrace();
                        }
                );
    }

    private PostAuthEmailReq getSendEmailRequest(){
        return new PostAuthEmailReq(emailEditText.getText().toString());
    }
}
