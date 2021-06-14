package com.viettel.smsbrandname.commons;

import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.service.dto.ExcelColumn;
import com.viettel.smsbrandname.service.dto.ExcelTitle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ExportUtils {
    private final DecimalFormat doubleFormat = new DecimalFormat("#.##");
    private final Logger log = LoggerFactory.getLogger(ExportUtils.class);


    public ByteArrayInputStream onExport(List<ExcelColumn> lstColumn, List<?> lstData, int startRow, int startCol, ExcelTitle excelTitle, Boolean displayIndex) throws Exception {

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data");
            int diff = this.getDiff(displayIndex);

            Row rowHeader = createFileTitle(startRow, startCol, excelTitle, workbook, sheet, (short) 500, (lstColumn.size() - 1 + diff));

            CellStyle cellStyleHeader = createStyleHeader(workbook);

            if (Boolean.TRUE.equals(displayIndex)) {
                Cell cellIndex = rowHeader.createCell(startCol);
                cellIndex.setCellValue("STT");
                cellIndex.setCellStyle(cellStyleHeader);
            }
            for (int i = 0; i < lstColumn.size(); i++) {
                Cell cellHeader = rowHeader.createCell(i + startCol + diff);
                cellHeader.setCellValue(lstColumn.get(i).getTitle());
                cellHeader.setCellStyle(cellStyleHeader);
            }
            AtomicInteger atomicInteger = new AtomicInteger(0);
            lstColumn.forEach(e -> {
                if (e.getColumnWidth() != null) {
                    sheet.setColumnWidth(startCol + diff + atomicInteger.getAndIncrement(), e.getColumnWidth());
                }
            });

            //trai
            ByteArrayInputStream byteArrayInputStream = createFileOutput(lstColumn, lstData, startRow, startCol, workbook, sheet, displayIndex);
            return byteArrayInputStream;
        }
    }

    private Row createFileTitle(int startRow, int startCol, ExcelTitle excelTitle, Workbook workbook, Sheet sheet, short rowHeight, int numCol) {
        int rowTitle = startRow > 3 ? startRow - 3 : 0;
        if (excelTitle != null) {
            if (!DataUtil.isNullOrEmpty(excelTitle.getTitle())) {
                Row rowMainTitle = sheet.createRow(rowTitle);
                Cell mainCellTitle = rowMainTitle.createCell(startCol);
                mainCellTitle.setCellValue(excelTitle.getTitle().toUpperCase());
                CellStyle cellStyleTitle = getCellStyleTitle(workbook);
                mainCellTitle.setCellStyle(cellStyleTitle);
                sheet.addMergedRegion(new CellRangeAddress(rowTitle, rowTitle, startCol, numCol));
            }
            if (!DataUtil.isNullOrEmpty(excelTitle.getDateExportPattern()) && !DataUtil.isNullOrEmpty(excelTitle.getDateExportTitle())) {
                Row rowDateExport = sheet.createRow(rowTitle + 1);
                Cell mainCellTitle = rowDateExport.createCell(numCol / 2);
                mainCellTitle.setCellValue(excelTitle.getDateExportTitle() + ":" + DataUtil.dateToString(new Date(), excelTitle.getDateExportPattern()));
                CellStyle cellStyle = createStyle(workbook);
                mainCellTitle.setCellStyle(cellStyle);
            }
        }
        //Header
        Row rowHeader = sheet.createRow(startRow);
        rowHeader.setHeight(rowHeight);

        return rowHeader;
    }

    private CellStyle createCellStyleHeader(Workbook workbook) {
        CellStyle cellStyleHeader = workbook.createCellStyle();
        cellStyleHeader.setAlignment(HorizontalAlignment.CENTER);
        cellStyleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleHeader.setBorderLeft(BorderStyle.THIN);
        cellStyleHeader.setBorderBottom(BorderStyle.THIN);
        cellStyleHeader.setBorderRight(BorderStyle.THIN);
        cellStyleHeader.setBorderTop(BorderStyle.THIN);
        cellStyleHeader.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        cellStyleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleHeader.setWrapText(true);
        return cellStyleHeader;
    }

    private CellStyle createCellStyle(Workbook workbook) {
        CellStyle cellStyleHeader = workbook.createCellStyle();
        cellStyleHeader.setAlignment(HorizontalAlignment.LEFT);
        cellStyleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyleHeader;
    }

    private Font createFontHeader(Workbook workbook) {
        Font hSSFFontHeader = workbook.createFont();
        hSSFFontHeader.setFontName(HSSFFont.FONT_ARIAL);
        hSSFFontHeader.setFontHeightInPoints((short) 10);
        hSSFFontHeader.setBold(true);
        return hSSFFontHeader;
    }

    private CellStyle createStyleHeader(Workbook workbook) {
        CellStyle cellStyleHeader = createCellStyleHeader(workbook);
        Font hSSFFontHeader = createFontHeader(workbook);
        hSSFFontHeader.setColor(IndexedColors.BLACK.index);
        cellStyleHeader.setFont(hSSFFontHeader);
        return cellStyleHeader;
    }

    private CellStyle createStyle(Workbook workbook) {
        CellStyle cellStyleHeader = createCellStyle(workbook);
        Font hSSFFontHeader = createFontHeader(workbook);
        cellStyleHeader.setFont(hSSFFontHeader);
        return cellStyleHeader;
    }

    private ByteArrayInputStream createFileOutput(List<ExcelColumn> lstColumn, List<?> lstData, int startRow, int startCol,
                                                  Workbook workbook, Sheet sheet, Boolean displayIndex) throws Exception {
        //trai
        CellStyle cellStyleLeft = getCellStyle(workbook, HorizontalAlignment.LEFT);
        //phai
        CellStyle cellStyleRight = getCellStyle(workbook, HorizontalAlignment.RIGHT);
        //giua
        CellStyle cellStyleCenter = getCellStyle(workbook, HorizontalAlignment.CENTER);

        writeDataReport(lstColumn, lstData, startRow, startCol, sheet, cellStyleLeft, cellStyleRight, cellStyleCenter, displayIndex);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
    }

    private CellStyle getCellStyle(Workbook workbook, HorizontalAlignment horizontalAlignment) {
        CellStyle cellStyleCenter = workbook.createCellStyle();
        cellStyleCenter.setAlignment(horizontalAlignment);
        cellStyleCenter.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleCenter.setBorderLeft(BorderStyle.THIN);
        cellStyleCenter.setBorderBottom(BorderStyle.THIN);
        cellStyleCenter.setBorderRight(BorderStyle.THIN);
        cellStyleCenter.setBorderTop(BorderStyle.THIN);
        cellStyleCenter.setWrapText(true);
        cellStyleCenter.setDataFormat((short) BuiltinFormats.getBuiltinFormat("@"));
        return cellStyleCenter;
    }

    private CellStyle getCellStyleTitle(Workbook workbook) {
        CellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(HorizontalAlignment.CENTER);
        cellStyleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleTitle.setFillForegroundColor(IndexedColors.GREEN.index);
        cellStyleTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font hSSFFont = workbook.createFont();
        hSSFFont.setFontName(HSSFFont.FONT_ARIAL);
        hSSFFont.setFontHeightInPoints((short) 20);
        hSSFFont.setBold(true);
        hSSFFont.setColor(IndexedColors.WHITE.index);
        cellStyleTitle.setFont(hSSFFont);
        return cellStyleTitle;
    }

    private void writeDataReport(List<ExcelColumn> lstColumn, List<?> lstData, int startRow, int startCol, Sheet sheet,
                                 CellStyle cellStyleLeft, CellStyle cellStyleRight, CellStyle cellStyleCenter,
                                 Boolean displayIndex) throws IllegalAccessException {
        if (lstData != null && !lstData.isEmpty()) {
            Object firstRow = lstData.get(0);
            Map<String, Field> mapField = new HashMap<>();
            for (ExcelColumn column : lstColumn) {
                String header = column.getColumn();
                Field[] fs = ReflectorUtil.getAllFields(firstRow.getClass());
                Arrays.stream(fs).peek(f -> f.setAccessible(true)).filter(f -> f.getName().equals(header)).forEach(f -> mapField.put(header, f));
            }

            int diff = this.getDiff(displayIndex);
            for (int i = 0; i < lstData.size(); i++) {
                Row row = sheet.createRow(i + startRow + 1);
                if (displayIndex) {
                    Cell cell = row.createCell(startCol);
                    cell.setCellValue(i + 1);
                    cell.setCellStyle(cellStyleCenter);
                }
                for (int j = 0; j < lstColumn.size(); j++) {
                    Cell cell = row.createCell(j + startCol + diff);
                    ExcelColumn column = lstColumn.get(j);
                    Object obj = lstData.get(i);
                    Field f = mapField.get(column.getColumn());
                    if (f != null) {
                        Object value = f.get(obj);
                        String text;
                        if (value instanceof Double) {
                            text = doubleToString((Double) value);
                        } else if (value instanceof Instant) {
                            text = instantToString((Instant) value, column.getPattern());
                        } else if (value instanceof Date) {
                            text = dateToString((Date) value, column.getPattern());
                        } else {
                            text = objectToString(value);
                        }
                        cell.setCellValue(text);
                        this.setCellStyle(cell, column, cellStyleLeft, cellStyleRight, cellStyleCenter);
                    }
                }
            }
        }
    }

    private void setCellStyle(Cell cell, ExcelColumn column, CellStyle cellStyleLeft, CellStyle cellStyleRight, CellStyle cellStyleCenter) {
        if (ExcelColumn.ALIGN_MENT.CENTER.equals(column.getAlign())) {
            cell.setCellStyle(cellStyleCenter);
        }
        if (ExcelColumn.ALIGN_MENT.LEFT.equals(column.getAlign())) {
            cell.setCellStyle(cellStyleLeft);
        }
        if (ExcelColumn.ALIGN_MENT.RIGHT.equals(column.getAlign())) {
            cell.setCellStyle(cellStyleRight);
        }
    }

    private String instantToString(Instant value, String pattern) {
        if (pattern != null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
            return dtf.format(LocalDateTime.ofInstant(value, ZoneId.of(Constants.TIME_ZONE_DEFAULT)));
        }
        return "";
    }

    private String dateToString(Date value, String pattern) {
        if (pattern != null) {
            SimpleDateFormat dtf = new SimpleDateFormat(pattern);
            return dtf.format(value);
        }
        return "";
    }

    private String objectToString(Object value) {
        return (value == null) ? "" : value.toString();
    }

    private String doubleToString(Double value) {
        if (value == null) {
            return "";
        }
        String result = doubleFormat.format(value);
        if (result.endsWith(".0")) {
            result = result.split("\\.")[0];
        }
        return result;
    }

    private int getDiff(Boolean displayIndex) {
        return Boolean.TRUE.equals(displayIndex) ? 1 : 0;
    }

    public String getRowString(Row row, int col) {
        String result = null;
        Cell cell = row.getCell(col);
        if (cell != null) {
            switch (cell.getCellType()) {
                case NUMERIC:
                    result = new DecimalFormat("#.#").format(cell.getNumericCellValue());
                    if (result.endsWith(".0")) {
                        result = result.substring(0, result.lastIndexOf("."));
                    }
                    break;
                case STRING:
                    result = cell.getStringCellValue();
                    break;
                default:
                    break;
            }
        }
        return result;
    }

}
