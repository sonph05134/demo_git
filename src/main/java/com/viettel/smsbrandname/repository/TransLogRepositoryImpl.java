package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.DateUtil;
import com.viettel.smsbrandname.service.dto.TransLogDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class TransLogRepositoryImpl implements TransLogRepositoryCustom {

    private static final Logger logger = LoggerFactory.getLogger(TransLogRepositoryImpl.class);

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;


    @Override
    public List<TransLogDTO> searchHasfiter(String cpCode, Long chanel, String fromDate, String toDate, String currency) {
        List<TransLogDTO> list = new ArrayList<>();
        try{
            StringBuilder builder = new StringBuilder();
            builder.append(" SELECT   cp.cp_code,\n" +
                "         cp.cp_name,\n" +
                "         (CASE tl.chanel\n" +
                "            WHEN 0 THEN 'SMPP'\n" +
                "            WHEN 1 THEN 'Webservice'\n" +
                "            WHEN 2 THEN 'CMS'\n" +
                "         END)as chanel,\n" +
                "         tl.amount,\n" +
                "         tl.balance_before,\n" +
                "         tl.balance_after,\n" +
                "         tl.trans_note,\n" +
                "         TO_CHAR(tl.trans_time, 'yyyy-mm-dd hh24:mi:ss') as trans_time,\n" +
                "         cp.currency\n" +
                "  FROM       trans_log tl\n" +
                "         JOIN\n" +
                "             cp\n" +
                "         ON tl.cp_id = cp.cp_id\n" +
                " WHERE       cp.status = 1\n" +
                "         AND  cp.charge_type = 'PRE' ");
            builder.append(" AND tl.trans_time >= TO_DATE(:fromDate ,'dd/MM/yyyy') ");
            builder.append(" AND tl.trans_time <= TO_DATE(:toDate,'dd/MM/yyyy') ");
            if(!DataUtil.isNullOrEmpty(cpCode)){
                builder.append(" AND cp.cp_code = :cpCode ");
            }
            if(chanel != null){
                builder.append(" AND tl.chanel = :chanel ");
            }
            if(!DataUtil.isNullOrEmpty(currency)){
                builder.append(" AND cp.currency = :currency ");
            }
            Query query = this.entityManager.createNativeQuery(builder.toString());
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            if(!DataUtil.isNullOrEmpty(cpCode)){
                query.setParameter("cpCode",cpCode);
            }
            if(chanel != null){
                query.setParameter("chanel",chanel);
            }
            if(!DataUtil.isNullOrEmpty(currency)){
                query.setParameter("currency",currency);
            }
            List lst = query.getResultList();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(lst != null && lst.size() > 0){
                long i = 0;
                for (Object o : lst) {
                    i++;
                    Object[] objs = (Object[]) o;
                    TransLogDTO dto = new TransLogDTO();
                    dto.setStt(i);
                    dto.setCpCode(DataUtil.safeToString(objs[0]));
                    dto.setCpName(DataUtil.safeToString(objs[1]));
                    dto.setChanelView(DataUtil.safeToString(objs[2]));
                    dto.setAmount(DataUtil.safeToLong(objs[3]));
                    dto.setBalanceBefore(DataUtil.safeToLong(objs[4]));
                    dto.setBalanceAfter(DataUtil.safeToLong(objs[5]));
                    dto.setTransNote(DataUtil.safeToString(objs[6]));
                    Date date = DateUtil.stringToDate(DataUtil.safeToString(objs[7]),dateFormat);
                    dto.setTransTime(date.toInstant());
                    dto.setCurrency(DataUtil.safeToString(objs[8]));
                    list.add(dto);
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }
}
