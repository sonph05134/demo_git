package com.viettel.smsbrandname.service.impl;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.MaperUtils;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.repository.DiscountProvinceRepository;
import com.viettel.smsbrandname.service.DiscountProvinceService;
import com.viettel.smsbrandname.service.dto.DiscountProvinceActionDTO;
import com.viettel.smsbrandname.service.dto.response.ComboboxResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DiscountProvinceServiceImpl extends StandardizeLogging implements DiscountProvinceService {

    @Autowired
    DiscountProvinceRepository discountProvinceRepository;

    public DiscountProvinceServiceImpl() {
        super(DiscountProvinceServiceImpl.class);
    }

    @Override
    public List<ComboboxResponseDTO> getDiscountByType(Integer type) {
        return new MaperUtils().convertToListCombobox(discountProvinceRepository.getDiscountByType(type));
    }

    @Override
    public List<ComboboxResponseDTO> getProvince() {
        return new MaperUtils().convertToListCombobox(discountProvinceRepository.getProvince());
    }

    @Override
    public List<ComboboxResponseDTO> getProvinceByDiscount(Long discountId) {
        return new MaperUtils().convertToListCombobox(discountProvinceRepository.getProvinceByDiscountId(discountId));
    }

    @Override
    @Transactional
    public void saveAction(DiscountProvinceActionDTO discountProvinceAction) {
        Date date = new Date();

        if (!discountProvinceAction.getNewListDelete().isEmpty()) {
            discountProvinceRepository.deleteData(discountProvinceAction.getNewListDelete(), discountProvinceAction.getDiscountId());
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "DiscountProvinceDAO_onSaveAction(delete)_success", date);
        }

        List<Long> newListSave = discountProvinceAction.getNewListSave();
        if (!newListSave.isEmpty()) {
            for (Long data : newListSave) {
                discountProvinceRepository.insertData(data, discountProvinceAction.getDiscountId());
            }
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "DiscountProvinceDAO_onSaveAction(add)_success", date);
        }
    }
}
