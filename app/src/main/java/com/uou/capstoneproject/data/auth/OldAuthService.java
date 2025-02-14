package com.uou.capstoneproject.data.auth;

import static com.uou.capstoneproject.data.NetworkModule.getRetrofit;

import com.uou.capstoneproject.data.auth.request.User;
import com.uou.capstoneproject.data.auth.response.login.LoginResponse;
import com.uou.capstoneproject.data.auth.response.duplicateuid.DuplicateResponse;
import com.uou.capstoneproject.data.auth.response.signup.SignUpResponse;
import com.uou.capstoneproject.view.DuplicateView;
import com.uou.capstoneproject.view.LoginView;
import com.uou.capstoneproject.view.SignUpView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OldAuthService {
    private SignUpView signUpView;
    private LoginView loginView;
    private DuplicateView duplicateView;

    public void setSignUpView(SignUpView signUpView) {
        this.signUpView = signUpView;
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    public void setDuplicateView(DuplicateView duplicateView) {
        this.duplicateView = duplicateView;
    }

    /**
     * 회원가입 API 호출
     * @param user
     */
    public void signUp(User user) {
        OldAuthRetrofitInterface authService = getRetrofit().create(OldAuthRetrofitInterface.class);
        authService.signUp(user).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                SignUpResponse resp = response.body();
                assert resp != null;
                if (resp.getCode() == 1000) {
                    signUpView.onSignUpSuccess();
                } else {
                    signUpView.onSignUpFailure(resp);
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {

            }
        });
    }


    /**
     * Id 중복 API
     * @param user
     */
    public void duplicate(User user) {
        OldAuthRetrofitInterface authService = getRetrofit().create(OldAuthRetrofitInterface.class);
        authService.duplicate(user).enqueue(new Callback<DuplicateResponse>() {
            @Override
            public void onResponse(Call<DuplicateResponse> call, Response<DuplicateResponse> response) {
                DuplicateResponse resp = response.body();
                assert resp != null;
                if (resp.getResult().getIsChecked() == 1) {
                    duplicateView.onCheckedSuccess();
                } else {
                    duplicateView.onCheckedFailure(resp);
                }
            }

            @Override
            public void onFailure(Call<DuplicateResponse> call, Throwable t) {
            }
        });
    }

    /**
     * 로그인 API
     * @param user
     */
    public void login(User user) {
        OldAuthRetrofitInterface authService = getRetrofit().create(OldAuthRetrofitInterface.class);
        authService.login(user).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse resp = response.body();
                if(resp == null){
                    loginView.onLoginFailure();
                    return;
                }
                if (resp.getCode() == 1000) {
                    loginView.onLoginSuccess(resp.getCode(), resp.getResult());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
            }
        });
    }
}
