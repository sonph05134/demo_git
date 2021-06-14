package com.viettel.smsbrandname.service.impl;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.Cp;
import com.viettel.smsbrandname.domain.Telco;
import com.viettel.smsbrandname.repository.CpGroupSubCustomRepository;
import com.viettel.smsbrandname.repository.CpRepository;
import com.viettel.smsbrandname.repository.TelcoRepository;
import com.viettel.smsbrandname.security.SecurityUtils;
import com.viettel.smsbrandname.service.CpGroupSubService;
import com.viettel.smsbrandname.domain.CpGroupSub;
import com.viettel.smsbrandname.repository.CpGroupSubRepository;
import com.viettel.smsbrandname.service.FileReader;
import com.viettel.smsbrandname.service.dto.CpGroupSubDTO;
import com.viettel.smsbrandname.service.dto.CpGroupSubForm;
import com.viettel.smsbrandname.service.dto.CpGroupSubResultDTO;
import com.viettel.smsbrandname.service.dto.ExcelColumn;
import com.viettel.smsbrandname.service.mapper.CpGroupSubMapper;
import com.viettel.smsbrandname.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CpGroupSub}.
 */
@Service
@Transactional
public class CpGroupSubServiceImpl implements CpGroupSubService {

    private final Logger log = LoggerFactory.getLogger(CpGroupSubServiceImpl.class);

    private final CpGroupSubRepository cpGroupSubRepository;

    private final CpGroupSubMapper cpGroupSubMapper;

    private final CpRepository cpRepository;

    private final TelcoRepository telcoRepository;
    private final FileReader xlsFileReader;
    private final CpGroupSubCustomRepository cpGroupSubCustomRepository;

    public CpGroupSubServiceImpl(CpGroupSubRepository cpGroupSubRepository, CpGroupSubMapper cpGroupSubMapper, CpRepository cpRepository, TelcoRepository telcoRepository, FileReader xlsFileReader, CpGroupSubCustomRepository cpGroupSubCustomRepository) {
        this.cpGroupSubRepository = cpGroupSubRepository;
        this.cpGroupSubMapper = cpGroupSubMapper;
        this.cpRepository = cpRepository;
        this.telcoRepository = telcoRepository;
        this.xlsFileReader = xlsFileReader;
        this.cpGroupSubCustomRepository = cpGroupSubCustomRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CpGroupSubDTO save(CpGroupSubDTO cpGroupSubDTO) throws Exception {
        log.debug("Request to save CpGroupSub : {}", cpGroupSubDTO);

        if (cpGroupSubDTO.getId() == null) {
            cpGroupSubDTO.setCreateDate(Instant.now());
        } else {
            cpGroupSubDTO.setUpdateDate(Instant.now());
        }
        if (Constants.DEFAULT_VALUE.equals(cpGroupSubDTO.getSex())) {
            cpGroupSubDTO.setSex(null);
        }

        Optional<Cp> cpOptional = cpRepository.findFirstByCpIdAndStatus(cpGroupSubDTO.getCpId(), Long.valueOf(Constants.STATUS.ACTIVE));
        if (cpOptional.isPresent()) {
            cpGroupSubDTO.setCpCode(cpOptional.get().getCpCode());
        }

        List<Telco> lstTelco = telcoRepository.findAll();

        Map<String, Telco> mapTelco = new HashMap<>();
        lstTelco.forEach(telco -> Arrays.asList(telco.getTelcoPrefix().split(",")).stream().forEach(prefix -> mapTelco.put(prefix, telco)));
        Telco telco = getTelco(mapTelco, cpGroupSubDTO.getMsisdn());

        //Validate SDT
        validateMsisdn(cpGroupSubDTO, lstTelco);


        //Lay SDT cu
        String oldMsisdn = getOldMsisdn(cpGroupSubDTO);
        CpGroupSub cpGroupSub = cpGroupSubMapper.toEntity(cpGroupSubDTO);
        cpGroupSub = cpGroupSubRepository.save(cpGroupSub);
        CpGroupSubDTO result = cpGroupSubMapper.toDto(cpGroupSub);

        //Insert
        if (DataUtil.isNullOrEmpty(cpGroupSubDTO.getId())) {
            cpGroupSubRepository.updateCpGroupSubLimit(cpGroupSubDTO.getMsisdn(), cpGroupSubDTO.getCpId(), telco == null ? 0L : telco.getCanFreeSms(), telco == null ? "" : telco.getTelcoCode());
        } else {
            //Update
            if (!DataUtil.isNullOrEmpty(oldMsisdn) && !oldMsisdn.equalsIgnoreCase(cpGroupSubDTO.getMsisdn())) {
                cpGroupSubRepository.updateCpGroupSubLimitEdit(cpGroupSubDTO.getMsisdn(), cpGroupSubDTO.getCpId());
                cpGroupSubRepository.deleteCpGroupSubEdit(cpGroupSubDTO.getCpId());
                cpGroupSubRepository.updateCpGroupSubLimit(cpGroupSubDTO.getMsisdn(), cpGroupSubDTO.getCpId(), telco == null ? 0L : telco.getCanFreeSms(), telco == null ? "" : telco.getTelcoCode());
            }
        }
        return result;
    }

    private String getOldMsisdn(CpGroupSubDTO cpGroupSubDTO) {
        String oldMsisdn = null;
        if (!DataUtil.isNullOrEmpty(cpGroupSubDTO.getId())) {
            Optional<CpGroupSub> cpGroupSubOpt = cpGroupSubRepository.findById(cpGroupSubDTO.getId());
            if (cpGroupSubOpt.isPresent()) {
                oldMsisdn = cpGroupSubOpt.get().getMsisdn();
                cpGroupSubDTO.setCreateDate(cpGroupSubOpt.get().getCreateDate());
            } else {
                throw new BadRequestAlertException(Translator.toLocale("error.haveSomeError"), "cpGroup", "cpGroup.cpGroupNotExisted");
            }
        }
        return oldMsisdn;
    }

    private void validateMsisdn(CpGroupSubDTO cpGroupSubDTO, List<Telco> lstTelco) throws Exception {
        List<String> lstPrefix = lstTelco.stream().filter(e -> !DataUtil.isNullOrEmpty(e.getPrefixDetail())).map(e -> Arrays.asList(e.getPrefixDetail().split(","))).flatMap(List::stream).collect(Collectors.toList());
        if (!DataUtil.isNullOrEmpty(cpGroupSubDTO.getMsisdn())) {
            if (!DataUtil.isValidMsisdn(cpGroupSubDTO.getMsisdn(), lstPrefix)) {
                throw new BadRequestAlertException(Translator.toLocale("error.cpGroupSub.msisdn.invalid"), "CpGroupSub", "cpGroupSub.msisdn.invalid");
            }

            List<CpGroupSub> numObj = cpGroupSubRepository.findMsisdn(cpGroupSubDTO.getMsisdn(), cpGroupSubDTO.getId(), cpGroupSubDTO.getCpGroupId());
            if (!DataUtil.isNullOrEmpty(numObj)) {
                throw new BadRequestAlertException(Translator.toLocale("error.cpGroupSub.msisdn.existed"), "CpGroupSub", "cpGroupSub.msisdn.existed");
            }
        }
    }

    private Telco getTelco(Map<String, Telco> mapTelco, String msisdn) {
        Set<String> setTelco = new TreeSet<>((s, t1) -> t1.length() - s.length());
        setTelco.addAll(mapTelco.keySet());
        for (String tel : setTelco) {
            if (msisdn.startsWith(tel)) {
                return mapTelco.get(tel);
            }
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CpGroupSubDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CpGroupSubs");
        return cpGroupSubRepository.findAll(pageable)
            .map(cpGroupSubMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CpGroupSubDTO> findOne(Long id) {
        log.debug("Request to get CpGroupSub : {}", id);
        return cpGroupSubRepository.findById(id)
            .map(cpGroupSubMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CpGroupSub : {}", id);
        Optional<CpGroupSubDTO> cpGroupSubDTOOptional = cpGroupSubRepository.findById(id).map(cpGroupSubMapper::toDto);
        if (cpGroupSubDTOOptional.isPresent()) {
            CpGroupSubDTO cpGroupSubDTO = cpGroupSubDTOOptional.get();
            cpGroupSubRepository.deleteById(id);
            cpGroupSubRepository.updateCpGroupSubLimitEdit(cpGroupSubDTO.getMsisdn(), cpGroupSubDTO.getCpId());
            cpGroupSubRepository.deleteCpGroupSubEdit(cpGroupSubDTO.getCpId());
        } else {
            throw new BadRequestAlertException(Translator.toLocale("error.haveSomeError"), "cpGroup", "cpGroup.cpGroupNotExisted");
        }
    }

    @Override
    public Page<CpGroupSubResultDTO> search(CpGroupSubDTO cpGroupSubDTO, Pageable pageable) {
        return cpGroupSubRepository.search(DataUtil.makeLikeParam(cpGroupSubDTO.getMsisdn()),
            DataUtil.makeLikeParam(cpGroupSubDTO.getName()), cpGroupSubDTO.getCpGroupId(),
            pageable).map(CpGroupSubResultDTO::new);
    }

    @Override
    public List<CpGroupSubResultDTO> onExport(CpGroupSubDTO cpGroupSubDTO) {
        return cpGroupSubRepository.onExport(DataUtil.makeLikeParam(cpGroupSubDTO.getMsisdn()),
            DataUtil.makeLikeParam(cpGroupSubDTO.getName()), cpGroupSubDTO.getCpGroupId()).stream()
            .map(CpGroupSubResultDTO::new).peek(CpGroupSubResultDTO::formatValue).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ByteArrayInputStream importCpGroupSub(CpGroupSubForm cpGroupSubForm) throws Exception {
        List<String> lstPrefix = telcoRepository.findAll().stream().filter(e -> !DataUtil.isNullOrEmpty(e.getPrefixDetail()))
            .map(e -> Arrays.asList(e.getPrefixDetail().split(",")))
            .flatMap(List::stream).collect(Collectors.toList());
        Optional<Cp> cpOptional = cpRepository.findFirstByCpIdAndStatus(cpGroupSubForm.getCpId(), Long.valueOf(Constants.STATUS.ACTIVE));
        List<CpGroupSubDTO> lstData;

        List<Telco> lstTelco = telcoRepository.findAll();
        Map<String, Telco> mapTelco = new HashMap<>();
        lstTelco.forEach(telco -> Arrays.asList(telco.getTelcoPrefix().split(",")).stream().forEach(prefix -> mapTelco.put(prefix, telco)));


        try (InputStream inputStream = cpGroupSubForm.getFileImport().getInputStream()) {
            List<ExcelColumn> lstColumn = buildColumn();
            lstColumn.remove(0);
            lstData = xlsFileReader.readFile(cpGroupSubForm.getFileImport().getOriginalFilename(), inputStream,
                cpGroupSubForm.getCpId(), cpOptional, lstPrefix, cpGroupSubForm.getCpGroupId(), lstColumn);

            List<CpGroupSubDTO> lstUpdate = lstData.stream().filter(e -> e.getError().isEmpty()).collect(Collectors.toList());
            cpGroupSubForm.setResult(Translator.toLocale("contact.import.success").replace("{{field}}", String.valueOf(lstUpdate.size())));
            cpGroupSubRepository.saveAll(cpGroupSubMapper.toEntity(lstUpdate));
            lstUpdate.stream().forEach(e -> {
                Telco telco = getTelco(mapTelco, e.getMsisdn());
                if (telco != null) {
                    e.setTelcoCode(telco.getTelcoCode());
                    e.setMonthFreeSms(telco.getCanFreeSms());
                }
            });
            cpGroupSubCustomRepository.updateCpGroupSubLimit(lstUpdate);
        }
        ByteArrayInputStream fileResult = xlsFileReader.writeFileResult(lstData, cpGroupSubForm);

        return fileResult;
    }

    public List<ExcelColumn> buildColumn() {
        List<ExcelColumn> lstColumn = new ArrayList<>();
        lstColumn.add(new ExcelColumn("cpGroupName", Translator.toLocale("cpGroupSub.cpGroupName"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("msisdn", Translator.toLocale("cpGroupSub.msisdn"), ExcelColumn.ALIGN_MENT.LEFT, Constants.WIDTH * 15));
        lstColumn.add(new ExcelColumn("name", Translator.toLocale("cpGroupSub.name"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("sexStr", Translator.toLocale("cpGroupSub.sex"), ExcelColumn.ALIGN_MENT.LEFT, Constants.WIDTH * 15));
        lstColumn.add(new ExcelColumn("code", Translator.toLocale("cpGroupSub.code"), ExcelColumn.ALIGN_MENT.LEFT, Constants.WIDTH * 15));
        lstColumn.add(new ExcelColumn("birthdayStr", Translator.toLocale("cpGroupSub.birthday"), ExcelColumn.ALIGN_MENT.CENTER, Constants.WIDTH * 15));
        lstColumn.add(new ExcelColumn("address", Translator.toLocale("cpGroupSub.address"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("note", Translator.toLocale("cpGroupSub.note"), ExcelColumn.ALIGN_MENT.LEFT));
        return lstColumn;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<CpGroupSubDTO> lstGroupSub) {
        log.debug("Request to delete {} CpGroupSubs", lstGroupSub.size());
        for (CpGroupSubDTO cpGroupSubDTO : lstGroupSub) {
            Optional<CpGroupSub> cpGroupSub = cpGroupSubRepository.findById(cpGroupSubDTO.getId());
            if (!cpGroupSub.isPresent()) {
                throw new BadRequestAlertException(Translator.toLocale("error.haveSomeError"), "cpGroup", "cpGroup.cpGroupNotExisted");
            }
        }
        cpGroupSubRepository.deleteAll(cpGroupSubMapper.toEntity(lstGroupSub));
        for (CpGroupSubDTO cpGroupSubDTO : lstGroupSub) {
            cpGroupSubRepository.updateCpGroupSubLimitEdit(cpGroupSubDTO.getMsisdn(), cpGroupSubDTO.getCpId());
            cpGroupSubRepository.deleteCpGroupSubEdit(cpGroupSubDTO.getCpId());
        }
    }
}
