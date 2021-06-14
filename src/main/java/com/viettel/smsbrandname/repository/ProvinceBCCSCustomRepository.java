package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.service.dto.ComboBean;

import java.util.List;

public interface ProvinceBCCSCustomRepository {
    List<ComboBean> getLstProvinceBccs(String userName);
}
