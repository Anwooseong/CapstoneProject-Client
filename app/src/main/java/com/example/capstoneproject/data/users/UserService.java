package com.example.capstoneproject.data.users;

import com.example.capstoneproject.data.NetworkModule;
import com.example.capstoneproject.data.match.response.matchroom.PostMatchRoomResponse;
import com.example.capstoneproject.data.users.response.GetRecordResponse;
import com.example.capstoneproject.data.users.response.GetRecordResult;
import com.example.capstoneproject.view.GetUserRecordView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {
    private final UserRetrofitInterface userRetrofitInterface = NetworkModule.getRetrofit().create(UserRetrofitInterface.class);
    private GetUserRecordView userRecordView;

    public void setUserRecordView(GetUserRecordView userRecordView) {
        this.userRecordView = userRecordView;
    }

    //GET
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
}
