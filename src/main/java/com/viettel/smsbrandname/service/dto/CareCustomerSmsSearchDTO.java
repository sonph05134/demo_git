package com.viettel.smsbrandname.service.dto;


import java.io.Serializable;
import java.util.Date;

public class CareCustomerSmsSearchDTO extends PageDTO implements Serializable{
    private Long stt;
    private Long progId;
    private Long cpId;
    private String progCode;
    private String cpName;
    private String cpCode;
    private String type;
    private String content;
    private Date createDate;
    private Date sentSchedule;
    private String statusIT;
    private String alias;
    private String link;
    private Long convertVN;
    private String groupNameList;
    private  String groupSex;
    private  String userName;

    public Long getStt() {
        return stt;
    }

    public void setStt(Long stt) {
        stt = stt;
    }

    public Long getConvertVN() {
        return convertVN;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long isConvertVN() {
        return convertVN;
    }

    public void setConvertVN(Long convertVN) {
        this.convertVN = convertVN;
    }

    public Long getProgId() {
        return progId;
    }

    public void setProgId(Long progId) {
        this.progId = progId;
    }

    public Long getCpId() {
        return cpId;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public String getProgCode() {
        return progCode;
    }

    public void setProgCode(String progCode) {
        this.progCode = progCode;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getCpCode() {
        return cpCode;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getSentSchedule() {
        return sentSchedule;
    }

    public void setSentSchedule(Date sentSchedule) {
        this.sentSchedule = sentSchedule;
    }

    public String getStatusIT() {
        return statusIT;
    }

    public void setStatusIT(String statusIT) {
        this.statusIT = statusIT;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getGroupNameList() {
        return groupNameList;
    }

    public void setGroupNameList(String groupNameList) {
        this.groupNameList = groupNameList;
    }

    public String getGroupSex() {
        return groupSex;
    }

    public void setGroupSex(String groupSex) {
        this.groupSex = groupSex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
