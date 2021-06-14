package com.viettel.smsbrandname.repository;

import java.math.BigDecimal;

public interface MaintainFeeCustomRepository {

    boolean maintainFeeExist(Long id, BigDecimal feeValue, Integer currency);
}
