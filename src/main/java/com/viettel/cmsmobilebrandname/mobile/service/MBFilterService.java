package com.viettel.cmsmobilebrandname.mobile.service;


import com.viettel.cmsmobilebrandname.mobile.dto.FilterDTOMB;
import com.viettel.smsbrandname.service.FilterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Autogen class: Lop thao tac danh sach Filter
 *
 * @author ToolGen
 * @date Thu Dec 10 17:32:22 ICT 2020
 */
public interface MBFilterService {



    Page<FilterDTOMB> searchFilter(FilterDTOMB filterDTOMB, Pageable pageable);

    void delete(Long id);

    boolean updateFilterForMobile(FilterDTOMB filterDTOMB);

    boolean addFilterForMobile(FilterDTOMB filterDTOMB);


}
