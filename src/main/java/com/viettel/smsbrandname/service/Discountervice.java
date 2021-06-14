package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.domain.Discount;
import com.viettel.smsbrandname.domain.MaintainFee;
import com.viettel.smsbrandname.service.dto.DiscountDTO;
import com.viettel.smsbrandname.service.dto.MaintainFeeDTO;
import org.springframework.data.domain.Page;

public interface Discountervice {

    Page<Discount> search(DiscountDTO discount);

    void save(Discount discount);

    void delete(Long id);
}
