package com.example.capstoneproject.view;


import com.example.capstoneproject.data.match.response.GetAllOnlineMatchCountResponse;

public interface GetAllOnlineMatchCountView {

    void onGetAllOnlineMatchCountSuccess(GetAllOnlineMatchCountResponse response);
    void onGetAllOnlineMatchCountFailure(GetAllOnlineMatchCountResponse response);
}
