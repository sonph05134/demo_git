package com.viettel.smsbrandname.repository;

import java.util.List;

public interface DiscountProvinceRepositoryCustom {

    List<Object[]> getDiscountByType(Integer type);

    List<Object[]> getProvince();

    List<Object[]> getProvinceByDiscountId(Long discountId);

    void deleteData(List<Long> newListDelete, Long discountId);

    void insertData(Long provinceId, Long discountId);

}
