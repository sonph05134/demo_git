package com.viettel.smsbrandname.repository.impl;

import com.viettel.smsbrandname.domain.ProvinceBccs;
import com.viettel.smsbrandname.repository.ProvinceBccsRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ProvinceBccsRepositoryImpl implements ProvinceBccsRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Object[]> onSearch(Long provinceId, Integer start, Integer limit, Double timeZone) {
        StringBuilder sql = new StringBuilder(" "
            + " SELECT pb.province_id provinceId,\n"
            + "         p.province_name provinceName,\n"
            + "         pb.id,\n"
            + "         pb.province_bccs_id provinceBccsId,\n"
            + "         pb.province_bccs_name provinceBccsName,\n"
            + "         pb.bccs_staff_code bccsStaffCode,\n"
            + "         pb.user_updated userUpdated,\n"
            + "         to_char(pb.date_updated + :P_TIME_ZONE, 'dd/mm/yyyy hh24:mi:ss') dateUpdateds,\n"
            + "         row_number() over(order by p.province_name, pb.province_bccs_name, pb.province_bccs_id) rn \n"
            + "    FROM province_bccs pb\n"
            + "    left join province p\n"
            + "      on p.province_id = pb.province_id\n"
            + "   where (:P_PROVINCE_ID = -1 OR pb.province_id = :P_PROVINCE_ID)");

        StringBuilder sqlPagination = new StringBuilder();
        sqlPagination.append("SELECT * FROM");
        sqlPagination.append("(").append(sql).append(")");
        sqlPagination.append(" WHERE ((:P_START + :P_LIMIT = 0) OR (RN BETWEEN (:P_START + 1) AND (:P_START + :P_LIMIT)))");

        Query query = entityManager.createNativeQuery(sqlPagination.toString());
        query.setParameter("P_PROVINCE_ID", provinceId);
        query.setParameter("P_START", start);
        query.setParameter("P_LIMIT", limit);
        query.setParameter("P_TIME_ZONE", timeZone / 24);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getLstProvinceNotInProvinceBccs() {
        String sql = ""
            + "SELECT province_id value, province_name label\n"
            + "  FROM province\n"
            + " WHERE province_id NOT IN\n"
            + "       (SELECT distinct NVL(PROVINCE_ID, -1) FROM PROVINCE_BCCS)\n"
            + " ORDER BY NLSSORT(LOWER(province_name), 'NLS_SORT=VIETNAMESE') ASC";
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }

    @Override
    public boolean isExistsDataProvinceBccs(ProvinceBccs provinceBCCS) {
        String sql = ""
            + "SELECT *\n"
            + "  FROM PROVINCE_BCCS S\n"
            + " WHERE (:P_ID = -1 OR S.ID != :P_ID) \n"
            + "   AND (:P_PROVINCE_BCCS_ID = -1 OR S.PROVINCE_BCCS_ID = :P_PROVINCE_BCCS_ID)\n"
            + "   AND NVL(S.PROVINCE_ID, -1) = NVL(:P_PROVINCE_ID, -1)\n";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("P_PROVINCE_BCCS_ID", provinceBCCS.getProvinceBccsId());
        query.setParameter("P_PROVINCE_ID", provinceBCCS.getProvinceId() == null ? -1 : provinceBCCS.getProvinceId());
        query.setParameter("P_ID", provinceBCCS.getId() == null ? -1 : provinceBCCS.getId());

        return query.getResultList().size() > 0;
    }

    @Override
    public boolean isExistProvinceSMS(Long provinceId) {
        if (provinceId != null) {
            String sql = "SELECT * FROM PROVINCE WHERE PROVINCE_ID = :P_PROVINCE_ID";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("P_PROVINCE_ID", provinceId);
            return query.getResultList().size() > 0;
        }
        return false;
    }

    @Override
    public boolean isMappedDataProvinceBccs(Long provinceId, Long id) {
        String sql = ""
            + "SELECT * FROM PROVINCE_BCCS P WHERE " +
                    "(:P_PROVINCE_ID = -1 OR P.PROVINCE_ID = :P_PROVINCE_ID) " +
                    "AND (:P_ID = -1 OR P.ID != :P_ID)  ";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("P_PROVINCE_ID", provinceId == null ? -1 : provinceId);
        query.setParameter("P_ID", id == null ? -1 : id);

        return query.getResultList().size() > 0;
    }

    public boolean deleteProvinceBccs(Long id) {
        String sql = " DELETE PROVINCE_BCCS\n"
            + " WHERE ID = :P_ID\n"
            + "   AND PROVINCE_ID IS NULL\n"
            + "   AND NOT EXISTS\n"
            + " (SELECT 1\n"
            + "          FROM PROVINCE P\n"
            + "         WHERE P.PROVINCE_ID NOT IN\n"
            + "               (SELECT distinct NVL(PROVINCE_ID, -1) FROM PROVINCE_BCCS)) ";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("P_ID", id);
        return query.executeUpdate() > 0;
    }
}
