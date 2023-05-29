package com.example.capstoneproject.data.match.response;

import com.google.gson.annotations.SerializedName;

public class GetAllMatchCountResult {
    @SerializedName(value = "count") private Integer count;

    public GetAllMatchCountResult(Integer count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
