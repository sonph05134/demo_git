package com.viettel.smsbrandname.repository.impl;

import com.viettel.smsbrandname.repository.ProvinceUserRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ProvinceUserRepositoryImpl implements ProvinceUserRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Object[]> onSearch(Long provinceId, String username, String email, String keyWord, Integer start, Integer limit) {

        StringBuilder sql = new StringBuilder(""
            + " SELECT\n"
            + "    a.id,\n"
            + "    b.province_name provinceName,\n"
            + "    a.user_name username,\n"
            + "    a.province_id provinceId,\n"
            + "    a.user_type userType,\n"
            + "    a.is_parent isParent,\n"
            + "    a.status, \n"
//            + "    u.FULL_NAME fullname,\n"
//            + "    u.cellPhone,\n"
//            + "    u.email,\n"
//            + "    U.STAFF_CODE staffCode\n"
            + "    row_number() over(order by b.province_name) rn \n"
            + " FROM\n"
            + "    province_users a\n"
            + "    JOIN province b ON a.province_id = b.province_id\n"
//            + "    LEFT JOIN ###SCHEMA_DB_VSA###.users u ON a.user_name = u.user_name \n"
            + " WHERE a.user_type IN (0, 1) \n"
            + "       AND (:P_PROVINCE_ID = -1 OR :P_PROVINCE_ID = a.province_id) \n"
            + "       AND (:P_USER_NAME IS NULL OR :P_USER_NAME = '' OR LOWER(a.USER_NAME) LIKE ('%' || :P_USER_NAME || '%')) \n"
//            + "     AND  (:P_EMAIL IS NULL OR :P_EMAIL = '' OR LOWER(u.EMAIL) LIKE CONCAT('%', :P_EMAIL, '%')) \n"
//            + "     AND  (:P_KEY_WORD IS NULL OR ::P_KEY_WORD = '' "
//            + "         OR ((LOWER(u.FULL_NAME) LIKE CONCAT('%', :P_KEY_WORD, '%'))"
//            + "             OR LOWER(u.cellPhone) LIKE CONCAT('%', :P_KEY_WORD, '%'))"
//            + "             OR LOWER(u.STAFF_CODE) LIKE CONCAT('%', :P_KEY_WORD, '%'))"
//            + ") \n"
            );

        StringBuilder sqlPagination = new StringBuilder();
        sqlPagination.append("SELECT * FROM");
        sqlPagination.append("(").append(sql).append(")");
        sqlPagination.append(" WHERE ((:P_START + :P_LIMIT = 0) OR (RN BETWEEN (:P_START + 1) AND (:P_START + :P_LIMIT)))");

        Query query = entityManager.createNativeQuery(sqlPagination.toString());

        query.setParameter("P_PROVINCE_ID", provinceId);
        query.setParameter("P_USER_NAME", username);
//        query.setParameter("P_EMAIL", email);
//        query.setParameter("P_KEY_WORD", keyWord.toLowerCase());
        query.setParameter("P_START", start);
        query.setParameter("P_LIMIT", limit);
        return query.getResultList();
    }

    @Override
    public boolean isExistsVsaUser(String username) {
        // viet dau ham
        return false;
    }

    @Override
    public boolean isValidAccountLevel(Long userType, Long provinceId) {
        String sql = "SELECT * FROM province_users "
            + "WHERE is_parent = 1 AND user_type = :userType AND province_id = :provinceId";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("userType", userType);
        query.setParameter("provinceId", provinceId);
        return query.getResultList().size() == 0;
    }

    @Override
    public boolean updateStatus(Long id, Integer status) {
        String sqlUpdateProvinceUsers = "UPDATE province_users SET status = :status WHERE id = :id";
        Query query = entityManager.createNativeQuery(sqlUpdateProvinceUsers);
        query.setParameter("status", status);
        query.setParameter("id", id);
        return query.executeUpdate() > 0;
    }

    @Override
    public void updateStatusUser(String username, Integer status) {
        // viet dau ham
    }
}
