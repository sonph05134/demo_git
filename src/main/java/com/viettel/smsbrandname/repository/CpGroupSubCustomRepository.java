package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.commons.DbUtils;
import com.viettel.smsbrandname.commons.MyWork;
import com.viettel.smsbrandname.service.dto.CpGroupSubDTO;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class CpGroupSubCustomRepository {
    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    public void updateCpGroupSubLimit(List<CpGroupSubDTO> lstData) throws Exception {
        PreparedStatement stmt = null;
        Connection connection;

        try {
            Session session = entityManager.unwrap(Session.class);
            MyWork myWork = new MyWork();
            session.doWork(myWork);
            connection = myWork.getConnection();
            connection.setAutoCommit(false);

            String updateLimitSub = "MERGE INTO cp_group_sub_limit a "
                + "USING (SELECT 1 FROM dual) "
                + "ON (a.msisdn = ? AND a.cp_id = ?) WHEN MATCHED THEN "
                + "UPDATE SET a.num_group_contain = a.num_group_contain + 1 "
                + "WHEN NOT MATCHED THEN "
                + "INSERT (a.cp_id, a.msisdn, a.month_free_sms, a.reset_date, a.num_group_contain, a.telco) "
                + "VALUES(?, ?, ?, SYSDATE, 1, ?)";

            stmt = connection.prepareStatement(updateLimitSub);
            int i = 0;
            int batch = 0;
            for (CpGroupSubDTO cpGroupSubDTO : lstData) {
                i = 1;
                DbUtils.setStringJdbc(stmt, i++, cpGroupSubDTO.getMsisdn());
                DbUtils.setLongJdbc(stmt, i++, cpGroupSubDTO.getCpId());
                DbUtils.setLongJdbc(stmt, i++, cpGroupSubDTO.getCpId());
                DbUtils.setStringJdbc(stmt, i++, cpGroupSubDTO.getMsisdn());
                DbUtils.setLongJdbc(stmt, i++, cpGroupSubDTO.getMonthFreeSms());
                DbUtils.setStringJdbc(stmt, i++, cpGroupSubDTO.getTelcoCode());
                stmt.addBatch();
                batch++;
                if (batch % 200 == 0) {
                    stmt.executeBatch();
                    stmt.getConnection().commit();
                }

            }
            stmt.executeBatch();
            stmt.getConnection().commit();
        } finally {
            DbUtils.close(stmt);
        }
    }
}
