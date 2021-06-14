package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.service.dto.ComboBean;

import java.util.List;

public interface ComboService {

    List<ComboBean> getListPriceLevel();
    List<ComboBean> getSMSC();
    List<ComboBean> getGateway();
}
