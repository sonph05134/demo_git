package com.viettel.cmsmobilebrandname.mobile.service.impl;

import com.viettel.cmsmobilebrandname.mobile.dto.FilterDTOMB;
import com.viettel.cmsmobilebrandname.mobile.dto.mapper.MBFilterMapper;
import com.viettel.cmsmobilebrandname.mobile.repository.MBFilterRepository;
import com.viettel.cmsmobilebrandname.mobile.repository.FilterSpecification;
import com.viettel.cmsmobilebrandname.mobile.service.MBFilterService;
import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.domain.CfgFilterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Autogen class: Lop thao tac danh sach Filter
 *
 * @author ToolGen
 * @date Thu Dec 10 17:32:22 ICT 2020
 */
@Service
public class MBFilterServiceImpl extends StandardizeLogging implements MBFilterService {

    @Autowired
    private MBFilterRepository filterRepository;

    public MBFilterServiceImpl() {
        super(MBFilterServiceImpl.class);
    }


    @Autowired
    MBFilterMapper filterMapper;
    /**
     * Lay du lieu cac filter
     *
     * @param filterDTO params client
     * @return
     */

    /**
     * Lay du lieu cac filter
     * Nguyen Van Huyen
     * viet cho Moble
     * @param filterDTOMB params client
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Page<FilterDTOMB> searchFilter(FilterDTOMB filterDTOMB, Pageable pageable) {

        Page<FilterDTOMB> filterDTOPage =   filterRepository.findAll(new FilterSpecification(filterDTOMB),pageable)
            .map(filterMapper :: toDto);
        for (FilterDTOMB filterDTOMBValue : filterDTOPage) {
            if(filterDTOMB.getStatus() == 1){
                filterDTOMBValue.setStatusName(Translator.toLocale("filter.active"));
            }else {
                filterDTOMBValue.setStatusName(Translator.toLocale("filter.not_active"));
            }
        }
        return filterDTOPage;
    }


    /**
     * delete  du lieu filter theo id
     * Nguyen Van Huyen
     * viet cho Moble
//     * @param filterDTO params client
     * @return
     */
    @Transactional
    @Override
    public void delete(Long id) {
        filterRepository.deleteById(id);
    }


    /**
     * update  du lieu filter theo id
     * Nguyen Van Huyen
     * viet cho Moble
     * @param filterDTOMB params client
     * @return
     */
    @Transactional
    @Override
    public boolean updateFilterForMobile(FilterDTOMB filterDTOMB) {
          Date date = new Date();
          String keywordFilter = filterDTOMB.getKeyword().toLowerCase();
          Long idFilter = filterDTOMB.getCfgFilterId();
          Boolean checkExist = true;

        for (CfgFilterEntity cfgFilterEntity : filterRepository.findAll()) {
                if(cfgFilterEntity.getKeyword().toLowerCase().equalsIgnoreCase(keywordFilter) == true){
                        checkExist = false;
                        break;
                }
        }

        if(checkExist == true){
            filterRepository.save(filterMapper.toEntity(filterDTOMB));
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS,"AdminFilterManagerDAO_saveOrUpdateCfgFilter((edit)_Success", date);
        }else {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "AdminFilterManagerDAO_saveOrUpdateCfgFilter_failed(Duplicate keyword)",date);
        }
        return checkExist;

    }


    /**
     * Add filter
     * Nguyen Van Huyen
     * viet cho Moble
     * @param filterDTOMB params client
     * @return
     */
    @Transactional
    @Override
    public boolean addFilterForMobile(FilterDTOMB filterDTOMB) {
        Date date = new Date();
        if(filterRepository.findKeyword(filterDTOMB.getKeyword()) == null) {
            filterRepository.save(filterMapper.toEntity(filterDTOMB));
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS,"AdminFilterManagerDAO_saveOrUpdateCfgFilter(add)_Success", date);
            return true;
        }else {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL,"AdminFilterManagerDAO_saveOrUpdateCfgFilter_failed((Duplicate keyword)", date);
            return false;
        }
    }


}
