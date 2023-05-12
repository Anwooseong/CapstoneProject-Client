package com.example.capstoneproject.view;


import com.example.capstoneproject.data.match.response.GetAllOfflineMatchCountResponse;

public interface GetAllOfflineMatchCountView {

    void onGetAllOfflineMatchCountSuccess(GetAllOfflineMatchCountResponse response);
    void onGetAllOfflineMatchCountFailure(GetAllOfflineMatchCountResponse response);
}
