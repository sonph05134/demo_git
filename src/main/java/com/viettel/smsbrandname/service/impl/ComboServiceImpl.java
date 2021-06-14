package com.viettel.smsbrandname.service.impl;

import com.viettel.smsbrandname.repository.ComboRepository;
import com.viettel.smsbrandname.service.ComboService;
import com.viettel.smsbrandname.service.dto.ComboBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComboServiceImpl implements ComboService {

    @Autowired
    private ComboRepository comboRepository;

    @Override
    public List<ComboBean> getListPriceLevel() {
        return comboRepository.getListPriceLevel();
    }

    @Override
    public List<ComboBean> getSMSC() {
        return comboRepository.getSMSC();
    }

    @Override
    public List<ComboBean> getGateway() {
        return comboRepository.getGateway();
    }
}
