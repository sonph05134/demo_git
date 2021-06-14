package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.PriceLevel;

import java.util.List;

public interface PriceLevelRepositoryCustom {

    List<Object[]> onSearch(Long numberSms, Integer start, Integer limit);

    boolean checkDuplicate(PriceLevel priceLevel);

}
