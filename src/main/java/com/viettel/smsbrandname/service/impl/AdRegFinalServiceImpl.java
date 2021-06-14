package com.viettel.smsbrandname.service.impl;

import com.viettel.smsbrandname.service.AdRegFinalService;
import com.viettel.smsbrandname.domain.AdRegFinal;
import com.viettel.smsbrandname.repository.AdRegFinalRepository;
import com.viettel.smsbrandname.service.dto.AdRegFinalDTO;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import com.viettel.smsbrandname.service.mapper.AdRegFinalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AdRegFinal}.
 */
@Service
@Transactional
public class AdRegFinalServiceImpl implements AdRegFinalService {

    private final Logger log = LoggerFactory.getLogger(AdRegFinalServiceImpl.class);

    private final AdRegFinalRepository adRegFinalRepository;

    private final AdRegFinalMapper adRegFinalMapper;

    public AdRegFinalServiceImpl(AdRegFinalRepository adRegFinalRepository, AdRegFinalMapper adRegFinalMapper) {
        this.adRegFinalRepository = adRegFinalRepository;
        this.adRegFinalMapper = adRegFinalMapper;
    }

    @Override
    public AdRegFinalDTO save(AdRegFinalDTO adRegFinalDTO) {
        log.debug("Request to save AdRegFinal : {}", adRegFinalDTO);
        adRegFinalDTO.setInsertTime(adRegFinalDTO.getInsertTime() != null ? adRegFinalDTO.getInsertTime() : new Date().toInstant());
        AdRegFinal adRegFinal = adRegFinalMapper.toEntity(adRegFinalDTO);
        adRegFinal = adRegFinalRepository.save(adRegFinal);
        return adRegFinalMapper.toDto(adRegFinal);
    }

    @Override
    @Transactional(readOnly = true)
    public CommonResponseDTO findAll(Long msisdn, Pageable pageable) {
        log.debug("Request to get all AdRegFinals");
        return adRegFinalRepository.search(msisdn, pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AdRegFinalDTO> findOne(Long id) {
        log.debug("Request to get AdRegFinal : {}", id);
        return adRegFinalRepository.findById(id)
            .map(adRegFinalMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdRegFinal : {}", id);
        adRegFinalRepository.deleteById(id);
    }

    @Override
    public Optional<AdRegFinal> findByMsisdn(Long msisdn) {
        return adRegFinalRepository.findByMsisdn(msisdn);
    }
}
