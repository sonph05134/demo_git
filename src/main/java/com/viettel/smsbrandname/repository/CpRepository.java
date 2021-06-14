package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.Cp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CpRepository extends JpaRepository<Cp, Long>, CpCustomRepository {

    Optional<Cp> getByCpId(Long cpId);

    @Query(value = "select a from Cp a where a.accountType = 1 and a.status = 1" +
        " and (:provinceId is null or a.provinceId = :provinceId )" +
        " and (:provinceUserId is null or a.provinceUserId = :provinceUserId)" +
        " order by a.cpCode asc ")
    List<Cp> findAllByProvince(@Param("provinceId") Long provinceId, @Param("provinceUserId") Long provinceUserId);

    @Query(value = "select a from Cp a where a.accountType = 1 and a.status = 1" +
        " and (:provinceId is null or a.provinceId = :provinceId )" +
        " order by a.cpCode asc ")
    List<Cp> findAllByProvince(@Param("provinceId") Long provinceId);

    Optional<Cp> findFirstByCpIdAndStatus(Long cpId, Long Status);

    Optional<Cp> findFirstByUserNameAndStatus(String userName, Long status);
}
