package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.CpGroupSub;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CpGroupSub entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CpGroupSubRepository extends JpaRepository<CpGroupSub, Long> {
    @Query(value = "SELECT a.cp_group_sub_id , a.cp_id , a.cp_code , " +
        " a.msisdn, a.note, a.create_date , a.update_date , a.name, " +
        " a.address, a.sex, a.birthday, a.code, b.cp_group_name , a.cp_group_id , c.free_sms " +
        " FROM cp_group_sub a JOIN cp_group b ON a.cp_group_id = b.cp_group_id  " +
        " JOIN cp_group_sub_limit lm ON a.cp_id = lm.cp_id and a.msisdn = lm.msisdn " +
        " JOIN cp c ON a.cp_id = c.cp_id " +
        " WHERE a.cp_group_id = :cpGroupId" +
        " and (:name is null or lower(a.name) like :name escape '\\' )" +
        " and (:msisdn is null or lower(a.msisdn) like :msisdn escape '\\' )", nativeQuery = true)
    List<Object[]> onExport(@Param("msisdn") String msisdn, @Param("name") String name, @Param("cpGroupId") Long cpGroupId);

    @Query(value = "SELECT a.cp_group_sub_id , a.cp_id , a.cp_code , " +
        " a.msisdn, a.note, a.create_date , a.update_date , a.name, " +
        " a.address, a.sex, a.birthday, a.code, b.cp_group_name , a.cp_group_id , c.free_sms " +
        " FROM cp_group_sub a JOIN cp_group b ON a.cp_group_id = b.cp_group_id  " +
        " JOIN cp_group_sub_limit lm ON a.cp_id = lm.cp_id and a.msisdn = lm.msisdn " +
        " JOIN cp c ON a.cp_id = c.cp_id " +
        " WHERE a.cp_group_id = :cpGroupId" +
        " and (:name is null or lower(a.name) like :name escape '\\' )" +
        " and (:msisdn is null or lower(a.msisdn) like :msisdn escape '\\' )",
        countQuery = "SELECT count(a.cp_group_sub_id) " +
            " FROM cp_group_sub a JOIN cp_group b ON a.cp_group_id = b.cp_group_id  " +
            " JOIN cp_group_sub_limit lm ON a.cp_id = lm.cp_id and a.msisdn = lm.msisdn " +
            " JOIN cp c ON a.cp_id = c.cp_id " +
            " WHERE a.cp_group_id = :cpGroupId" +
            " and (:name is null or lower(a.name) like :name escape '\\' )" +
            " and (:msisdn is null or lower(a.msisdn) like :msisdn escape '\\' )", nativeQuery = true)
    Page<Object[]> search(@Param("msisdn") String msisdn, @Param("name") String name,
                          @Param("cpGroupId") Long cpGroupId,
                          Pageable pageable);

    @Query(value = "select a from CpGroupSub a join CpGroup b on a.cpGroupId = b.id " +
        " where 1 = 1 and lower(a.msisdn) = :msisdn AND (:cpGroupId is null or a.cpGroupId = :cpGroupId) " +
        " and (:cpGroupSubId is null or a.id <> :cpGroupSubId) ")
    List<CpGroupSub> findMsisdn(@Param("msisdn") String msisdn, @Param("cpGroupSubId") Long cpGroupSubId, @Param("cpGroupId") Long cpGroupId);


    @Modifying
    @Query(value = "MERGE INTO cp_group_sub_limit a USING (SELECT 1 FROM dual) " +
        " ON (a.msisdn = :msisdn AND a.cp_id = :cpId) WHEN MATCHED THEN " +
        " UPDATE SET a.num_group_contain = a.num_group_contain + 1 " +
        " WHEN NOT MATCHED THEN " +
        " INSERT (a.cp_id, a.msisdn, a.month_free_sms, a.reset_date, a.num_group_contain, a.telco) " +
        " VALUES(:cpId, :msisdn, :monthFreeSms, SYSDATE, 1, :telco)", nativeQuery = true)
    void updateCpGroupSubLimit(@Param("msisdn") String msisdn, @Param("cpId") Long cpId, @Param("monthFreeSms") Long monthFreeSms, @Param("telco") String telco);


    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE cp_group_sub_limit SET num_group_contain = num_group_contain - 1 " +
        " WHERE cp_id = :cpId AND msisdn = :msisdn ", nativeQuery = true)
    void updateCpGroupSubLimitEdit(@Param("msisdn") String msisdn, @Param("cpId") Long cpId);

    @Modifying
    @Query(value="DELETE FROM cp_group_sub_limit WHERE cp_id = :cpId AND num_group_contain <= 0", nativeQuery = true)
    void deleteCpGroupSubEdit(@Param("cpId") Long cpId);


}
