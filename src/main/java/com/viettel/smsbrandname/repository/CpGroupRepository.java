package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.CpGroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CpGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CpGroupRepository extends JpaRepository<CpGroup, Long> {

    @Query(value = "select a.id, a.cpGroupName, a.cpCode, a.createDate, a.updateDate, a.createUser, a.note, " +
        " a.cpId, b.freeSms from CpGroup a join Cp b on a.cpId = b.cpId " +
        " where b.status = 1 and b.accountType = 1 AND a.createUser is null " +
        " and (:provinceId is null or b.provinceId = :provinceId) " +
        " and (:isParent is null or :isParent = 1 or (:provinceUserId is null or b.provinceUserId = :provinceUserId))" +
        " and (:cpId is null or a.cpId = :cpId)" +
        " and (:cpGroupName is null or lower(a.cpGroupName) like :cpGroupName escape '\\' )  " +
        " order by a.cpCode, a.cpGroupName ",
    countQuery = "select count(a) from CpGroup a join Cp b on a.cpId = b.cpId " +
        " where b.status = 1 and b.accountType = 1 AND a.createUser is null " +
        " and (:provinceId is null or b.provinceId = :provinceId) " +
        " and (:isParent is null or :isParent = 1 or (:provinceUserId is null or b.provinceUserId = :provinceUserId))" +
        " and (:cpId is null or a.cpId = :cpId)" +
        " and (:cpGroupName is null or lower(a.cpGroupName) like :cpGroupName escape '\\' ) ")
    Page<Object[]> onSearch(@Param("provinceId") Long provinceId, @Param("isParent") Integer isParent,
                            @Param("provinceUserId") Long provinceUserId, @Param("cpId") Long cpId,
                            @Param("cpGroupName") String cpGroupName, Pageable pageable);

    @Query(value = "select a from CpGroup a where a.cpId = :cpId and a.cpGroupName = :cpGroupName and (:cpGroupId is null or a.id <> :cpGroupId)")
    List<CpGroup> findCpGroupByCpAndName(@Param("cpId") Long cpId, @Param("cpGroupName") String cpGroupName, @Param("cpGroupId") Long cpGroupId);

    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE cp_group_sub_limit SET num_group_contain = num_group_contain - 1 " +
        " WHERE cp_id = :cpId AND msisdn IN (SELECT msisdn FROM cp_group_sub WHERE cp_group_id = :cpGroupId)", nativeQuery = true)
    void updateCpGroupSubLimit(@Param("cpId") Long cpId, @Param("cpGroupId") Long cpGroupId);

    @Modifying
    @Query(value="DELETE FROM cp_group_sub_limit WHERE cp_id = :cpId AND num_group_contain <= 0", nativeQuery = true)
    void deleteCpGroupSubLimit(@Param("cpId") Long cpId);

    @Modifying
    @Query(value="DELETE FROM cp_group WHERE cp_group_id = :cpGroupId", nativeQuery = true)
    void deleteCpGroup(@Param("cpGroupId") Long cpGroupId);

    @Modifying
    @Query(value="DELETE FROM cp_group_sub WHERE cp_group_id = :cpGroupId", nativeQuery = true)
    void deleteCpGroupSub(@Param("cpGroupId") Long cpGroupId);



}
