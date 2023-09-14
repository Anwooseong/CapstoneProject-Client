package com.uou.capstoneproject.async;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthRetrofitInterface {
    @POST("users/auth/email")
    Single<EmailSendResponse> postEmailAuthNumber(@Body PostAuthEmailBeforeReq postAuthEmailBeforeReq);
}
