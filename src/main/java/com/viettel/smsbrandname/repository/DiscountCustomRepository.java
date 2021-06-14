package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.Discount;

public interface DiscountCustomRepository {

    boolean discountExist(Discount discount);

    void deleteDiscountProvince(Long id);
}
