package com.viettel.smsbrandname.service.impl;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.Cp;
import com.viettel.smsbrandname.domain.ProvinceUsers;
import com.viettel.smsbrandname.repository.CpRepository;
import com.viettel.smsbrandname.repository.ProvinceUserRepository;
import com.viettel.smsbrandname.security.SecurityUtils;
import com.viettel.smsbrandname.service.CpGroupService;
import com.viettel.smsbrandname.domain.CpGroup;
import com.viettel.smsbrandname.repository.CpGroupRepository;
import com.viettel.smsbrandname.service.dto.CpDTO;
import com.viettel.smsbrandname.service.dto.CpGroupDTO;
import com.viettel.smsbrandname.service.dto.CpGroupDetailDTO;
import com.viettel.smsbrandname.service.dto.ProvinceUsersDTO;
import com.viettel.smsbrandname.service.mapper.CpGroupMapper;
import com.viettel.smsbrandname.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service Implementation for managing {@link CpGroup}.
 */
@Service
@Transactional
public class CpGroupServiceImpl implements CpGroupService {

    private final Logger log = LoggerFactory.getLogger(CpGroupServiceImpl.class);

    private final CpGroupRepository cpGroupRepository;

    private final CpGroupMapper cpGroupMapper;

    private final ProvinceUserRepository provinceUserRepository;

    private final CpRepository cpRepository;


    public CpGroupServiceImpl(CpGroupRepository cpGroupRepository, CpGroupMapper cpGroupMapper, ProvinceUserRepository provinceUserRepository, CpRepository cpRepository) {
        this.cpGroupRepository = cpGroupRepository;
        this.cpGroupMapper = cpGroupMapper;
        this.provinceUserRepository = provinceUserRepository;
        this.cpRepository = cpRepository;
    }

    @Override
    public CpGroupDTO save(CpGroupDTO cpGroupDTO) {
        log.debug("Request to save CpGroup : {}", cpGroupDTO);
        List<CpGroup> lstCpGroup = cpGroupRepository.findCpGroupByCpAndName(cpGroupDTO.getCpId(),
            cpGroupDTO.getCpGroupName(), cpGroupDTO.getId());
        if (!DataUtil.isNullOrEmpty(lstCpGroup)) {
            throw new BadRequestAlertException(Translator.toLocale("error.cpGroup.exists"), "cpGroup", "cpGroup.exists");
        }
        Optional<Cp> cp = cpRepository.getByCpId(cpGroupDTO.getCpId());
        if (cp.isPresent()) {
            cpGroupDTO.setCpCode(cp.get().getCpCode());
        } else {
            throw new BadRequestAlertException(Translator.toLocale("error.cpGroup.cpIdNotExisted"), "cpGroup", "cpGroup.cpIdNotExisted");
        }
        if (cpGroupDTO.getId() != null) {
            Optional<CpGroup> cpGroup = cpGroupRepository.findById(cpGroupDTO.getId());
            if (cpGroup.isPresent()) {
                cpGroupDTO.setCreateDate(cpGroup.get().getCreateDate());
            } else {
                throw new BadRequestAlertException(Translator.toLocale("error.haveSomeError"), "cpGroup", "cpGroup.cpGroupNotExisted");
            }
            cpGroupDTO.setUpdateDate(Instant.now());
        } else {
            cpGroupDTO.setCreateDate(Instant.now());
        }
        cpGroupDTO.setDeleted(0);

        CpGroup cpGroup = cpGroupMapper.toEntity(cpGroupDTO);
        cpGroup = cpGroupRepository.save(cpGroup);
        CpGroupDTO result = cpGroupMapper.toDto(cpGroup);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CpGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CpGroups");
        return cpGroupRepository.findAll(pageable)
            .map(cpGroupMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CpGroupDTO> findOne(Long id) {
        log.debug("Request to get CpGroup : {}", id);
        return cpGroupRepository.findById(id)
            .map(cpGroupMapper::toDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id, Long cpId) {
        log.debug("Request to delete CpGroup : {}", id);
        cpGroupRepository.findById(id).orElseThrow(() -> new BadRequestAlertException(Translator.toLocale("error.haveSomeError"), "cpGroup", "cpGroup.cpGroupNotExisted"));
        cpGroupRepository.updateCpGroupSubLimit(cpId, id);
        cpGroupRepository.deleteCpGroupSubLimit(cpId);
        cpGroupRepository.deleteCpGroup(id);
        cpGroupRepository.deleteCpGroupSub(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CpGroupDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CpGroups for query {}", query);
//        return cpGroupSearchRepository.search(queryStringQuery(query), pageable)
//            .map(cpGroupMapper::toDto);
        return null;
    }

    @Override
    public Page<CpGroupDetailDTO> onSearch(CpGroupDTO cpGroupDTO, Pageable pageable) {
        ProvinceUsers province = provinceUserRepository.getByUserNameAndStatus(SecurityUtils.getCurrentUserLogin().get(), Constants.STATUS.ACTIVE).orElse(null);
        return cpGroupRepository.onSearch(province != null ? province.getProvinceId() : null,
            province != null ? province.getIsParent() : null,
            province != null ? province.getId() : null,
            Constants.DEFAULT_VALUE.equals(cpGroupDTO.getCpId()) ? null: cpGroupDTO.getCpId(), DataUtil.makeLikeParam(cpGroupDTO.getCpGroupName()), pageable).
            map(CpGroupDetailDTO::new);

    }
}
