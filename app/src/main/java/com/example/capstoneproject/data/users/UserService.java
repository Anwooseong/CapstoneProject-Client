package com.example.capstoneproject.data.users;

import com.example.capstoneproject.data.NetworkModule;
import com.example.capstoneproject.data.users.response.info.GetSimpleInfoResponse;
import com.example.capstoneproject.data.users.response.info.GetUserInfoResponse;
import com.example.capstoneproject.data.users.response.push.GetPushListResponse;
import com.example.capstoneproject.data.users.response.record.GetRecordResponse;
import com.example.capstoneproject.view.GetPushListView;
import com.example.capstoneproject.view.GetSimpleInfoView;
import com.example.capstoneproject.view.GetUserInfoView;
import com.example.capstoneproject.view.GetUserRecordView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {
    private final UserRetrofitInterface userRetrofitInterface = NetworkModule.getRetrofit().create(UserRetrofitInterface.class);
    private GetUserRecordView userRecordView;
    private GetPushListView pushListView;
    private GetSimpleInfoView simpleInfoView;
    private GetUserInfoView userInfoView;

    public void setSimpleInfoView(GetSimpleInfoView simpleInfoView) {
        this.simpleInfoView = simpleInfoView;
    }

    public void setUserRecordView(GetUserRecordView userRecordView) {
        this.userRecordView = userRecordView;
    }

    public void setPushListView(GetPushListView pushListView) {
        this.pushListView = pushListView;
    }

    public void setUserInfoView(GetUserInfoView userInfoView) {
        this.userInfoView = userInfoView;
    }

    //GET RECORD

    /**
     * 전적 기록 목록 조회 API
     * @param jwt
     */
    public void getUserRecord(String jwt){
        userRetrofitInterface.getRecordRes(jwt).enqueue(new Callback<GetRecordResponse>() {
            @Override
            public void onResponse(Call<GetRecordResponse> call, Response<GetRecordResponse> response) {
                GetRecordResponse resp = response.body();
                assert resp != null;
                if(resp.getCode() == 1000){
                    userRecordView.onGetMatchRoomSuccess(resp.getResult());
                }else {
                    userRecordView.onGetMatchRoomFailure();
                }
            }

            @Override
            public void onFailure(Call<GetRecordResponse> call, Throwable t) {

            }
        });
    }

    //GET PUSHES

    /**
     * 알림 목록 조회 API
     * @param jwt
     */
    public void getPushList(String jwt) {
        userRetrofitInterface.getPushListRes(jwt).enqueue(new Callback<GetPushListResponse>() {
            @Override
            public void onResponse(Call<GetPushListResponse> call, Response<GetPushListResponse> response) {
                GetPushListResponse resp = response.body();
                assert resp != null;
                if (resp.getCode() == 1000) {
                    pushListView.onGetPushListSuccess(resp.getResult());
                }else {
                    pushListView.onGetPushListFailure();
                }
            }

            @Override
            public void onFailure(Call<GetPushListResponse> call, Throwable t) {

            }
        });
    }
    //GET

    /**
     * 간단 내 정보 API
     * @param jwt
     */
    public void getSimpleInfo(String jwt){
        userRetrofitInterface.getSimpleInfoRes(jwt).enqueue(new Callback<GetSimpleInfoResponse>() {
            @Override
            public void onResponse(Call<GetSimpleInfoResponse> call, Response<GetSimpleInfoResponse> response) {
                GetSimpleInfoResponse resp = response.body();
                assert resp != null;
                if (resp.getCode() == 1000){
                    simpleInfoView.onGetSimpleInfoSuccess(resp.getResult());
                }else{
                    simpleInfoView.onGetSimpleInfoFailure();
                }
            }

            @Override
            public void onFailure(Call<GetSimpleInfoResponse> call, Throwable t) {

            }
        });
    }
    //GET

    /**
     * 내 정보 API
     * @param jwt
     */
    public void getUserInfo(String jwt){
        userRetrofitInterface.getUserInfoRes(jwt).enqueue(new Callback<GetUserInfoResponse>() {
            @Override
            public void onResponse(Call<GetUserInfoResponse> call, Response<GetUserInfoResponse> response) {
                GetUserInfoResponse resp = response.body();
                assert resp != null;
                if(resp.getCode() == 1000){
                    userInfoView.onGetUserInfoSuccess(resp.getResult());
                }else{
                    userInfoView.onGetUserInfoFailure();
                }
            }

            @Override
            public void onFailure(Call<GetUserInfoResponse> call, Throwable t) {

            }
        });
    }
}
