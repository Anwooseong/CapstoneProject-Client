package com.uou.capstoneproject.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.uou.capstoneproject.R;
import com.uou.capstoneproject.async.AuthRetrofitInterface;
import com.uou.capstoneproject.async.PostAuthEmailBeforeReq;
import com.uou.capstoneproject.data.NetworkModule;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignUpActivity extends AppCompatActivity{
    AuthRetrofitInterface authService;
    private EditText emailEditText, emailValidationEditText; // 이메일 입력, 이메일 인증번호 입력
    private AppCompatButton validationNumberSendBtn; // 인증코드 전송
    private AppCompatButton validationNumberCheckBtn; // 인증코드 확인 체크
    private ConstraintLayout emailValidationLayout; // 인증번호 입력
    private EditText passwordEditText, passwordAgainEditText; // 비밀번호 입력, 재입력
    private EditText nameEditText, nickNameEditText; // 이름 입력, 닉네임 입력


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
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onResume() {
        super.onResume();
        // 1. 인증코드전송 버튼 누름
        validationNumberSendBtn.setOnClickListener(v ->{
            // 2. 인증번호 전송 api 호출 (비동기)
            authService.postEmailAuthNumber(getRequest())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            response -> {
                                try {
                                    if(response.getCode() == 200){
                                        Log.d("Response", response.getResult().getAuthEmailCheck());
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
        });

        // 3. 인증번호 적는 EditText 보이도록
        // 4. 인증번호 확인버튼 눌렀을때 체킹하는 api 호출 (비동기)
    }

    private PostAuthEmailBeforeReq getRequest(){
        return new PostAuthEmailBeforeReq(emailEditText.getText().toString());
    }
}
