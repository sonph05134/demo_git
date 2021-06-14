package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.service.dto.DiscountProvinceActionDTO;
import com.viettel.smsbrandname.service.dto.response.ComboboxResponseDTO;

import java.util.List;

public interface DiscountProvinceService {

    List<ComboboxResponseDTO> getDiscountByType(Integer type);

    List<ComboboxResponseDTO> getProvince();

    List<ComboboxResponseDTO> getProvinceByDiscount(Long discountId);

    void saveAction(DiscountProvinceActionDTO discountProvinceAction);

}
