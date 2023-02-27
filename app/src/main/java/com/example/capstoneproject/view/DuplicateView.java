package com.example.capstoneproject.view;

import com.example.capstoneproject.data.auth.response.duplicateuid.DuplicateResponse;

public interface DuplicateView {
    void onCheckedSuccess();

    void onCheckedFailure(DuplicateResponse resp);
}
