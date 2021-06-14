package com.viettel.smsbrandname.service.dto.cpprog;

import com.viettel.smsbrandname.commons.DataUtil;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;


public class CPProgResultDTO {


    private  Long stt;
    private  Long progId;
    private String code;
    private String allas;
    private  String  groupName;
    private  Long catId;
    private  String content;
    private  String status;
    private  String tested;
    private  String createDate;
    private  String createUser;
    private  String isAdminUpdoad;
    private  String startDate;
    private  String finishDate;
    private  String maxSMS;
    private String type;
    private  String link;
    private  String totalSub;
    private  String convertVn;
    private  String sendDay;
    private  String exceptionDay;
    private  String startTimeZone;
    private  String finishTimeZone;
    private String needApproveRun;
    private  String catName;
    private String preFix;
    private String sufFix;
    private  String sentTimeZone;



    public static List<CPProgResultDTO> toDto(List<Object[]> objects) {

List<CPProgResultDTO> result = new ArrayList<>();
        if(!DataUtil.isNullOrEmpty(objects)){
           objects.stream().forEach(obj -> {
               int i = 0;
               CPProgResultDTO dto = new CPProgResultDTO();
               dto.setStt(DataUtil.safeToLong(obj[i++]));
               dto.setProgId(DataUtil.safeToLong(obj[i++]));
               dto.setCode(DataUtil.safeToString(obj[i++]));
               dto.setAllas(DataUtil.safeToString(obj[i++]));
               dto.setGroupName(DataUtil.safeToString(obj[i++]));
               dto.setCatId(DataUtil.safeToLong(obj[i++]));
               dto.setContent(DataUtil.safeToString(obj[i++]));
               dto.setStatus(DataUtil.safeToString(obj[i++]));
               dto.setTested(DataUtil.safeToString(obj[i++]));
               dto.setCreateDate(DataUtil.safeToString(obj[i++]));
               dto.setCreateUser(DataUtil.safeToString(obj[i++]));
               dto.setIsAdminUpdoad(DataUtil.safeToString(obj[i++]));
               dto.setStartDate(DataUtil.safeToString(obj[i++]));
               dto.setFinishDate(DataUtil.safeToString(obj[i++]));
               dto.setMaxSMS(DataUtil.safeToString(obj[i++]));
               dto.setType(DataUtil.safeToString(obj[i++]));
               dto.setLink(DataUtil.safeToString(obj[i++]));
               dto.setTotalSub(DataUtil.safeToString(obj[i++]));
               dto.setConvertVn(DataUtil.safeToString(obj[i++]));
               dto.setSendDay(DataUtil.safeToString(obj[i++]));
               dto.setExceptionDay(DataUtil.safeToString(obj[i++]));
               dto.setStartTimeZone(DataUtil.safeToString(obj[i++]));
               dto.setFinishTimeZone(DataUtil.safeToString(obj[i++]));
               dto.setNeedApproveRun(DataUtil.safeToString(obj[i++]));
                dto.setCatName(DataUtil.safeToString(obj[i++]));
               dto.setPreFix(DataUtil.safeToString(obj[i++]));
               dto.setSufFix(DataUtil.safeToString(obj[i++]));
               dto.setSentTimeZone(DataUtil.safeToString(obj[i++]));



               result.add(dto);
           });
        }
        return  result;
    }

    public Long getStt() {
        return stt;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }

    public Long getProgId() {
        return progId;
    }

    public void setProgId(Long progId) {
        this.progId = progId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAllas() {
        return allas;
    }

    public void setAllas(String allas) {
        this.allas = allas;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTested() {
        return tested;
    }

    public void setTested(String tested) {
        this.tested = tested;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getIsAdminUpdoad() {
        return isAdminUpdoad;
    }

    public void setIsAdminUpdoad(String isAdminUpdoad) {
        this.isAdminUpdoad = isAdminUpdoad;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getMaxSMS() {
        return maxSMS;
    }

    public void setMaxSMS(String maxSMS) {
        this.maxSMS = maxSMS;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTotalSub() {
        return totalSub;
    }

    public void setTotalSub(String totalSub) {
        this.totalSub = totalSub;
    }

    public String getConvertVn() {
        return convertVn;
    }

    public void setConvertVn(String convertVn) {
        this.convertVn = convertVn;
    }

    public String getSendDay() {
        return sendDay;
    }

    public void setSendDay(String sendDay) {
        this.sendDay = sendDay;
    }

    public String getExceptionDay() {
        return exceptionDay;
    }

    public void setExceptionDay(String exceptionDay) {
        this.exceptionDay = exceptionDay;
    }

    public String getStartTimeZone() {
        return startTimeZone;
    }

    public void setStartTimeZone(String startTimeZone) {
        this.startTimeZone = startTimeZone;
    }

    public String getFinishTimeZone() {
        return finishTimeZone;
    }

    public void setFinishTimeZone(String finishTimeZone) {
        this.finishTimeZone = finishTimeZone;
    }

    public String getNeedApproveRun() {
        return needApproveRun;
    }

    public void setNeedApproveRun(String needApproveRun) {
        this.needApproveRun = needApproveRun;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getPreFix() {
        return preFix;
    }

    public void setPreFix(String preFix) {
        this.preFix = preFix;
    }

    public String getSufFix() {
        return sufFix;
    }

    public void setSufFix(String sufFix) {
        this.sufFix = sufFix;
    }

    public String getSentTimeZone() {
        return sentTimeZone;
    }

    public void setSentTimeZone(String sentTimeZone) {
        this.sentTimeZone = sentTimeZone;
    }


}
