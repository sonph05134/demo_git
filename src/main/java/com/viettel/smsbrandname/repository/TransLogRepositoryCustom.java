package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.service.dto.TransLogDTO;

import java.util.List;

public interface TransLogRepositoryCustom {

    List<TransLogDTO> searchHasfiter(String cpCode,Long chanel,String fromDate, String toDate, String currency);
}
