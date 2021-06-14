package com.viettel.smsbrandname.service.impl;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.ExcelUtils;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.service.MtService;
import com.viettel.smsbrandname.domain.Mt;
import com.viettel.smsbrandname.repository.MtRepository;
import com.viettel.smsbrandname.service.dto.MtDTO;
import com.viettel.smsbrandname.service.dto.MtSearchDTO;
import com.viettel.smsbrandname.service.dto.TransLogDTO;
import com.viettel.smsbrandname.service.mapper.MtMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Mt}.
 */
@Service
@Transactional
public class MtServiceImpl implements MtService {

    private final Logger log = LoggerFactory.getLogger(MtServiceImpl.class);

    private final MtRepository mtRepository;

    private final MtMapper mtMapper;

    private final ExcelUtils excelUtils;

    //private final MtSearchRepository mtSearchRepository;

    public MtServiceImpl(MtRepository mtRepository, MtMapper mtMapper, ExcelUtils excelUtils) {
        this.mtRepository = mtRepository;
        this.mtMapper = mtMapper;
        this.excelUtils = excelUtils;
        //this.mtSearchRepository = mtSearchRepository;
    }

    @Override
    public MtDTO save(MtDTO mtDTO) {
        log.debug("Request to save Mt : {}", mtDTO);
        Mt mt = mtMapper.toEntity(mtDTO);
        mt = mtRepository.save(mt);
        MtDTO result = mtMapper.toDto(mt);
        //mtSearchRepository.save(mt);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MtDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Mts");
        return mtRepository.findAll(pageable)
            .map(mtMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MtDTO> findOne(Long id) {
        log.debug("Request to get Mt : {}", id);
        return mtRepository.findById(id)
            .map(mtMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mt : {}", id);
        mtRepository.deleteById(id);
        //mtSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MtDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Mts for query {}", query);
//        return mtRepository.search(queryStringQuery(query), pageable)
//            .map(mtMapper::toDto);
        return null;
    }

    @Override
    public Page<MtDTO> searchHasFilter(MtSearchDTO dto, Pageable pageable) {
        return mtRepository.search(dto.getFromDate(), dto.getToDate(), dto.getReceiver(), dto.getCpId(),
            dto.getSender(), dto.getAliasType(), dto.getTelcoId(), pageable)
            .map(MtDTO::new);
    }

    @Override
    public ByteArrayInputStream export(MtSearchDTO dto) {
        try {
            List<MtDTO> lst = mtRepository.search(dto.getFromDate(), dto.getToDate(), dto.getReceiver(), dto.getCpId(),
                dto.getSender(), dto.getAliasType(), dto.getTelcoId(), null).stream()
                .map(MtDTO::new).collect(Collectors.toList());
            if (!DataUtil.isNullOrEmpty(lst)) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                lst.stream().forEach(mtDTO -> {
                    if (!DataUtil.isNullOrEmpty(mtDTO.getMtTime())) {
                        mtDTO.setMtTimeString(df.format(Date.from(mtDTO.getMtTime())));
                    }
                    mtDTO.setReceiver(mtDTO.getReceiver() + " ");
                });
            }
            List<String> lstHeader = Arrays.asList(
                "Thời gian",
                "Số thuê bao",
                "Alias",
                "Nội dung",
                "SMS",
                "Trạng thái",
                "Mã khách hàng",
                "Loại chương trình",
                "Nhà mạng",
                "webservice");
            List<String> lstColumn = Arrays.asList(
                "mtTimeString",
                "receiver",
                "sender",
                "message",
                "numMt",
                "statusView",
                "cpCode",
                "aliasTypeView",
                "telcoName",
                "webservice");
            String title = "Tra cứu MT";
            List<Integer> lstSize = new ArrayList<>();
            lstSize.addAll(Arrays.asList(
                ExcelUtils.WIDTH * 20,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 60,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30));
            List<Integer> lstAlign = new ArrayList<>();
            lstAlign.addAll(Arrays.asList(
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_RIGHT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT));
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String fromDate = Translator.toLocale("translog.fromDate") + " " + df.format(df.parse(dto.getFromDate()));
            String toDate = Translator.toLocale("translog.toDate") + " " + df.format(df.parse(dto.getToDate()));
            return excelUtils.export(title, lst, lstHeader, lstColumn, title, lstSize, lstAlign, fromDate, toDate);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
