package com.viettel.smsbrandname.service.impl;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.service.AdPackageService;
import com.viettel.smsbrandname.domain.AdPackage;
import com.viettel.smsbrandname.repository.AdPackageRepository;
import com.viettel.smsbrandname.service.dto.AdPackageDTO;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import com.viettel.smsbrandname.service.mapper.AdPackageMapper;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import org.apache.http.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AdPackage}.
 */
@Service
@Transactional
public class AdPackageServiceImpl implements AdPackageService {

    private final Logger log = LoggerFactory.getLogger(AdPackageServiceImpl.class);

    private final AdPackageRepository adPackageRepository;

    private final AdPackageMapper adPackageMapper;

    public AdPackageServiceImpl(AdPackageRepository adPackageRepository, AdPackageMapper adPackageMapper) {
        this.adPackageRepository = adPackageRepository;
        this.adPackageMapper = adPackageMapper;
    }

    @Override
    public AdPackageDTO save(AdPackageDTO adPackageDTO) throws BusinessException {
        log.debug("Request to save AdPackage : {}", adPackageDTO);
        if(DataUtil.isNullOrEmpty(adPackageDTO.getPackageName())){
            throw new BusinessException(Constants.RESULT.AD_PACKAGE.PACK_AGE_NAME_NULL,"adPackAge.package.name.invalid");
        }
        if(adPackageDTO.getPackageName().length()>200){
            throw new BusinessException(Constants.RESULT.AD_PACKAGE.TO_LONG_VALUE,"adPackAge.field.tolong");
        }
        if(findByNameAndType(adPackageDTO.getPackageName(),adPackageDTO.getType().toString()).isPresent()){
            throw new BusinessException(Constants.RESULT.AD_PACKAGE.PACK_AGE_NAME_EXISTED,"adPackAge.package.name.existed");
        }
        adPackageDTO.setStatus(DataUtil.isNullOrEmpty(adPackageDTO.getStatus()) ? Constants.STATUS_ACTIVE : adPackageDTO.getStatus());
//        AdPackage adPackage = adPackageMapper.toEntity(adPackageDTO);
//        adPackage = adPackageRepository.save(adPackage);
        AdPackage adPackage = adPackageRepository.save(adPackageMapper.toEntity(adPackageDTO));
        if(DataUtil.isNullObject(adPackage)) return null;
        return adPackageMapper.toDto(adPackage);
    }

    @Override
    @Transactional(readOnly = true)
    public CommonResponseDTO findAll(String packageName, Integer type, Pageable pageable) {
        log.debug("Request to get all AdPackages");
        return adPackageRepository.search(packageName, type, pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AdPackageDTO> findOne(Long id) {
        log.debug("Request to get AdPackage : {}", id);
        return adPackageRepository.findById(id)
            .map(adPackageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdPackage : {}", id);
        adPackageRepository.deleteById(id);
    }

    @Override
    public Optional<AdPackage> findByName(String packageName) {
        return adPackageRepository.findFirstByPackageNameAndStatus(packageName);
    }
    //start sonph 10/04/2021
    @Override
    public Optional<AdPackage> findByNameAndType(String name, String type) {
        return adPackageRepository.findByNameAndType(name,type);
    }

    @Override
    public Integer changeStatus(Long id,Integer status) {
        try {
            if (DataUtil.isNullOrZero(id)) {
                throw new BusinessException(Constants.RESULT.AD_PACKAGE.ID_NULL_OR_ZERO, "adPackAge.id.is.empty");
            }
            if(status != 0 && status != 1){
                throw new BusinessException(Constants.RESULT.AD_PACKAGE.STATUS_INVALID,"adPackAge.status.is.invalid");
            }
            return adPackageRepository.changeStatus(id, status);
        }catch (Exception e){
            throw e;
        }
    }
    //end sonph 10/04/2021
}
