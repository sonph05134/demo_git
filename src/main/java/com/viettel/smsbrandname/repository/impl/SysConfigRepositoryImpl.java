package com.viettel.smsbrandname.repository.impl;

import com.viettel.smsbrandname.domain.SysConfig;
import com.viettel.smsbrandname.repository.SysConfigRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class SysConfigRepositoryImpl implements SysConfigRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Object[]> onSearch(String keyWord, Long module, Long deleted, Integer start, Integer limit) {

        StringBuilder sql = new StringBuilder(" "
            + "WITH TMP AS\n"
            + " (select t.sys_config_id sysConfigId,\n"
            + "         t.config_group configGroup,\n"
            + "         t.config_code configCode,\n"
            + "         t.config_name configName,\n"
            + "         t.user_updated userUpdated,\n"
            + "         to_char(t.date_updated, 'dd/mm/yyyy hh24:mi:ss') dateUpdateds,\n"
            + "         t.deleted,\n"
            + "         t.module,\n"
            + "         row_number() over(order by t.date_updated desc) rn, \n"
            + "         t.config_value configValue,\n"
            + "         t.note \n"
            + "    from sys_config t\n"
            + "   where (:P_MODULE = -1 OR T.MODULE = :P_MODULE)\n"
            + "     and (:P_KEYWORD = '' OR \n"
            + "         (LOWER(t.config_name) LIKE '%' || :P_KEYWORD || '%' ESCAPE '\\') OR\n"
            + "         (LOWER(t.config_code) LIKE '%' || :P_KEYWORD || '%' ESCAPE '\\') OR\n"
            + "         (LOWER(t.config_group) LIKE '%' || :P_KEYWORD || '%' ESCAPE '\\'))\n"
            + "     and (:P_DELETED = -1 OR T.DELETED = :P_DELETED))\n"
            + "SELECT *\n"
            + "  FROM TMP\n"
            + " WHERE ((:P_START + :P_LIMIT = 0) OR\n"
            + "       (TMP.RN BETWEEN (:P_START + 1) AND (:P_START + :P_LIMIT)))");

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("P_MODULE", module);
        query.setParameter("P_DELETED", deleted);
        query.setParameter("P_KEYWORD", keyWord);
        query.setParameter("P_START", start);
        query.setParameter("P_LIMIT", limit);

        return query.getResultList();
    }

    public boolean isExistsData(SysConfig sysConfig) {
        String sql = ""
            + "SELECT *\n"
            + "  FROM SYS_CONFIG S\n"
            + " WHERE S.MODULE = :P_MODULE\n"
            + "   AND (:P_CONFIG_GROUP IS NULL OR :P_CONFIG_GROUP = '' OR LOWER(S.CONFIG_GROUP) = LOWER(:P_CONFIG_GROUP))\n"
            + "   AND LOWER(S.CONFIG_CODE) = LOWER(:P_CONFIG_CODE)\n"
            + "   AND (:P_SYS_CONFIG_ID = -1 OR S.SYS_CONFIG_ID != :P_SYS_CONFIG_ID) ";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("P_MODULE", sysConfig.getModule());
        query.setParameter("P_CONFIG_GROUP", sysConfig.getConfigGroup());
        query.setParameter("P_CONFIG_CODE", sysConfig.getConfigCode());
        query.setParameter("P_SYS_CONFIG_ID", sysConfig.getSysConfigId() == null ? -1 : sysConfig.getSysConfigId());

        return query.getResultList().size() > 0L;
    }

    @Override
    public void updateDeleted(SysConfig sysConfig) {
        String sql = ""
            + "UPDATE SYS_CONFIG S \n"
            + "  SET DELETED = :P_DELETED \n"
            + " WHERE S.SYS_CONFIG_ID = :P_SYS_CONFIG_ID \n";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("P_DELETED", sysConfig.getDeleted());
        query.setParameter("P_SYS_CONFIG_ID", sysConfig.getSysConfigId());
        query.executeUpdate();
    }
}
