package com.viettel.smsbrandname.service.dto;

import java.util.List;

public class CommonResponseDTO {
    List<? extends Object> listData;
    Integer count;

    public List<? extends Object> getListData() {
        return listData;
    }

    public void setListData(List<? extends Object> listData) {
        this.listData = listData;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CommonResponseDTO{" +
            "listData=" + listData +
            ", count=" + count +
            '}';
    }
}
