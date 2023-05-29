package com.example.capstoneproject.data.match.response;

import com.google.gson.annotations.SerializedName;

public class GetAllOfflineMatchCountResult {
    @SerializedName(value = "count") private Integer count;

    public GetAllOfflineMatchCountResult(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
