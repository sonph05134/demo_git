package com.viettel.cmsmobilebrandname.mobile.repository;

import com.viettel.smsbrandname.domain.CfgFilterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Autogen class Repository Interface: Lop thao tac danh sach Filter
 *
 * @author toolGen
 * @date Thu Dec 10 17:32:22 ICT 2020 ,

// */
public interface MBFilterRepository extends JpaRepository<CfgFilterEntity, Long>,JpaSpecificationExecutor<CfgFilterEntity> {


    @org.springframework.data.jpa.repository.Query(
        value = "select cf.keyword from" +
        " CfgFilterEntity cf" +
        " where lower(cf.keyword) like lower(concat('%', :keyWord,'%'))")
    String findKeyword(@Param("keyWord") String keyWord);



}
