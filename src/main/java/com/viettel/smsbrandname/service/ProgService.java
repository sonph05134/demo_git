package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.domain.Prog;
import com.viettel.smsbrandname.service.dto.CareCustomerSmsSearchDTO;
import com.viettel.smsbrandname.service.dto.ComboBean;
import com.viettel.smsbrandname.service.dto.CpDTO;
import com.viettel.smsbrandname.service.dto.cpprog.CPProgAdvertisementSearchDTO;
import com.viettel.smsbrandname.service.dto.cpprog.ProgAdApproveHisDTO;
import com.viettel.smsbrandname.service.dto.response.CPProgAdvertisementResponseDTO;
import org.springframework.data.domain.Page;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.security.Principal;
import java.sql.Blob;
import java.util.List;

public interface ProgService {
    List<ComboBean> getListCheckBox();

    CPProgAdvertisementResponseDTO searchAdvertisement(CPProgAdvertisementSearchDTO cpProgAdvertisementSearchDTO);

    Page<CareCustomerSmsSearchDTO> search(CareCustomerSmsSearchDTO careCustomerSmsSearchDTO);

    ByteArrayInputStream export(CPProgAdvertisementSearchDTO cpProgAdvertisementSearchDTO, Principal principal);

    CareCustomerSmsSearchDTO getCareCustomerSmsSearchDTOById(CareCustomerSmsSearchDTO careCustomerSmsSearchDTO);

    void updateSMSgAdvertisement(List<ProgAdApproveHisDTO> progAdApproveHisDTO, boolean isRefuse) throws Exception;

    Prog updateProg(CareCustomerSmsSearchDTO careCustomerSmsSearchDTO, boolean isRefuse);

    ByteArrayInputStream exportListCareCustmoerSms(CareCustomerSmsSearchDTO careCustomerSmsSearchDTO);

    byte[] geTattachFile(Long progId) throws Exception;

}
