package com.viettel.smsbrandname.service.dto.response;


import java.util.List;

public class CareProgResponseDTO {
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
        return "CPProgResponseDTO{" +
            "listData=" + listData +
            ", count=" + count +
            '}';
    }
}

