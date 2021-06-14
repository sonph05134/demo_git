package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.Mt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Mt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MtRepository extends JpaRepository<Mt, Long>, MtRepositoryCustom {

    @Query(value = "  SELECT  TO_CHAR(mt.mt_time, 'yyyy-mm-dd hh24:mi:ss') as mt_time ,\n" +
        "         mt.receiver,\n" +
        "         mt.sender,\n" +
        "         mt.MESSAGE,\n" +
        "         mt.num_mt,\n" +
        "         (case mt.status when 0 then 'UNPROCESS/Chưa xử lý'\n" +
        "         when 1 then 'CONTENT_ERROR/Lỗi nội dung'\n" +
        "         when 2 then 'SENT_SUCESS/Gửi thành công'\n" +
        "         when 3 then 'SENT_ERROR/Gửi lỗi'\n" +
        "         when 16 then 'RETRY_NORMAL/Retry lần 1'\n" +
        "         when 17 then 'RETRY_LAST/Retry lần 2'\n" +
        "         when 19 then 'RETRY_SUCESS/Retry thành công'\n" +
        "         when 20 then 'RETRY_FAILURE/Retry thất bại'\n" +
        "         when 22 then 'ALIAS_ERROR/Lỗi Brand'\n" +
        "         when 23 then 'NOT_ENOUGH_MONEY/Không đủ tiền'\n" +
        "         when 24 then 'NOT_GET_PRICE/Không lấy được giá của tin nhắn'\n" +
        "         when 25 then 'NOT_UPDATE/Tin nhắn chưa update'\n" +
        "         when 26 then 'OVER_QUOTA/Thuê bao đạt max tin'\n" +
        "         when 27 then 'UNREGISTER/Thuê bao từ chối nhận tin quảng cáo'  end) as status,\n" +
        "         mt.cp_code,\n" +
        "         (case mt.alias_type when 0 then 'Chăm sóc khách hàng' when 1 then 'Quảng cáo' END) as alias_type,\n" +
        "         mt.webservice,\n" +
        "         t.telco_name\n" +
        "  FROM   mt\n" +
        "  JOIN  cp  ON mt.cp_id = cp.cp_id \n" +
        "  JOIN telco t ON mt.telco_id = t.telco_id \n" +
        "  JOIN prog p  ON mt.prog_id = p.prog_id\n" +
        "  where mt.mt_time >= TO_DATE( :fromDate ,'dd/MM/yyyy') \n" +
        "    AND mt.mt_time <= TO_DATE( :toDate,'dd/MM/yyyy')\n" +
        "    and ( :receiver is null or mt.receiver = :receiver )\n" +
        "    and ( :cpCode is null or mt.cp_code = :cpCode )\n" +
        "    and ( :sender is null or mt.sender = :sender )\n" +
        "    and ( :aliasType is null or mt.alias_type = to_number(:aliasType) )\n" +
        "    and ( :telcoId is null or mt.telco_id = to_number(:telcoId) ) ",
        nativeQuery = true)
    List<Object[]> searchExport(@Param("fromDate") String fromDate,
                          @Param("toDate") String toDate,
                          @Param("receiver") String receiver,
                          @Param("cpCode") String cpCode,
                          @Param("sender") String sender,
                          @Param("aliasType") Long aliasType,
                          @Param("telcoId") Long telcoId);
}
