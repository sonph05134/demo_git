package com.viettel.smsbrandname.service.impl;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.*;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.Cp;
import com.viettel.smsbrandname.domain.ProvinceUsers;
import com.viettel.smsbrandname.repository.BalanceHisCustomRepository;
import com.viettel.smsbrandname.repository.CpRepository;
import com.viettel.smsbrandname.repository.ProvinceBCCSCustomRepository;
import com.viettel.smsbrandname.repository.ProvinceUserRepository;
import com.viettel.smsbrandname.service.BccsService;
import com.viettel.smsbrandname.service.ChargingService;
import com.viettel.smsbrandname.service.UserService;
import com.viettel.smsbrandname.service.VirtualAccountService;
import com.viettel.smsbrandname.service.dto.ComboBean;
import com.viettel.smsbrandname.service.dto.ProvinceUsersDTO;
import com.viettel.smsbrandname.service.dto.VirtualAccountDTO;
import com.viettel.smsbrandname.service.dto.response.BccsResponse;
import com.viettel.smsbrandname.service.mapper.CpMapper;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VirtualAccountServiceImpl extends StandardizeLogging implements VirtualAccountService {


    private final Logger log = LoggerFactory.getLogger(VirtualAccountServiceImpl.class);

    @Autowired
    private CpRepository cpRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BccsService bccsService;

    @Autowired
    private ProvinceBCCSCustomRepository provinceBCCSCustomRepository;

    @Autowired
    private ChargingService chargingService;

    @Autowired
    private ProvinceUserRepository provinceUserRepository;

    @Autowired
    private BalanceHisCustomRepository balanceHisRepo;

    @Autowired
    ExcelUtils excelUtils;

    @Value("${folder-dir.cp-attach-dir}")
    private String cpAttachDir;

    private String payMethodNote = ".PayMethod: ";

    public VirtualAccountServiceImpl() {
        super(VirtualAccountServiceImpl.class);
    }

    @Override
    public Page<VirtualAccountDTO> getBalanceHisByCondition(VirtualAccountDTO virtualAccount, Principal principal) {
        ProvinceUsersDTO optional = cpRepository.getLoginPermission(getUserName(principal));
        return cpRepository.findByCondition(virtualAccount, optional, false);
    }

    public List<ComboBean> getCPPre(String currency, Long status, Principal principal) {
        Optional<ProvinceUsers> optional = provinceUserRepository.getByUserNameContains(getUserName(principal));
        return cpRepository.findAllCpPreProvince(currency, status, optional);
    }

    @Override
    public ByteArrayInputStream downloadCPAttachFile(String fileName) throws IOException {
        File file = new File(cpAttachDir + File.separator + fileName);
        if (!file.exists()) {
            throw new BusinessException(ErrorCodeResponse.FILE_NOT_EXIST);
        }
        return new ByteArrayInputStream(
            FileCopyUtils.copyToByteArray(file));
    }

    private String getUserName(Principal principal) {
        if (principal instanceof AbstractAuthenticationToken) {
            return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal).getLogin();
        }
        return null;
    }

    public ByteArrayInputStream export(VirtualAccountDTO virtualAccount, Principal principal) {
        try {
            ProvinceUsersDTO optional = cpRepository.getLoginPermission(getUserName(principal));
            List<VirtualAccountDTO> lst = cpRepository.findByCondition(virtualAccount, optional, true).getContent();

            lst = lst.stream().map(virtual -> {
                if ("-1".equals(virtual.getBranch())) {
                    virtual.setBranch("");
                }
                String balance = "virtual.account.type.qc";
                if (virtual.getBalanceType() == 0) {
                    balance = "virtual.account.type.cc";
                }
                String status = "virtual.account.status.active";
                if (virtual.getStatus() == 0) {
                    status = "virtual.account.status.inactive";
                }
                virtual.setStatusName(Translator.toLocale(status));
                virtual.setBalanceName(Translator.toLocale(balance));
                return virtual;
            }).collect(Collectors.toList());
            List<String> lstHeader = Arrays.asList(Translator.toLocale("virtual.account.cpCode"),
                Translator.toLocale("virtual.account.cpName"),
                Translator.toLocale("virtual.account.currency"),
                Translator.toLocale("virtual.account.balanceType"),
                Translator.toLocale("virtual.account.status"),
                Translator.toLocale("virtual.account.amount"),
                Translator.toLocale("virtual.account.balanceBefore"),
                Translator.toLocale("virtual.account.balanceAfter"),
                Translator.toLocale("virtual.account.currentBalance"),
                Translator.toLocale("virtual.account.note"),
                Translator.toLocale("virtual.account.dateChange"),
                Translator.toLocale("virtual.account.rate"),
                Translator.toLocale("virtual.account.user"),
                Translator.toLocale("virtual.account.discount"));
            List<String> lstColumn = Arrays.asList(
                "cpCode",
                "cpName",
                "currency",
                "balanceName",
                "statusName",
                "amount",
                "balanceBefore",
                "balanceAfter",
                "currentBalance",
                "note",
                "dateChange",
                "rate",
                "userModified",
                "discount");
            String title = Translator.toLocale("virtual.account.balanceHistory");
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
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30));
            List<Integer> lstAlign = new ArrayList<>();
            lstAlign.addAll(Arrays.asList(
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_RIGHT,
                ExcelUtils.ALIGN_RIGHT,
                ExcelUtils.ALIGN_RIGHT,
                ExcelUtils.ALIGN_RIGHT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_RIGHT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_RIGHT,
                ExcelUtils.ALIGN_LEFT));
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String fromDate = Translator.toLocale("excel.fromDate") + " " + df.format(virtualAccount.getFromDate());
            String toDate = Translator.toLocale("excel.toDate") + " " + df.format(virtualAccount.getToDate());
            String dateExport = Translator.toLocale("excel.date-export") + " " + df.format(new Date());
            String balanceHis = Translator.toLocale("excel.history-balance.sheetName");
            return excelUtils.export(balanceHis, lst, lstHeader, lstColumn, title, lstSize, lstAlign, fromDate, toDate, dateExport);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void refund(Principal principal, VirtualAccountDTO virtual) {
        Date dateExec = new Date();
        try {
            String userName = getUserName(principal);
            BigDecimal zeroValue = new BigDecimal("0");
            String branchName = null;
            String currency = virtual.getCurrency() != null ? virtual.getCurrency() : "";
            Long cpId = Long.valueOf(virtual.getCpId());
            Integer balanceType = virtual.getBalanceType();
            BigDecimal amount = virtual.getAmountForCharge();
            Long branch = null;
            BigDecimal exchangeRate = virtual.getRate();
            MultipartFile attachFile = virtual.getFile();
            String fileName = attachFile.getOriginalFilename();
            //get cp
            Optional<Cp> optional = cpRepository.getByCpId(cpId);

            //check cp
            if (!optional.isPresent()) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "cpNotExists", dateExec);
                throw new BusinessException(ErrorCodeResponse.CP_NOT_EXIST);
            }
            Cp cp = optional.get();

            //Kiem tra CP da co tren data Webservice Charge hay chua. Neu chua thi return!
            Boolean checkCp = chargingService.checkExistsCP(cp.getCpCode());
            if (!checkCp) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "cpIsNotExists", dateExec);
                throw new BusinessException(ErrorCodeResponse.CP_NOT_EXIST_IN_CHARGING);
            }
            int validateAttachFile = FileUtils.validateAttachFile(virtual.getFile(), fileName);
            if (validateAttachFile == 24) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "wrongFileType", dateExec);
                throw new BusinessException(ErrorCodeResponse.FILE_WRONG_TYPE);
            } else if (validateAttachFile == 25) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "wrongFileSize20MB", dateExec);
                throw new BusinessException(ErrorCodeResponse.FILE_TOO_LARGE);
            }
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String outputAttachFileName = cp.getCpCode() + "_refund_"
                + date + "." + fileType;
            //copy file
            Boolean copyTmp = FileUtils.copyAttachFile(attachFile, cpAttachDir, outputAttachFileName);
            if (!copyTmp) {
                log.error("Khong copy duoc file dinh kem len server");
                throw new BusinessException(ErrorCodeResponse.SAVE_FILE_FAIL);
            }

            BigDecimal usdLength = new BigDecimal(Constants.USD_LENGTH);

            if (!"".equals(currency) && !currency.equals(Constants.HEADER_VALUE_NUMBER)) {

                amount = DataUtil.getRealAmountForCharge(amount, currency);

                if (amount.toString().length() > 20) {
                    writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "amountGreaterThan20", dateExec);
                    throw new BusinessException(ErrorCodeResponse.AMOUNT_GREATER_20);
                }

                //phucvm1: lay tai khoan hien tai
                BigDecimal currentBalance = null;
                if (balanceType == Constants.CUSTOMER_CARE) {
                    currentBalance = getRealBalance(cp.getBalance(), cp.getCurrency());
                } else if (balanceType == Constants.ADVERTISE) {
                    currentBalance = getRealBalance(cp.getAdBalance(), cp.getCurrency());
                }
                if (currentBalance != null && currency.equalsIgnoreCase(Constants.CURRENCY_USD)) {
                    currentBalance = currentBalance.multiply(usdLength);
                }

                if (currentBalance != null && currentBalance.toString().contains(".")) {
                    currentBalance = new BigDecimal(currentBalance.toString().substring(0, currentBalance.toString().indexOf(".")));
                }

                BigDecimal balanceAfter = currentBalance != null ? currentBalance.add(amount) : null;

                if (balanceAfter == null) {
                    writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "refundFail", dateExec);
                    throw new BusinessException(ErrorCodeResponse.REFUND_FAIL);
                }

                if (balanceAfter.toString().length() > 20) {
                    writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "balanceGreaterThan20", dateExec);
                    throw new BusinessException(ErrorCodeResponse.AMOUNT_GREATER_20);
                }

                if (amount.compareTo(zeroValue) == -1 && balanceAfter.compareTo(zeroValue) == -1) {
                    writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "errorReduceMoney", dateExec);
                    throw new BusinessException(ErrorCodeResponse.REDUCE_MONEY_FAIL);
                }

                long orderNoSeq = bccsService.getOrderNoSeq("ORDERNO_SEQ");

                boolean chargeResult = chargingService.changeBalance(cp.getCpCode(), amount, 2,
                    "refund --- seqNo: " + orderNoSeq, exchangeRate.longValue(), 0,
                    outputAttachFileName, null, String.valueOf(branch), userName, balanceType.intValue(),
                    branchName, 0, 0);
                if (!chargeResult) {
                    writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "refundFail", dateExec);
                    throw new BusinessException(ErrorCodeResponse.REFUND_FAIL);
                } else {
                    writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "refundOk", dateExec);
                }
            }
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, dateExec);
            throw ex;
        }
    }

    private BigDecimal getRealBalance(Long balance, String currency) {
        if (currency != null && currency.equals(Constants.USD) && balance != null) {
            return BigDecimal.valueOf(balance).divide(new BigDecimal(Constants.USD_LENGTH));
        } else if (balance == null) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(balance);
    }

    @Override
    public Cp getCPById(Long cpId) {
        Optional<Cp> optional = cpRepository.getByCpId(cpId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new BusinessException(ErrorCodeResponse.CP_NOT_EXIST);
    }

    @Override
    public void updateTrans(VirtualAccountDTO virtual, Principal principal) {
        try {
            String userName = getUserName(principal);
            Long cpId = virtual.getCpId(); //phucvm1: get value cua cpCode tren form jsp la cpId
            Long balanceHisId = virtual.getId();
            String branch = virtual.getBranch();
            BigDecimal exchangeRate = virtual.getRate();
            String discount = virtual.getDiscount();
            Long discountValue = Long.valueOf(discount.replaceAll("%", ""));
            Integer balanceType = virtual.getBalanceType();
            BigDecimal amountTrans = virtual.getAmount();
            String changeNote = virtual.getNote();
            String payMethod = DataUtil.validString(virtual.getPayMethod()) ? virtual.getPayMethod() : Constants.TRANSFER;
            //get cp
            Optional<Cp> optional = cpRepository.getByCpId(cpId);

            //check cp
            if (!optional.isPresent()) {
                throw new BusinessException(ErrorCodeResponse.CP_NOT_EXIST);
            }

            Cp cp = optional.get();

            //kiem tra giao dich lan dau khong
            boolean isFirstTrans = cpRepository.isFirtTrans(cpId, balanceHisId);

            amountTrans = DataUtil.getRealAmountForBCCS(amountTrans, Constants.CURRENCY_USD, exchangeRate);//Currency cho la USD hay VND deu duoc.

            BigDecimal discountAmountTrans = DataUtil.getRealDiscountAmount(amountTrans, discountValue);

            long orderNoSeq = bccsService.getOrderNoSeq("ORDERNO_SEQ");

            changeNote = changeNote.substring(0, changeNote.indexOf("seqNo:") + "seqNo:".length())
                + " " + orderNoSeq + payMethodNote + payMethod;

            //phucvm1: goi ham luu giao dich tam BCCS
            String saleTransId;
            //phucvm1: goi ham ws
            String custName = cp.getCpName() != null ? cp.getCpName() : "";
            String companyName = cp.getRepresentative() != null ? cp.getRepresentative() : "";
            String address = cp.getAddress() != null ? cp.getAddress() : "";
            String tin = cp.getTaxCode() != null ? cp.getTaxCode() : "";
            String contractNo = cp.getCpCode() != null ? cp.getCpCode() : "";
            String email = cp.getEmail() != null ? cp.getEmail() : "";
            String orderNoBccs = String.valueOf(orderNoSeq);
            String branchId = branch;
            XMLGregorianCalendar saleTransDate = bccsService.getCurrentDate();
            String telNumber = "";
            String saleTransType = balanceType.equals(0L) ? Constants.BCCS_SALE_TRANS_TYPE.CUSTOMERCARE : Constants.BCCS_SALE_TRANS_TYPE.ADVERTISE;
            saleTransId = bccsService.saveSaleTransBulkSmsFull(custName, companyName, address,
                tin, contractNo, orderNoBccs, amountTrans.toString(), discountAmountTrans.toString(),
                branchId, saleTransDate, telNumber, saleTransType, email, payMethod);

            if (saleTransId != null && !"".equals(saleTransId)) {
                if (receiveBulkSmsResult(saleTransId, balanceType)) {
                    if (balanceHisRepo.updateBalanceHis(virtual, isFirstTrans, saleTransId, userName)) {
                        return;
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        throw new BusinessException(ErrorCodeResponse.UPDATE_TRANS_FAIL);
    }

    private boolean receiveBulkSmsResult(String saleTransId, Integer balanceType) {
        boolean result = false;
        int receiveResult;
        if (balanceType == 0) {
            receiveResult = bccsService.receiveBulkSmsResult(saleTransId);
        } else {
            receiveResult = bccsService.receiveAmsResult(saleTransId);
        }
        if (receiveResult == 0) {
            result = true;
        }

        return result;
    }

    @Override
    public void retryTrans(VirtualAccountDTO virtual) {
        String changeNote = virtual.getNote();
        String saleTransId = virtual.getSaleTransId();
        if (saleTransId != null && !"".equals(saleTransId.trim())) { //da co saleTransId -> ghi nhan giao dich
            if (receiveBulkSmsResult(saleTransId, virtual.getBalanceType())) {
                if (balanceHisRepo.updateIsBccsOk(virtual.getId(), null)) {
                    return;
                }
            }
        } else { //chua co saleTransId -> goi giao dich tam va ghi nhan giao dich
            Optional<Cp> optional = cpRepository.getByCpId(virtual.getCpId());

            if (!optional.isPresent()) {
                throw new BusinessException(ErrorCodeResponse.CP_NOT_EXIST);
            }
            Cp cp = optional.get();
            BigDecimal amountTrans = DataUtil.getRealAmountForBCCS(virtual.getAmount(), Constants.CURRENCY_USD, virtual.getRate());//Currency cho la USD hay VND deu duoc.

            BigDecimal discountAmountTrans = DataUtil.getRealDiscountAmount(amountTrans, Long.valueOf(virtual.getDiscount().replaceAll("%", "")));
            //phucvm1: goi ham ws
            String custName = cp.getCpName() != null ? cp.getCpName() : "";
            String companyName = cp.getRepresentative() != null ? cp.getRepresentative() : "";
            String address = cp.getAddress() != null ? cp.getAddress() : "";
            String tin = cp.getTaxCode() != null ? cp.getTaxCode() : "";
            String contractNo = cp.getCpCode() != null ? cp.getCpCode() : "";
            String orderNoBccs = changeNote.substring(changeNote.indexOf("seqNo:") + "seqNo:".length()).trim();
            XMLGregorianCalendar saleTransDate = bccsService.getCurrentDate();
            String telNumber = "";
            String saleTransType = virtual.getBalanceType() == 0 ? Constants.BCCS_SALE_TRANS_TYPE.CUSTOMERCARE : Constants.BCCS_SALE_TRANS_TYPE.ADVERTISE;
            String email = DataUtil.validString(cp.getEmail()) ? cp.getEmail() : "";
            String branchId = virtual.getBranch();
            BccsResponse bccsResponse = bccsService.saveSaleTransBulkSmsFullToBccsResult(custName, companyName, address,
                tin, contractNo, orderNoBccs, amountTrans.toString(), discountAmountTrans.toString(),
                branchId, saleTransDate, telNumber, saleTransType, email, virtual.getPayMethod());

            if (bccsResponse == null) {
                throw new BusinessException(ErrorCodeResponse.RETRY_FAIL);
            } else if ("0".equals(bccsResponse.getResponseCode())) {
                saleTransId = bccsResponse.getResult();
            } else {
                throw new BusinessException(bccsResponse.getResponseCode(), bccsResponse.getResult());
            }

            //goi giao dich that
            if (saleTransId != null && !"".equals(saleTransId) && !"null".equals(saleTransId)) {
                if (receiveBulkSmsResult(saleTransId, virtual.getBalanceType())) {
                    if (balanceHisRepo.updateIsBccsOk(virtual.getId(), Long.valueOf(saleTransId))) {
                        return;
                    }
                }
            }
        }
        throw new BusinessException(ErrorCodeResponse.RETRY_FAIL);
    }
}
