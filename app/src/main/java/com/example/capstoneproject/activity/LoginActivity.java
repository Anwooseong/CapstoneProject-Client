package com.example.capstoneproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.capstoneproject.R;
import com.example.capstoneproject.common.SharedPreferencesManager;
import com.example.capstoneproject.data.auth.AuthService;
import com.example.capstoneproject.data.auth.request.User;
import com.example.capstoneproject.data.auth.response.login.LoginResult;
import com.example.capstoneproject.view.LoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;


public class LoginActivity extends AppCompatActivity implements LoginView {

    private TextInputEditText loginId;
    private TextInputEditText loginPassword;
    private AppCompatButton loginButton, createButton;
    private CheckBox loginCb;
    private String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w("token fail", "onComplete: " + task.getException());
                    return;
                }

                //토큰 조회 성공
                token = task.getResult();
            }
        });
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
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                Log.d("회원가입 버튼", "onClick: 클릭되었습니다.");
            }
        });

    }

    private void login() {
        if (loginId.getText().toString().isEmpty()) {
            Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (loginPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        AuthService authService = new AuthService();
        authService.setLoginView(this);
        authService.login(loginReq());
    }

    private User loginReq() {
        String uid = loginId.getText().toString();
        String password = loginPassword.getText().toString();
        Log.d("TAG", "토큰값: "+token);
        return new User(uid, password, token);
    }

    // 뷰 초기화
    private void init() {
        loginId = findViewById(R.id.login_id_etv);
        loginPassword = findViewById(R.id.login_pw_etv);
        loginButton = findViewById(R.id.login_btn);
        createButton = findViewById(R.id.login_create_btn);
        loginCb = findViewById(R.id.login_cb);
    }
    private void saveDataInShared(String jwt,int userIdx, String name,String nickname){
        final SharedPreferences spf = getSharedPreferences("auth",MODE_PRIVATE);
        final SharedPreferences.Editor editor = spf.edit();
        editor.putString("jwt",jwt);
        editor.putInt("userIdx", userIdx);
        editor.putString("name",name);
        editor.putString("nickName",nickname);
        editor.apply();
    }

    private int getUserIdx(){
        SharedPreferences spf = this.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE);
        return spf.getInt("userIdx",0);
    }

    @Override
    public void onLoginSuccess(int code, LoginResult result) {
        if (code == 1000) {
            if (loginCb.isChecked()) {
                SharedPreferencesManager.setLoginInfo(this, result.getJwt()); // 로그인 정보 로컬 저장소에 저장
            }
            Log.d("userIdx1", ""+result.getUserIdx());
            Log.d("userIdx2", ""+getUserIdx());
            if(result.getUserIdx() == 26){
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(intent);
            }else{
                saveDataInShared(result.getJwt(), result.getUserIdx(), result.getName(),result.getNickName());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            finish();

        }
    }

    @Override
    public void onLoginFailure() {
        Toast.makeText(this, "입력값을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
    }

    //뒤로 2번 눌러 종료
    private long backPressedTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() <= backPressedTime + 2000) {
            finish();
        }
    }

    private String getJwt(){
        SharedPreferences spf = this.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE);
        return spf.getString("jwt","");
    }

    private String getNickname(){
        SharedPreferences spf = this.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE);
        return spf.getString("nickname","");
    }

}
