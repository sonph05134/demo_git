/*
 * Copyright (C) 2010 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.smsbrandname.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

/**
 * @author thangdd8@viettel.com.vn
 * @version 1.0
 * @since Apr 12, 2010
 */
public class FileUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static int validateAttachFile(MultipartFile attachFile, String fileName) {
        String fileExt = "7z,rar,zip,txt,ppt,pptx,doc,docx,xls,xlsx,pdf,jpg,jpeg,png,bmp,gif";
        List<String> lstValidFileExt = Arrays.asList(fileExt.split(","));

        String fileType = fileName.toLowerCase().substring(fileName.lastIndexOf(".") + 1);
        if (!lstValidFileExt.contains(fileType)) {
            return 24; //24-wrongFileType
        }

        double bytes = attachFile.getSize();
        double kilobytes = (bytes / 1024);
        double megabytes = (kilobytes / 1024);
        if (megabytes > 20) {
            return 25; //25-wrongFileSize20MB
        }
        return 0; //0-validate ok
    }


    public static Boolean copyAttachFile(MultipartFile file, String attachFileDir, String fileName) {
        try {
            File dir = new File(attachFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Path targetLocation = Paths.get(attachFileDir + File.separator + fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("File copied successful: " + attachFileDir + File.separator + fileName);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public static Boolean deleteAttachFile(String attachFileDir, String fileName) {
        try {
            File fileDelete = new File(attachFileDir + File.separator + fileName);
            fileDelete.delete();
            log.info("Delete file successful: " + attachFileDir + File.separator + fileName);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

}
