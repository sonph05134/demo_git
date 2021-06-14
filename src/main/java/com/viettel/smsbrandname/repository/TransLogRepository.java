package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.TransLog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the TransLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransLogRepository extends JpaRepository<TransLog, Long>, TransLogRepositoryCustom {
    @Query(value = "SELECT   cp.cp_code,\n" +
        "             cp.cp_name,\n" +
        "             (CASE tl.chanel\n" +
        "                WHEN 0 THEN 'SMPP'\n" +
        "                WHEN 1 THEN 'Webservice'\n" +
        "                WHEN 2 THEN 'CMS'\n" +
        "             END)as chanel,\n" +
        "             tl.amount,\n" +
        "             tl.balance_before,\n" +
        "             tl.balance_after,\n" +
        "             tl.trans_note,\n" +
        "             TO_CHAR(tl.trans_time, 'yyyy-mm-dd hh24:mi:ss') as trans_time,\n" +
        "             cp.currency\n" +
        "      FROM       trans_log tl\n" +
        "             JOIN\n" +
        "                 cp\n" +
        "             ON tl.cp_id = cp.cp_id\n" +
        "     WHERE       cp.status = 1\n" +
        "             AND  cp.charge_type = 'PRE'\n" +
        "AND tl.trans_time >= TO_DATE(:fromDate ,'dd/MM/yyyy')\n" +
        "AND tl.trans_time <= TO_DATE(:toDate,'dd/MM/yyyy')\n" +
        "AND (:cpCode is null or cp.cp_code = :cpCode )\n" +
        "AND (:chanel is null or tl.chanel = to_number(:chanel) )\n" +
        "AND (:currency is null or cp.currency = :currency ) " +
        " ORDER BY tl.trans_time desc",
        countQuery = "  SELECT   count(1) " +
            "      FROM       trans_log tl\n" +
            "             JOIN\n" +
            "                 cp\n" +
            "             ON tl.cp_id = cp.cp_id\n" +
            "     WHERE       cp.status = 1\n" +
            "             AND  cp.charge_type = 'PRE'\n" +
            "AND tl.trans_time >= TO_DATE(:fromDate ,'dd/MM/yyyy')\n" +
            "AND tl.trans_time <= TO_DATE(:toDate,'dd/MM/yyyy')\n" +
            "AND (:cpCode is null or cp.cp_code = :cpCode )\n" +
            "AND (:chanel is null or tl.chanel = to_number(:chanel) )\n" +
            "AND (:currency is null or cp.currency = :currency ) ",
        nativeQuery = true)
    Page<Object[]> search(@Param("fromDate") String fromDate,
                          @Param("toDate") String toDate,
                          @Param("cpCode") String cpCode,
                          @Param("chanel") Long chanel,
                          @Param("currency") String currency,
                          Pageable pageable);

    @Query(value = "SELECT   cp.cp_code,\n" +
        "             cp.cp_name,\n" +
        "             (CASE tl.chanel\n" +
        "                WHEN 0 THEN 'SMPP'\n" +
        "                WHEN 1 THEN 'Webservice'\n" +
        "                WHEN 2 THEN 'CMS'\n" +
        "             END)as chanel,\n" +
        "             tl.amount,\n" +
        "             tl.balance_before,\n" +
        "             tl.balance_after,\n" +
        "             tl.trans_note,\n" +
        "             TO_CHAR(tl.trans_time, 'yyyy-mm-dd hh24:mi:ss') as trans_time,\n" +
        "             cp.currency\n" +
        "      FROM       trans_log tl\n" +
        "             JOIN\n" +
        "                 cp\n" +
        "             ON tl.cp_id = cp.cp_id\n" +
        "     WHERE       cp.status = 1\n" +
        "             AND  cp.charge_type = 'PRE'\n" +
        "AND tl.trans_time >= TO_DATE(:fromDate ,'dd/MM/yyyy')\n" +
        "AND tl.trans_time <= TO_DATE(:toDate,'dd/MM/yyyy')\n" +
        "AND (:cpCode is null or cp.cp_code = :cpCode )\n" +
        "AND (:chanel is null or tl.chanel = to_number(:chanel) )\n" +
        "AND (:currency is null or cp.currency = :currency ) " +
        " ORDER BY tl.trans_time desc",
        nativeQuery = true)
    List<Object[]> search(@Param("fromDate") String fromDate,
                          @Param("toDate") String toDate,
                          @Param("cpCode") String cpCode,
                          @Param("chanel") Long chanel,
                          @Param("currency") String currency);
}
