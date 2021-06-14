package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.domain.PriceLevel;
import com.viettel.smsbrandname.service.dto.response.ApiResponseDTO;

public interface PriceLevelService {

   boolean checkPriceLevel(Long priceLevelId);
    ApiResponseDTO onSearch(Long numberSms, Integer start, Integer limit);

    void saveOrEdit(PriceLevel priceLevel);

    void delete(Long priceLevelId);
}
