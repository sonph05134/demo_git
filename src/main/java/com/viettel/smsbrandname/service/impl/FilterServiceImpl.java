package com.viettel.smsbrandname.service.impl;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.domain.CfgFilterEntity;
import com.viettel.smsbrandname.repository.FilterRepository;
import com.viettel.smsbrandname.service.FilterService;
import com.viettel.smsbrandname.service.dto.FilterDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * Autogen class: Lop thao tac danh sach Filter
 *
 * @author ToolGen
 * @date Thu Dec 10 17:32:22 ICT 2020
 */
@Service
public class FilterServiceImpl extends StandardizeLogging implements FilterService {

    @Autowired
    private FilterRepository filterRepository;

    public FilterServiceImpl() {
        super(FilterServiceImpl.class);
    }

    /**
     * Lay du lieu cac filter
     *
     * @param filterDTO params client
     * @return
     */
    @Override
    public Page<CfgFilterEntity> search(FilterDTO filterDTO) {
        int pageSize = filterDTO.getPageSize();
        int currentPage = filterDTO.getCurrentPage();
        Long status = filterDTO.getStatus();
        if (status == null) {
            return filterRepository.findAllByKeywordContainingIgnoreCase(
                PageRequest.of(currentPage, pageSize, Sort.by("keyword").ascending()), filterDTO.getKeyword()
            );
        }
        return filterRepository.findAllByKeywordContainingIgnoreCaseAndStatus(
            PageRequest.of(currentPage, pageSize, Sort.by("keyword").ascending()),
            filterDTO.getKeyword(), filterDTO.getStatus()
        );
    }



    @Override
    public void add(CfgFilterEntity entity) {
        Date date = new Date();
        Long id = entity.getCfgFilterId() == null ? -1 : entity.getCfgFilterId();
        Optional<CfgFilterEntity> optional =
            filterRepository.findByKeywordIgnoreCaseAndCfgFilterIdNotLike(entity.getKeyword(), id);
        if (optional.isPresent()) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "AdminFilterManagerDAO_saveOrUpdateCfgFilter_failed(Duplicate keyword)", date);
            throw new BusinessException(ErrorCodeResponse.ERR_EXIST, Translator.toLocale("filter-keyword"));
        }
        filterRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        Date date = new Date();
        try {
            filterRepository.deleteById(id);
        } catch (Exception e) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "AdminFilterManagerDAO_deleteCfgFilter_Fail", date);
            throw new BusinessException(ErrorCodeResponse.DELETE_FAIL);
        }
    }
}
