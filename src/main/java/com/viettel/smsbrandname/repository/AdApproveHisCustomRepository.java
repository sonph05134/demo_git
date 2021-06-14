package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.AdApproveHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdApproveHisCustomRepository extends JpaRepository<AdApproveHis, String> {
}
