package com.example.capstoneproject.data.auth;

import static com.example.capstoneproject.data.NetworkModule.getRetrofit;

import android.util.Log;

import com.example.capstoneproject.data.auth.request.User;
import com.example.capstoneproject.data.auth.response.AuthResponse;
import com.example.capstoneproject.data.auth.response.DuplicateResponse;
import com.example.capstoneproject.view.DuplicateView;
import com.example.capstoneproject.view.LoginView;
import com.example.capstoneproject.view.SignUpView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthService {
    private SignUpView signUpView;
    private LoginView loginView;
    private DuplicateView duplicateView;

    public void setSignUpView(SignUpView signUpView){
        this.signUpView=signUpView;
    }
    public void setLoginView(LoginView loginView){
        this.loginView=loginView;
    }

    public void setDuplicateView(DuplicateView duplicateView) {
        this.duplicateView = duplicateView;
    }

    public void signUp(User user){
        AuthRetrofitInterface authService = getRetrofit().create(AuthRetrofitInterface.class);
        authService.signUp(user).enqueue(new Callback<AuthResponse>() {
            @Override // 응답이 왔을 때
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                AuthResponse resp =  response.body();
                assert resp != null;
                if(resp.getCode() == 1000){
                    signUpView.onSignUpSuccess();
                }else{
                    signUpView.onSignUpFailure(resp);
                }
            }

            @Override // 네트워크 연결 실패 시
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: 회원가입 통신 실패");
            }
        });
    }

    public void duplicate(User user){
        AuthRetrofitInterface authService = getRetrofit().create(AuthRetrofitInterface.class);
        authService.duplicate(user).enqueue(new Callback<DuplicateResponse>() {
            @Override
            public void onResponse(Call<DuplicateResponse> call, Response<DuplicateResponse> response) {
                DuplicateResponse resp = response.body();
                assert resp != null;
                if(resp.getResult().getIsChecked() == 1){
                    duplicateView.onCheckedSuccess();
                }else{
                    duplicateView.onCheckedFailure(resp);
                }
            }

            @Override
            public void onFailure(Call<DuplicateResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: 중복확인 통신 실패");
            }
        });
    }

    public void login(User user){
        AuthRetrofitInterface authService = getRetrofit().create(AuthRetrofitInterface.class);
        authService.login(user).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                AuthResponse resp = response.body();
                assert resp != null;
                if(resp.getCode() == 1000){
                    loginView.onLoginSuccess(resp.getCode(), resp.getResult());
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: 로그인 통신 실패");
            }
        });
    }
}
