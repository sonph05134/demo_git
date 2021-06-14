package com.viettel.smsbrandname.service;


import com.viettel.smsbrandname.domain.CfgFilterEntity;
import com.viettel.smsbrandname.service.dto.FilterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Autogen class: Lop thao tac danh sach Filter
 *
 * @author ToolGen
 * @date Thu Dec 10 17:32:22 ICT 2020
 */
public interface FilterService {


    public void deleteById(Long id);

     Page<CfgFilterEntity> search(FilterDTO filterDTO);

     void add(CfgFilterEntity entity);

}
