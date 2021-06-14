package com.viettel.smsbrandname.service.impl;

import com.viettel.smsbrandname.service.CpAliasService;
import com.viettel.smsbrandname.domain.CpAlias;
import com.viettel.smsbrandname.repository.CpAliasRepository;
import com.viettel.smsbrandname.service.dto.ComboBean;
import com.viettel.smsbrandname.service.dto.CpAliasDTO;
import com.viettel.smsbrandname.service.mapper.CpAliasMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CpAlias}.
 */
@Primary
@Service
@Transactional
public class CpAliasServiceImpl implements CpAliasService {

    private final Logger log = LoggerFactory.getLogger(CpAliasServiceImpl.class);

    private final CpAliasRepository cpAliasRepository;

    private final CpAliasMapper cpAliasMapper;

    public CpAliasServiceImpl(CpAliasRepository cpAliasRepository, CpAliasMapper cpAliasMapper) {
        this.cpAliasRepository = cpAliasRepository;
        this.cpAliasMapper = cpAliasMapper;
    }

    @Override
    public CpAliasDTO save(CpAliasDTO cpAliasDTO) {
        log.debug("Request to save CpAlias : {}", cpAliasDTO);
        CpAlias cpAlias = cpAliasMapper.toEntity(cpAliasDTO);
        cpAlias = cpAliasRepository.save(cpAlias);
        return cpAliasMapper.toDto(cpAlias);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CpAliasDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CpAliases");
        return cpAliasRepository.findAll(pageable)
            .map(cpAliasMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CpAliasDTO> findOne(Long id) {
        log.debug("Request to get CpAlias : {}", id);
        return cpAliasRepository.findById(id)
            .map(cpAliasMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CpAlias : {}", id);
        cpAliasRepository.deleteById(id);
    }

    @Override
    public List<ComboBean> searchBrandName(Long cpId) {
        List<ComboBean> comboBeans = new ArrayList<>();
        cpAliasRepository.searchBrandName(cpId).stream().forEach(cpAlias -> {
            ComboBean comboBean = new ComboBean();
            comboBean.setValue(cpAlias.getCpAlias());
            comboBean.setLabel(cpAlias.getCpAlias());
            comboBeans.add(comboBean);
        });
        return comboBeans;
    }
}
