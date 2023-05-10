package com.example.capstoneproject.view;


import com.example.capstoneproject.data.match.response.GetAllOnlineMatchCountResponse;

public interface GetAllOnlineMatchCountView {

    void onGetAllMatchCountSuccess(GetAllOnlineMatchCountResponse response);
    void onGetAllMatchCountFailure(GetAllOnlineMatchCountResponse response);
}
