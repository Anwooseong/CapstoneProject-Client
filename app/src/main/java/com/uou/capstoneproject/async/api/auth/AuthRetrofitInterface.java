package com.uou.capstoneproject.async.api.auth;

import com.uou.capstoneproject.async.api.auth.email.request.PostSignUpWithEmailReq;
import com.uou.capstoneproject.async.api.auth.email.response.EmailSendResponse;
import com.uou.capstoneproject.async.api.auth.email.request.PostAuthEmailReq;
import com.uou.capstoneproject.async.api.auth.email.response.SignUpWithEmailResponse;
import com.uou.capstoneproject.async.api.auth.social.kakao.request.KakaoLoginReq;
import com.uou.capstoneproject.async.api.auth.social.kakao.response.KakaoLoginResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthRetrofitInterface {
    @POST("users/auth/email")
    Single<EmailSendResponse> postEmailAuthNumber(@Body PostAuthEmailReq postAuthEmailReq);

    @POST("users/auth")
    Single<SignUpWithEmailResponse> postSignUpWithEmail(@Body PostSignUpWithEmailReq postSignUpWithEmailReq);

    @POST("users/auth/kakao")
    Single<KakaoLoginResponse> postKakaoLogin(@Body KakaoLoginReq kakaoLoginReq);
}
