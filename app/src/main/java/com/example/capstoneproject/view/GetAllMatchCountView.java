package com.example.capstoneproject.view;


import com.example.capstoneproject.data.match.response.GetAllMatchCountResponse;

public interface GetAllMatchCountView {
    void onGetAllMatchCountSuccess(GetAllMatchCountResponse response);
    void onGetAllMatchCountFailure(GetAllMatchCountResponse response);
}
