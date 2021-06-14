package com.viettel.smsbrandname.service.impl;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.service.AdCategoryService;
import com.viettel.smsbrandname.domain.AdCategory;
import com.viettel.smsbrandname.repository.AdCategoryRepository;
import com.viettel.smsbrandname.service.dto.AdCategoryDTO;
import com.viettel.smsbrandname.service.dto.ComboBean;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import com.viettel.smsbrandname.service.mapper.AdCategoryMapper;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AdCategory}.
 */
@Service
@Transactional
public class AdCategoryServiceImpl implements AdCategoryService {

    private final Logger log = LoggerFactory.getLogger(AdCategoryServiceImpl.class);

    private final AdCategoryRepository adCategoryRepository;

    private final AdCategoryMapper adCategoryMapper;

    public AdCategoryServiceImpl(AdCategoryRepository adCategoryRepository, AdCategoryMapper adCategoryMapper) {
        this.adCategoryRepository = adCategoryRepository;
        this.adCategoryMapper = adCategoryMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AdCategoryDTO save(AdCategoryDTO adCategoryDTO) throws BusinessException {
        try{
            if(DataUtil.isNullOrEmpty(adCategoryDTO.getCatCode())){
                throw new BusinessException(Constants.RESULT.ADCATEGORY.CAT_CODE_NULL, "adCategory.catCode.invalid");
            }
            if(DataUtil.isNullOrEmpty(adCategoryDTO.getCatName())){
                throw new BusinessException(Constants.RESULT.ADCATEGORY.CAT_NAME_NULL, "adCategory.catName.invalid");
            }
            if(adCategoryDTO.getCatCode().length() > 100 || adCategoryDTO.getCatName().length() > 100){
                throw new BusinessException(Constants.RESULT.ADCATEGORY.TO_LONG_VALUE, "adCategory.field.tolong");
            }
            Optional<AdCategory> optionalCode = findByCode(adCategoryDTO.getCatCode());
            Optional<AdCategory> optionalName = findByName(adCategoryDTO.getCatName());
            if (optionalCode.isPresent())
                throw new BusinessException(Constants.RESULT.ADCATEGORY.CAT_CODE_EXISTED, "adCategory.catCode.existed");
            if (optionalName.isPresent())
                throw new BusinessException(Constants.RESULT.ADCATEGORY.CAT_NAME_EXISTED, "adCategory.catName.existed");
            log.debug("Request to save AdCategory : {}", adCategoryDTO);
            adCategoryDTO.setRecycle(DataUtil.isNullOrEmpty(adCategoryDTO.getRecycle()) ? Constants.STATUS_ACTIVE : adCategoryDTO.getRecycle());
            AdCategory adCategory = adCategoryRepository.save(adCategoryMapper.toEntity(adCategoryDTO));
            if(!DataUtil.isNullObject(adCategory)){
               return adCategoryMapper.toDto(adCategory);
            }
            return null;
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CommonResponseDTO findAll(String catCode, String catName, Pageable pageable) {
        log.debug("Request to get all AdCategories");
        return adCategoryRepository.search(catCode, catName, pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AdCategoryDTO> findOne(Long id) {
        log.debug("Request to get AdCategory : {}", id);
        return adCategoryRepository.findById(id)
            .map(adCategoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdCategory : {}", id);
        adCategoryRepository.deleteById(id);
    }

    @Override
    public List<ComboBean> findAdCategoryByRecycle() {
        return adCategoryRepository.findAdCategoryByRecycle();
    }

    @Override
    public Optional<AdCategory> findByCode(String code) {
        return adCategoryRepository.findFirstByCatCodeAndRecycle(code, Constants.STATUS_ACTIVE);
    }

    @Override
    public Optional<AdCategory> findByName(String name) {
        return adCategoryRepository.findFirstByCatNameAndRecycle(name, Constants.STATUS_ACTIVE);
    }
}
