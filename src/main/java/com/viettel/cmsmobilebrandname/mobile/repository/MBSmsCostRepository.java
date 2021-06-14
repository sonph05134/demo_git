package com.viettel.cmsmobilebrandname.mobile.repository;

import com.viettel.cmsmobilebrandname.mobile.repository.custom.MBSmsCostCustomRepository;
import com.viettel.smsbrandname.domain.SMSCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface MBSmsCostRepository extends JpaRepository<SMSCost,Long>, MBSmsCostCustomRepository {


}
