package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.service.dto.ComboBean;
import com.viettel.smsbrandname.service.dto.cpprog.CPProgAdvertisementSearchDTO;
import com.viettel.smsbrandname.service.dto.CareCustomerSmsSearchDTO;
import com.viettel.smsbrandname.service.dto.CpDTO;
import com.viettel.smsbrandname.service.dto.response.CPProgAdvertisementResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.sql.Blob;
import java.util.List;

/**
 * Spring Data  repository for the Prog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgRepository {
    List<ComboBean> getListCheckBox();

    CPProgAdvertisementResponseDTO searchAdvertisement(CPProgAdvertisementSearchDTO cpProgSearchDTO, Long provinceid);

    Page<CareCustomerSmsSearchDTO> doSearch(CareCustomerSmsSearchDTO careCustomerSmsSearchDTO, Long provinceid, boolean isExport);

    CareCustomerSmsSearchDTO getCareCustomerSmsSearchDTOById(CareCustomerSmsSearchDTO careCustomerSmsSearchDTO);

    boolean updateProg(Long progId, Long status);
}

