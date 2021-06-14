package com.viettel.smsbrandname.repository;

import com.viettel.cmsmobilebrandname.mobile.dto.AliasCostDTO;
import com.viettel.smsbrandname.domain.AliasCost;
import com.viettel.smsbrandname.domain.Cp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AliasCostRepository extends JpaRepository<AliasCost, Long>, AliasCostRepositoryCustom {

    Optional<AliasCost> findFirstByAliasCostTypeAndAliasTelco(Long aliasCostType, String telco);

    @Query(value = "select al from AliasCost al where al.aliasTelco =:teLcoAlias and al.aliasCostType =:aliasType")
    AliasCost getInformationAliasCost(@Param("teLcoAlias") String teLcoAlias,@Param("aliasType") Long aliasType);
}
