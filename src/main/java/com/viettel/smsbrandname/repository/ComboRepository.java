package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.service.dto.ComboBean;

import java.util.List;

public interface ComboRepository {

    List<ComboBean> getLstProvinceBccs(String userName);

    List<ComboBean> getListPriceLevel();

    List<ComboBean> getSMSC();

    List<ComboBean> getGateway();
}
