package com.viettel.smsbrandname.service.dto;

import java.util.List;

public class DiscountProvinceActionDTO {

    Long discountId;
    List<Long> newListSave;
    List<Long> newListDelete;

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public List<Long> getNewListSave() {
        return newListSave;
    }

    public void setNewListSave(List<Long> newListSave) {
        this.newListSave = newListSave;
    }

    public List<Long> getNewListDelete() {
        return newListDelete;
    }

    public void setNewListDelete(List<Long> newListDelete) {
        this.newListDelete = newListDelete;
    }
}
