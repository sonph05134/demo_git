package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.AdminMsisdn;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the AdminMsisdn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminMsisdnRepository extends JpaRepository<AdminMsisdn, Long>, IsdnCustomRepository {
}
