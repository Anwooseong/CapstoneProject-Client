package com.example.capstoneproject.data.users;

import com.example.capstoneproject.data.NetworkModule;
import com.example.capstoneproject.data.users.response.push.GetPushListResponse;
import com.example.capstoneproject.data.users.response.record.GetRecordResponse;
import com.example.capstoneproject.view.GetPushListView;
import com.example.capstoneproject.view.GetUserRecordView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {
    private final UserRetrofitInterface userRetrofitInterface = NetworkModule.getRetrofit().create(UserRetrofitInterface.class);
    private GetUserRecordView userRecordView;
    private GetPushListView pushListView;

    public void setUserRecordView(GetUserRecordView userRecordView) {
        this.userRecordView = userRecordView;
    }

    public void setPushListView(GetPushListView pushListView) {
        this.pushListView = pushListView;
    }

    //GET RECORD
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
}
