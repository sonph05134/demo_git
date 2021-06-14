package com.viettel.smsbrandname.service.impl;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.service.AdJobService;
import com.viettel.smsbrandname.domain.AdJob;
import com.viettel.smsbrandname.repository.AdJobRepository;
import com.viettel.smsbrandname.service.dto.AdJobDTO;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import com.viettel.smsbrandname.service.mapper.AdJobMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AdJob}.
 */
@Service
@Transactional
public class AdJobServiceImpl implements AdJobService {

    private final Logger log = LoggerFactory.getLogger(AdJobServiceImpl.class);

    private final AdJobRepository adJobRepository;

    private final AdJobMapper adJobMapper;

    public AdJobServiceImpl(AdJobRepository adJobRepository, AdJobMapper adJobMapper) {
        this.adJobRepository = adJobRepository;
        this.adJobMapper = adJobMapper;
    }

    @Override
    public AdJobDTO save(AdJobDTO adJobDTO) {
        log.debug("Request to save AdJob : {}", adJobDTO);
        adJobDTO.setStatus(DataUtil.isNullOrEmpty(adJobDTO.getStatus()) ? Constants.STATUS_ACTIVE : adJobDTO.getStatus());
        AdJob adJob = adJobMapper.toEntity(adJobDTO);
        adJob = adJobRepository.save(adJob);
        return adJobMapper.toDto(adJob);
    }

    @Override
    @Transactional(readOnly = true)
    public CommonResponseDTO findAll(String jobName, Pageable pageable) {
        log.debug("Request to get all AdJobs");
        return adJobRepository.search(jobName, pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AdJobDTO> findOne(Long id) {
        log.debug("Request to get AdJob : {}", id);
        return adJobRepository.findById(id)
            .map(adJobMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdJob : {}", id);
        adJobRepository.deleteById(id);
    }

    @Override
    public Optional<AdJob> findByName(String jobName) {
        return adJobRepository.findByName(jobName);
    }
}
