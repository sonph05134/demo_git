package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.domain.Cp;
import com.viettel.smsbrandname.service.dto.CpGroupSubDTO;
import com.viettel.smsbrandname.service.dto.CpGroupSubForm;
import com.viettel.smsbrandname.service.dto.ExcelColumn;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FileReader {
    default String getSafeFileExport(String folder, String fileNameOut) {
        File folderOut = new File(folder);
        if (!folderOut.exists()) {
            folderOut.mkdirs();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM_ddHHmmss_");
        String strCurTimeExp = dateFormat.format(new Date());
        return strCurTimeExp + fileNameOut;

    }

    List<CpGroupSubDTO> readFile(String filename, InputStream inputStream, Long cpId, Optional<Cp> cpOptional,
                                 List<String> lstPrefix, Long cpGroupId, List<ExcelColumn> lstColumn) throws Exception;

    ByteArrayInputStream writeFileResult(List<CpGroupSubDTO> lstData, CpGroupSubForm cpGroupSubForm)  throws Exception;

}
