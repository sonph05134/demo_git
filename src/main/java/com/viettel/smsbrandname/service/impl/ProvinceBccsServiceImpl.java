package com.viettel.smsbrandname.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.MaperUtils;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.ProvinceBccs;
import com.viettel.smsbrandname.repository.ProvinceBccsRepository;
import com.viettel.smsbrandname.repository.ProvinceRepository;
import com.viettel.smsbrandname.service.ProvinceBccsService;
import com.viettel.smsbrandname.service.dto.province.ProvinceBccsDTO;
import com.viettel.smsbrandname.service.dto.response.ApiResponseDTO;
import com.viettel.smsbrandname.service.dto.response.ComboboxResponseDTO;
import com.viettel.smsbrandname.service.mapper.ProvinceBccsMapper;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProvinceBccs}.
 */
@Service
@Transactional
public class ProvinceBccsServiceImpl extends StandardizeLogging implements ProvinceBccsService {

    private final Logger log = LoggerFactory.getLogger(ProvinceBccsServiceImpl.class);

    private final ProvinceBccsRepository provinceBccsRepository;

    private final ProvinceRepository provinceRepository;

    private final ProvinceBccsMapper provinceBccsMapper;

    public ProvinceBccsServiceImpl(ProvinceBccsRepository provinceBccsRepository,
                                   ProvinceBccsMapper provinceBccsMapper,
                                   ProvinceRepository provinceRepository) {
        super(ProvinceBccsServiceImpl.class);
        this.provinceBccsRepository = provinceBccsRepository;
        this.provinceBccsMapper = provinceBccsMapper;
        this.provinceRepository = provinceRepository;
    }

    @Override
    public com.viettel.smsbrandname.service.dto.ProvinceBccsDTO save(com.viettel.smsbrandname.service.dto.ProvinceBccsDTO provinceBccsDTO) {
        log.debug("Request to save ProvinceBccs : {}", provinceBccsDTO);
        ProvinceBccs provinceBccs = provinceBccsMapper.toEntity(provinceBccsDTO);
        provinceBccs = provinceBccsRepository.save(provinceBccs);
        return provinceBccsMapper.toDto(provinceBccs);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<com.viettel.smsbrandname.service.dto.ProvinceBccsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProvinceBccs");
        return provinceBccsRepository.findAll(pageable)
            .map(provinceBccsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<com.viettel.smsbrandname.service.dto.ProvinceBccsDTO> findAll() {
        log.debug("Request to get all ProvinceBccs");
        return provinceBccsRepository.findAll().stream().map(provinceBccsMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<com.viettel.smsbrandname.service.dto.ProvinceBccsDTO> findOne(Long id) {
        log.debug("Request to get ProvinceBccs : {}", id);
        return provinceBccsRepository.findById(id)
            .map(provinceBccsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProvinceBccs : {}", id);
        provinceBccsRepository.deleteById(id);
    }

    @Override
    public ApiResponseDTO onSearch(Long provinceId, Integer start, Integer limit, Double timeZone) {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
        List<Object[]> list = provinceBccsRepository.onSearch(provinceId, start, limit, timeZone);
        int total = provinceBccsRepository.onSearch(provinceId, 0, 0, timeZone).size();
        apiResponseDTO.setData(ProvinceBccsDTO.toDto(list));
        apiResponseDTO.setTotalRow(total);
        return apiResponseDTO;
    }

    @Override
    public List<ComboboxResponseDTO> getLstProvinceNotInProvinceBccs() {
        return new MaperUtils().convertToListCombobox(provinceBccsRepository.getLstProvinceNotInProvinceBccs());
    }

    @Override
    @Transactional
    public void saveOrEdit(ProvinceBccs provinceBCCS, String username) {
        Date date = new Date();
        boolean isUpdate;
        if (DataUtil.isNullOrEmpty(provinceBCCS.getProvinceId())) {
            isUpdate = false;
        } else {
            isUpdate = true;
        }
        if (provinceBccsRepository.isExistsDataProvinceBccs(provinceBCCS)) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "isExistDataOnBCCS", date);
            throw new BusinessException(ErrorCodeResponse.ERR_EXIST, Translator.toLocale("province.bccs"));
        }

        if (provinceBCCS.getProvinceId() != null && !provinceBccsRepository.isExistProvinceSMS(provinceBCCS.getProvinceId())) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "provinceSmsNotExist", date);
            throw new BusinessException(ErrorCodeResponse.NOT_EXIST, Translator.toLocale("province.sms"));
        }

        if (provinceBCCS.getProvinceId() != null && provinceBccsRepository.isMappedDataProvinceBccs(provinceBCCS.getProvinceId(), provinceBCCS.getId())) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "isMappedData", date);
            throw new BusinessException(ErrorCodeResponse.PROVINCE_IS_MAPPED);
        }

        provinceBCCS.setDateUpdated(Instant.now());
        provinceBCCS.setUserUpdated(username);
        try {
            provinceBccsRepository.save(provinceBCCS);
        }
        catch (Exception e) {
            if (!isUpdate) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "insertFail", date);
            } else {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "updateFail", date);
            }
            throw new BusinessException(ErrorCodeResponse.SAVE_FAIL);
        }
    }

    @Override
    @Transactional
    public void deleteBccs(ProvinceBccs provinceBCCS, String username) {
        Date date = new Date();

        if (!provinceRepository.isExitDataBCCS(provinceBCCS.getId())) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "deleteFail", date);
            throw new BusinessException(ErrorCodeResponse.DELETE_FAIL);
        }

        String json = "";
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            json = ow.writeValueAsString(provinceBCCS);
            provinceRepository.insertHistoryTable("PROVINCE", provinceBCCS.getId().toString(), username, json, Constants.DELETE);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw new BusinessException(ErrorCodeResponse.UNKNOWN);
        }

        try {
            provinceBccsRepository.deleteById(provinceBCCS.getId());
        } catch (Exception e) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "deleteFail", date);
            throw new BusinessException(ErrorCodeResponse.DELETE_FAIL);
        }
//        if (!provinceBccsRepository.deleteProvinceBccs(provinceBCCS.getId())) {
//            throw new BusinessException(ErrorCodeResponse.EXISTS_PROVINCE_NOT_IN_PROVINCE_BCCS);
//        }
    }
}
