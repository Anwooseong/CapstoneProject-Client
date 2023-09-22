package com.uou.capstoneproject.activity;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;
import com.kakao.sdk.user.model.Profile;
import com.navercorp.nid.NaverIdLoginSDK;
import com.navercorp.nid.oauth.NidOAuthLogin;
import com.navercorp.nid.oauth.OAuthLoginCallback;
import com.navercorp.nid.oauth.view.NidOAuthLoginButton;
import com.uou.capstoneproject.R;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    ActivityResultLauncher<Intent> signInLauncher;

    SignInButton googleLoginBtn;
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
        setKakaoLoginCallback(); // 카카오 로그인 콜백
        setGoogleLoginCallback(); // 구글 로그인 콜백
    }


    @Override
    protected void onResume() {
        super.onResume();
        setCreateButtonListener(); // 이메일 회원가입 버튼 리스터
        setKakaoLoginButtonListener(); // 카카오 로그인 리스너
        setGoogleLoginButtonListener(); // 구글 로그인 리스너
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
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this,gso);
        googleLoginBtn = findViewById(R.id.login_google_auth);

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
                            setKakaoRequest(account);
                            setKakaoUserInfoToServer();
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
    private void setKakaoRequest(Account account){
        account.getEmail();
    }
    private void setKakaoUserInfoToServer(){

    }

    private void setCreateButtonListener() {
        createButton.setOnClickListener(v ->{
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    // 카카오 로그인 버튼 눌렀을 때
    private void setKakaoLoginButtonListener() {
        kakaoLoginButton.setOnClickListener(v -> {
            if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, kakaoCallback);
            } else {
                UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, kakaoCallback);
            }
        });
    }
    // 구글 로그인 버튼 눌렀을 때
    private void setGoogleLoginButtonListener(){
        googleLoginBtn.setOnClickListener(v -> {
            signIn();
        });
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        signInLauncher.launch(signInIntent);
    }

    // Google 로그인 인텐트의 결과를 처리
    private void setGoogleLoginCallback(){
        // ActivityResultLauncher를 사용하여 새 결과 API를 설정.
        signInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Intent를 처리하는 로직
                        Log.d("result.getResultCode() = ",String.valueOf(result.getResultCode()));
                        Intent data = result.getData();
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        handleSignInResult(task);
                    }
                }
        );
    }

    // Google 로그인 결과를 처리
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d("account: ",account.toString());
            if(account != null){
                Log.d(TAG, "handleSignInResult:personName=" + account.getDisplayName()); // 성명
                Log.d(TAG, "handleSignInResult:personEmail=" + account.getEmail()); // 이메일
                Log.d(TAG, "handleSignInResult:personPhoto=" + account.getPhotoUrl()); // 프로필

            }
            // checkIfUserIsRegistered(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            // Update UI accordingly
        }
    }

    // Google - 사용자가 시스템에 이미 등록되어 있는지 확인
    private void checkIfUserIsRegistered(GoogleSignInAccount account) {
        // Here, use the information from the account object to check if the user is registered in your system.
        // If registered, navigate them to the main activity.
        // If not registered, navigate them to the sign-up activity.

        String userEmail = account.getEmail();
        if (userEmail != null) {
            // Check if the email is registered in your system
            if (isUserRegistered(userEmail)) {
                // Navigate to the main activity
            } else {
                // Navigate to the sign-up activity
            }
        }
    }

    // Google - 사용자가 이미 회원으로 등록되어 있는지 확인
    private boolean isUserRegistered(String email) {
        // Implement your user registration check logic here
        // For the demonstration, we assume that the user is not registered
        return false;
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
