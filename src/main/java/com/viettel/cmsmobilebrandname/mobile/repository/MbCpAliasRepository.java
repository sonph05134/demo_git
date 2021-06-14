package com.viettel.cmsmobilebrandname.mobile.repository;

import com.viettel.cmsmobilebrandname.mobile.repository.custom.MbCpAliasCustomRepository;
import com.viettel.smsbrandname.domain.CpAlias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MbCpAliasRepository extends JpaRepository<CpAlias, Long>, MbCpAliasCustomRepository {

    @Query(value = "select cp from CpAlias cp " +
        "where cp.cpAliasId = :cpAliasId and cp.cpId = :cpId")
    CpAlias checkExistCpAliasByIdAndCpAliasId(@Param("cpId") Long cpId,@Param("cpAliasId") Long cpAliasId);

}
