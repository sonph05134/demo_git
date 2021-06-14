package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.domain.Cp;
import com.viettel.smsbrandname.service.dto.ComboBean;
import com.viettel.smsbrandname.service.dto.VirtualAccountDTO;
import org.springframework.data.domain.Page;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface VirtualAccountService {

    List<ComboBean> getCPPre(String currency, Long status, Principal principal);

    Page<VirtualAccountDTO> getBalanceHisByCondition(VirtualAccountDTO virtualAccount, Principal principal);

    ByteArrayInputStream downloadCPAttachFile(String fileName) throws IOException;

    ByteArrayInputStream export(VirtualAccountDTO virtualAccount, Principal principal);

    void refund(Principal principal, VirtualAccountDTO virtual);

    Cp getCPById(Long cpId);

    void updateTrans(VirtualAccountDTO virtual, Principal principal);

    void retryTrans(VirtualAccountDTO virtual);
}
