package com.viettel.smsbrandname.service.impl;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.domain.Telco;
import com.viettel.smsbrandname.repository.TelcoRepository;
import com.viettel.smsbrandname.service.TelcoService;
import com.viettel.smsbrandname.service.dto.PageDTO;
import com.viettel.smsbrandname.service.dto.TelcoDTO;
import com.viettel.smsbrandname.service.mapper.TelcoMapper;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TelcoServiceImpl extends StandardizeLogging implements TelcoService {

    private final Logger log = LoggerFactory.getLogger(TelcoServiceImpl.class);

    private final TelcoRepository telcoRepository;

    private final TelcoMapper telcoMapper;

//    private final TelcoSearchRepository telcoSearchRepository;

    public TelcoServiceImpl(TelcoRepository telcoRepository, TelcoMapper telcoMapper) {
        super(TelcoServiceImpl.class);
        this.telcoRepository = telcoRepository;
        this.telcoMapper = telcoMapper;
//        this.telcoSearchRepository = telcoSearchRepository;
    }

    @Override
    public Page<Telco> getAll(PageDTO pageDTO) {
        Pageable page = PageRequest.of(pageDTO.getCurrentPage(), pageDTO.getPageSize(), Sort.by("telcoName").ascending());
        return telcoRepository.findAll(page);
    }

    @Override
    public void save(Telco telco) {
        Date date = new Date();
        boolean isUpdate;
        if (DataUtil.isNullOrEmpty(telco.getId())) {
            isUpdate = false;
        } else {
            isUpdate = true;
        }
        DataUtil.trim(telco);
        existCode(telco);
        existName(telco);
        if (isUpdate) {
            Optional<Telco> telcoOptional = telcoRepository.findById(telco.getId());
            if (telcoOptional.isPresent()) {
                telco.setIsVietnamese(telcoOptional.get().getIsVietnamese());
                telco.setPrefixDetail(telcoOptional.get().getPrefixDetail());
                telco.setCanFreeSms(telcoOptional.get().getCanFreeSms());
                telco.setSendType(telcoOptional.get().getSendType());
                telco.setGroupName(telcoOptional.get().getGroupName());
            }
        }
        try {
            telcoRepository.save(telco);
        } catch (Exception e) {
            if (!isUpdate) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "insertFail", date);
            } else {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "updateFail", date);
            }
            throw new BusinessException(ErrorCodeResponse.SAVE_FAIL);
        }
    }

    private void existCode(Telco telco) {
        Optional<Telco> optional = null;
        if (telco.getId() == null) {
            optional = telcoRepository.getByTelcoCodeIgnoreCase(telco.getTelcoCode());
        } else {
            optional = telcoRepository.getByTelcoCodeIgnoreCaseAndIdNot(telco.getTelcoCode(), telco.getId());
        }
        if (optional.isPresent())
            throw new BusinessException(ErrorCodeResponse.ERR_EXIST, Translator.toLocale("admin.telco.telcoCode"));
    }

    private void existName(Telco telco) {
        Optional<Telco> optional = null;
        if (telco.getId() == null) {
            optional = telcoRepository.getByTelcoNameIgnoreCase(telco.getTelcoName());
        } else {
            optional = telcoRepository.getByTelcoNameIgnoreCaseAndIdNot(telco.getTelcoName(), telco.getId());
        }
        if (optional.isPresent())
            throw new BusinessException(ErrorCodeResponse.ERR_NAME_EXIST, Translator.toLocale("admin.telco.telcoName"));
    }

    @Override
    public TelcoDTO save(TelcoDTO telcoDTO) {
        log.debug("Request to save Telco : {}", telcoDTO);
        Telco telco = telcoMapper.toEntity(telcoDTO);
        telco = telcoRepository.save(telco);
        TelcoDTO result = telcoMapper.toDto(telco);
//        telcoSearchRepository.save(telco);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TelcoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Telcos");
        return telcoRepository.findAll(pageable)
            .map(telcoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TelcoDTO> findOne(Long id) {
        log.debug("Request to get Telco : {}", id);
        return telcoRepository.findById(id)
            .map(telcoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        Date date = new Date();
        log.debug("Request to delete Telco : {}", id);
        try {
            telcoRepository.deleteById(id);
        } catch (Exception e) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "deleteFail", date);
            throw new BusinessException(ErrorCodeResponse.DELETE_FAIL);
        }
//        telcoSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TelcoDTO> search(String query, Pageable pageable) {
//        log.debug("Request to search for a page of Telcos for query {}", query);
//        return telcoSearchRepository.search(queryStringQuery(query), pageable)
//            .map(telcoMapper::toDto);
        return null;
    }

    @Override
    public List<TelcoDTO> searchAll() {
        return telcoRepository.findAll().stream().map(telcoMapper::toDto).collect(Collectors.toList());
    }
}
