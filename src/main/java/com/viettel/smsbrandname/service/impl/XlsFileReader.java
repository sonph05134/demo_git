package com.viettel.smsbrandname.service.impl;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.ExportUtils;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.domain.Cp;
import com.viettel.smsbrandname.service.FileReader;
import com.viettel.smsbrandname.service.dto.CpGroupSubDTO;
import com.viettel.smsbrandname.service.dto.CpGroupSubForm;
import com.viettel.smsbrandname.service.dto.ExcelColumn;
import com.viettel.smsbrandname.web.rest.errors.BadRequestAlertException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.Instant;
import java.util.*;

@Component("xlsFileReader")
public class XlsFileReader implements FileReader {
    @Autowired
    ExportUtils excelUtils;
    @Autowired
    ContactValidate contactValidate;

    @Value("${server.pathOutput}")
    String pathOutput;

    private static final Logger log = LoggerFactory.getLogger(XlsFileReader.class);

    private static final Integer START_ROW = 1;
    private static final Integer START_COLUMN = 0;
    private static final Integer MAX_COLUMN = 7;

    @Override
    public List<CpGroupSubDTO> readFile(String filename, InputStream inputStream, Long cpId, Optional<Cp> cpOptional, List<String> lstPrefix, Long cpGroupId, List<ExcelColumn> lstColumn) throws Exception {
        System.out.println("Doc file xls");
        List<CpGroupSubDTO> lstData = new ArrayList<>();
        Workbook workbook;
        if (filename.toLowerCase().endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else {
            workbook = new HSSFWorkbook(inputStream);
        }
        Sheet sheet = workbook.getSheetAt(0);
        int startRow = START_ROW;
        int maxRow = sheet.getLastRowNum();

        Row header = sheet.getRow(START_ROW - 1);
        this.validateHeader(header, lstColumn);

        Instant now = Instant.now();
        int i;
        Set<String> msisdns = new HashSet<>();
        for (int rowIdx = startRow; rowIdx <= maxRow; rowIdx++) {
            Row row = sheet.getRow(rowIdx);
            i = START_COLUMN;
            CpGroupSubDTO dto = new CpGroupSubDTO();
            dto.setCpId(cpId);

            dto.setCpCode(cpOptional.get().getCpCode());
            dto.setCpGroupId(cpGroupId);
            dto.setMsisdn(excelUtils.getRowString(row, i++));
            dto.setName(excelUtils.getRowString(row, i++));
            dto.setSexStr(excelUtils.getRowString(row, i++));
            dto.setCode(excelUtils.getRowString(row, i++));
            dto.setBirthdayStr(excelUtils.getRowString(row, i++));
            dto.setAddress(excelUtils.getRowString(row, i++));
            dto.setNote(excelUtils.getRowString(row, i++));
            contactValidate.validateForm(dto, lstPrefix, cpOptional.get().getCpCode(), now, msisdns);
            if (!DataUtil.isNullOrEmpty(dto.getMsisdn())) {
                msisdns.add(dto.getMsisdn());
            }
            lstData.add(dto);
        }
        return lstData;
    }

    @Override
    public ByteArrayInputStream writeFileResult(List<CpGroupSubDTO> lstData, CpGroupSubForm cpGroupSubForm) throws Exception {
        MultipartFile multipartFile = cpGroupSubForm.getFileImport();
        String fileSave = this.getSafeFileExport(this.pathOutput, "result_" + multipartFile.getOriginalFilename());
        cpGroupSubForm.setFileResult(fileSave);

        InputStream inputStream = multipartFile.getInputStream();
        Workbook workbook;
        if (cpGroupSubForm.getFileImport().getOriginalFilename().toLowerCase().endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else {
            workbook = new HSSFWorkbook(inputStream);
        }
        Sheet sheet = workbook.getSheetAt(0);

        Row rowHeader = sheet.getRow(START_ROW - 1);
        rowHeader.setHeight((short) 500);
        Cell cellHeader = rowHeader.createCell(MAX_COLUMN);
        cellHeader.setCellStyle(rowHeader.getCell(MAX_COLUMN - 1).getCellStyle());
        cellHeader.setCellValue(Translator.toLocale("contact.result"));
        if (!DataUtil.isNullOrEmpty(lstData)) {
            for (int i = 0; i < lstData.size(); i++) {
                Row row = sheet.getRow(START_ROW + i);
                row.createCell(MAX_COLUMN).setCellValue(DataUtil.isNullOrEmpty(lstData.get(i).getError()) ? Translator.toLocale("contact.result.success") : lstData.get(i).getError());
            }
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());

    }

    void validateHeader(Row header, List<ExcelColumn> lstColumn) throws Exception {
        try {
            for (int i = 0; i < lstColumn.size(); i++) {
                if (!header.getCell(i).getStringCellValue().equalsIgnoreCase(lstColumn.get(i).getTitle())) {
                    throw new BadRequestAlertException(Translator.toLocale("error.cpGroupSub.template.invalidFormat"), "CpGroupSub", "cpGroupSub.template.invalidFormat");
                }
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new BadRequestAlertException(Translator.toLocale("error.cpGroupSub.template.invalidFormat"), "CpGroupSub", "cpGroupSub.template.invalidFormat");
        }

    }

}
