package com.viettel.smsbrandname.service.impl;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.domain.Partner;
import com.viettel.smsbrandname.repository.PartnerRepository;
import com.viettel.smsbrandname.service.PartnerService;
import com.viettel.smsbrandname.service.dto.PageDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PartnerServiceImpl extends StandardizeLogging implements PartnerService {

    @Autowired
    private PartnerRepository partnerRepository;

    public PartnerServiceImpl() {
        super(PartnerServiceImpl.class);
    }

    @Override
    public Page<Partner> search(PageDTO page) {
        return partnerRepository.findAll(PageRequest.of(page.getCurrentPage(), page.getPageSize(), Sort.by("code").ascending()));
    }

    @Override
    public void save(Partner partner) {
        Date date = new Date();
        boolean isUpdate;
        if (DataUtil.isNullOrEmpty(partner.getId())) {
            isUpdate = false;
        } else {
            isUpdate = true;
        }
        DataUtil.trim(partner);
        if(!matches(partner.getCode())) {
            throw new BusinessException(ErrorCodeResponse.SPECIAL_CHARACTERS, Translator.toLocale("partner.code"));
        }

        if(codeExisted(partner)) {
            throw new BusinessException(ErrorCodeResponse.ERR_EXIST, Translator.toLocale("partner.code"));
        }
        if(nameExisted(partner)) {
            throw new BusinessException(ErrorCodeResponse.ERR_NAME_EXIST, Translator.toLocale("partner.name"));
        }
        try {
            partnerRepository.save(partner);
        } catch (Exception e) {
            if (!isUpdate) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "insertFail", date);
            } else {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "updateFail", date);
            }
            throw new BusinessException(ErrorCodeResponse.SAVE_FAIL);
        }
    }

    private boolean codeExisted(Partner partner) {
        if(partner.getId() == null) {
            return partnerRepository.getByCodeIgnoreCase(partner.getCode()).isPresent();
        }
        return partnerRepository.getByCodeIgnoreCaseAndIdNot(partner.getCode(), partner.getId()).isPresent();
    }

    private boolean nameExisted(Partner partner) {
        if(partner.getId() == null) {
            return partnerRepository.getByNameIgnoreCase(partner.getName()).isPresent();
        }
        return partnerRepository.getByNameIgnoreCaseAndIdNot(partner.getName(), partner.getId()).isPresent();
    }

    private boolean matches(String value) {
        return value.matches("^[ A-Z 0-9_]*$");
    }

    @Override
    public void delete(Long id) {
        Date date = new Date();
        try {
            partnerRepository.deleteById(id);
        } catch (Exception e) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "deleteFail", date);
            throw new BusinessException(ErrorCodeResponse.DELETE_FAIL);
        }
    }
}
