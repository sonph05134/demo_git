package com.viettel.smsbrandname.service.impl;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.service.AdminMsisdnService;
import com.viettel.smsbrandname.domain.AdminMsisdn;
import com.viettel.smsbrandname.repository.AdminMsisdnRepository;
import com.viettel.smsbrandname.service.dto.AdminMsisdnDTO;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import com.viettel.smsbrandname.service.mapper.AdminMsisdnMapper;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AdminMsisdn}.
 */
@Service
@Transactional
public class AdminMsisdnServiceImpl implements AdminMsisdnService {

    private final Logger log = LoggerFactory.getLogger(AdminMsisdnServiceImpl.class);

    private final AdminMsisdnRepository adminMsisdnRepository;

    private final AdminMsisdnMapper adminMsisdnMapper;
	// Start 01-04-2021
    @Autowired
    private AdminMsisdnService adminMsisdnService;
 	// End 01-04-2021
    public AdminMsisdnServiceImpl(AdminMsisdnRepository adminMsisdnRepository, AdminMsisdnMapper adminMsisdnMapper) {
        this.adminMsisdnRepository = adminMsisdnRepository;
        this.adminMsisdnMapper = adminMsisdnMapper;
    }

    @Override
    // Start 01-04-2021
    @Transactional(rollbackFor = Exception.class)
    // End 01-04-2021
    public AdminMsisdnDTO save(AdminMsisdnDTO adminMsisdnDTO){
        try {
            log.debug("Request to save AdminMsisdn : {}", adminMsisdnDTO);
            // Start 01-04-2021
            validateInput(adminMsisdnDTO);
            adminMsisdnDTO.setStatus(DataUtil.isNullOrEmpty(adminMsisdnDTO.getStatus()) ? Constants.STATUS_ACTIVE : adminMsisdnDTO.getStatus());
            adminMsisdnDTO.setInsertTime(adminMsisdnDTO.getInsertTime() != null ? adminMsisdnDTO.getInsertTime() : new Date().toInstant());
            AdminMsisdn adminMsisdn = adminMsisdnRepository.save(adminMsisdnMapper.toEntity(adminMsisdnDTO));
            return adminMsisdnMapper.toDto(adminMsisdn);
        }catch (Exception e){
            throw e;
        }
        // End 01-04-2021
    }

    @Override
    public CommonResponseDTO findAll(String userName, String msisdn, Pageable pageable) {
        log.debug("Request to get all AdminMsisdns");
        return adminMsisdnRepository.search(userName, msisdn, pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AdminMsisdnDTO> findOne(Long id) {
        log.debug("Request to get AdminMsisdn : {}", id);
        return adminMsisdnRepository.findById(id)
            .map(adminMsisdnMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        try{
            log.debug("Request to delete AdminMsisdn : {}", id);
            adminMsisdnRepository.deleteById(id);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<String> getLstPrefix() {
        return adminMsisdnRepository.getLstPrefix();
    }

    @Override
    public Boolean checkExisted(String userName, Long id) {
        return adminMsisdnRepository.checkExisted(userName, id);
    }

    // Start 01-04-2021
    private void validateInput(AdminMsisdnDTO adminMsisdnDTO) throws BusinessException {
        List<String> lstPrefix = adminMsisdnService.getLstPrefix();
        if (!DataUtil.isValidMsisdn(adminMsisdnDTO.getMsisdn(), lstPrefix)) {
            throw new BusinessException("msisdnInvalid","isdn-management.msisdn.invalid");
        }
        if(DataUtil.isNullOrEmpty(adminMsisdnDTO.getUserName())){
            throw new BusinessException("nullpackageName","isdn-management.null.package.name");
        }
        Boolean isExisted = adminMsisdnService.checkExisted(adminMsisdnDTO.getUserName(), adminMsisdnDTO.getId());
        if (isExisted == true) {
            throw new BusinessException("existsData","isdn-management.exists.data");
        }
    }
    // End 01-04-2021
}
