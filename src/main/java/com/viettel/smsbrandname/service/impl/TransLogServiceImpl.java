package com.viettel.smsbrandname.service.impl;

import com.viettel.smsbrandname.commons.ExcelUtils;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.service.TransLogService;
import com.viettel.smsbrandname.domain.TransLog;
import com.viettel.smsbrandname.repository.TransLogRepository;
import com.viettel.smsbrandname.service.dto.TransLogDTO;
import com.viettel.smsbrandname.service.mapper.TransLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TransLog}.
 */
@Service
@Transactional
public class TransLogServiceImpl implements TransLogService {

    private final Logger log = LoggerFactory.getLogger(TransLogServiceImpl.class);

    private final TransLogRepository transLogRepository;

    private final TransLogMapper transLogMapper;

    private final ExcelUtils excelUtils;

//    private final TransLogSearchRepository transLogSearchRepository;

    public TransLogServiceImpl(TransLogRepository transLogRepository, TransLogMapper transLogMapper,ExcelUtils excelUtils) {
        this.transLogRepository = transLogRepository;
        this.transLogMapper = transLogMapper;
        this.excelUtils = excelUtils;
//        this.transLogSearchRepository = transLogSearchRepository;
    }

    @Override
    public TransLogDTO save(TransLogDTO transLogDTO) {
        log.debug("Request to save TransLog : {}", transLogDTO);
        TransLog transLog = transLogMapper.toEntity(transLogDTO);
        transLog = transLogRepository.save(transLog);
        TransLogDTO result = transLogMapper.toDto(transLog);
//        transLogSearchRepository.save(transLog);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransLogs");
        return transLogRepository.findAll(pageable)
            .map(transLogMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TransLogDTO> findOne(Long id) {
        log.debug("Request to get TransLog : {}", id);
        return transLogRepository.findById(id)
            .map(transLogMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransLog : {}", id);
        transLogRepository.deleteById(id);
//        transLogSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransLogDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TransLogs for query {}", query);
//        return transLogSearchRepository.search(queryStringQuery(query), pageable)
//            .map(transLogMapper::toDto);
        return null;
    }

    @Override
    public Page<TransLogDTO> searchHasfilter(String cpCode, Long chanel, String fromDate, String toDate, String currency, Pageable pageable){
        return transLogRepository.search(fromDate, toDate, cpCode, chanel, currency, pageable)
            .map(TransLogDTO::new);
    }

    @Override
    public ByteArrayInputStream export(String cpCode, Long chanel, String fromDate, String toDate, String currency) {
        try {
            List<TransLogDTO> lst = transLogRepository.search(fromDate, toDate, cpCode, chanel, currency)
                .stream().map(TransLogDTO::new).collect(Collectors.toList());
            List<String> lstHeader = Arrays.asList(
                "Mã khách hàng",
                "Tên khách hàng",
                "Kênh giao dịch",
                "Số tiền giao dịch",
                "Tài khoản trước",
                "Tài khoản sau",
                "Ghi chú",
                "Ngày",
                "Loại tiền tệ");
            List<String> lstColumn = Arrays.asList(
                "cpCode",
                "cpName",
                "chanelView",
                "amount",
                "balanceBefore",
                "balanceAfter",
                "transNote",
                "transTime",
                "currency");
            String title = "Danh sách lịch sử giao dịch của CP";
            List<Integer> lstSize = new ArrayList<>();
            lstSize.addAll(Arrays.asList(
                ExcelUtils.WIDTH * 20,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
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
                ExcelUtils.ALIGN_RIGHT,
                ExcelUtils.ALIGN_RIGHT,
                ExcelUtils.ALIGN_RIGHT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT));
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String dateExport = Translator.toLocale("translog.exportDate") + " " + df.format(new Date());
            return excelUtils.export(title,lst, lstHeader, lstColumn, title, lstSize, lstAlign, dateExport);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
