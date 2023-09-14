package com.uou.capstoneproject.data.auth.response.duplicateuid;

import com.google.gson.annotations.SerializedName;

public class CheckUidResult {
    @SerializedName(value = "checkIdx") private int isChecked;

    public CheckUidResult(int isChecked) {
        this.isChecked = isChecked;
    }

    public int getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }
}
