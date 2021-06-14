package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.service.dto.ComboBean;
import com.viettel.smsbrandname.service.dto.response.BccsResponse;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

public interface BccsService {
    String saveSaleTransBulkSms(String custName, String companyName, String address, String tin,
                                String contractNo, String orderNo, String amountTrans, String discountAmountTrans, String branchId,
                                XMLGregorianCalendar saleTransDate, String telNumber, String saleTransType);

    XMLGregorianCalendar getCurrentDate();

    Long getOrderNoSeq(String seq);

    String saveSaleTransBulkSmsFull(String custName, String companyName, String address, String tin,
                                    String contractNo, String orderNo, String amountTrans, String discountAmountTrans, String branchId,
                                    XMLGregorianCalendar saleTransDate, String telNumber, String saleTransType, String email, String payMethod);

    int receiveAmsResult(String amsSaleTransId);

    int receiveBulkSmsResult(String amsSaleTransId);

    BccsResponse saveSaleTransBulkSmsFullToBccsResult(String custName, String companyName, String address, String tin,
                                                      String contractNo, String orderNo, String amountTrans, String discountAmountTrans, String branchId,
                                                      XMLGregorianCalendar saleTransDate, String telNumber, String saleTransType, String email, String payMethod);
    List<ComboBean> getLstProject();

}
