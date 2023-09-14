package com.uou.capstoneproject.data.match.response;

import com.google.gson.annotations.SerializedName;

public class GetAllOnlineMatchCountResult {
    @SerializedName(value = "count") private Integer count;

    public GetAllOnlineMatchCountResult(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
