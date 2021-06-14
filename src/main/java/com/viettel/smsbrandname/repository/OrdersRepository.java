package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.Orders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Order entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    @Query(value = " select c.cp_name,\n" +
        "       o.amount,\n" +
        "       o.discount_percent,\n" +
        "       o.vat,\n" +
        "       c.currency,\n" +
        "       o.balance_type,\n" +
        "       o.order_type,\n" +
        "       o.charge_time,\n" +
        "       o.charge_result,\n" +
        "       b.province_bccs_name,\n" +
        "       o.order_code,\n" +
        "       o.approve_note,\n" +
        "       o.approve_time,\n" +
        "       o.bccs_sale_id,\n" +
        "       o.contact_email,\n" +
        "       o.id\n" +
        "from orders o\n" +
        "         inner join cp c on o.cp_id = c.cp_id\n" +
        "         inner join province_bccs b on o.bccs_branch_id = b.province_bccs_id\n" +
        "where (1 = 1)\n" +
        "  and (:balancetype is null or o.balance_type = to_number(:balancetype))\n" +
        "  and (:ordertype is null or o.order_type = to_number(:ordertype))\n" +
        "  and (:chargeresult is null or o.charge_result = to_number(:chargeresult))\n" +
        "  and (:staffname is null or lower(b.bccs_staff_code) like :staffname)\n" +
        "  and (:provincebccsid is null or b.province_bccs_id = to_number(:provincebccsid)) ",
        countQuery = " select count(1)\n" +
            "from orders o\n" +
            "         inner join cp c on o.cp_id = c.cp_id\n" +
            "         inner join province_bccs b on o.bccs_branch_id = b.province_bccs_id\n" +
            "where (1 = 1)\n" +
            "  and (:balancetype is null or o.balance_type = to_number(:balancetype))\n" +
            "  and (:ordertype is null or o.order_type = to_number(:ordertype))\n" +
            "  and (:chargeresult is null or o.charge_result = to_number(:chargeresult))\n" +
            "  and (:staffname is null or lower(b.bccs_staff_code) = :staffname)\n" +
            "  and (:provincebccsid is null or b.province_bccs_id = to_number(:provincebccsid)) ",
        nativeQuery = true)
    Page<Object[]> search(@Param("balancetype") Long balancetype,
                        @Param("ordertype") Long ordertype,
                        @Param("chargeresult") Long chargeresult,
                        @Param("staffname") String staffname,
                        @Param("provincebccsid") Long provincebccsid,
                        Pageable pageable);

    @Query(value = " select c.cp_name,\n" +
        "       o.amount,\n" +
        "       o.discount_percent,\n" +
        "       o.vat,\n" +
        "       c.currency,\n" +
        "       o.balance_type,\n" +
        "       o.order_type,\n" +
        "       o.charge_time,\n" +
        "       o.charge_result,\n" +
        "       b.province_bccs_name,\n" +
        "       o.order_code,\n" +
        "       o.approve_note,\n" +
        "       o.approve_time,\n" +
        "       o.bccs_sale_id,\n" +
        "       o.contact_email,\n" +
        "       o.id\n" +
        "from orders o\n" +
        "         inner join cp c on o.cp_id = c.cp_id\n" +
        "         inner join province_bccs b on o.bccs_branch_id = b.province_bccs_id\n" +
        "where (1 = 1)\n" +
        "  and (:balancetype is null or o.balance_type = to_number(:balancetype))\n" +
        "  and (:ordertype is null or o.order_type = to_number(:ordertype))\n" +
        "  and (:chargeresult is null or o.charge_result = to_number(:chargeresult))\n" +
        "  and (:staffname is null or lower(b.bccs_staff_code) like :staffname)\n" +
        "  and (:provincebccsid is null or b.province_bccs_id = to_number(:provincebccsid)) ",
        nativeQuery = true)
    List<Object[]> search(@Param("balancetype") Long balancetype,
                          @Param("ordertype") Long ordertype,
                          @Param("chargeresult") Long chargeresult,
                          @Param("staffname") String staffname,
                          @Param("provincebccsid") Long provincebccsid);

    @Query(value = " SELECT   cp.cp_name,\n" +
        "         cp.address,\n" +
        "         cp.tax_code,\n" +
        "         cp.order_no,\n" +
        "         orders.order_code,\n" +
        "         orders.contact_name,\n" +
        "         orders.contact_phone,\n" +
        "         orders.contact_email,\n" +
        "         (CASE orders.balance_type\n" +
        "              WHEN 0 THEN 'Chăm sóc khách hàng'\n" +
        "              WHEN 1 THEN 'Quảng cáo'\n" +
        "          END)\n" +
        "             AS balance_type_view,\n" +
        "         orders.amount,\n" +
        "         orders.discount_percent,\n" +
        "         orders.discount_percent * orders.amount,\n" +
        "         ROUND(orders.payment_value/((orders.vat + 100)/100)),\n" +
        "         orders.vat,\n" +
        "         ROUND(orders.payment_value - (orders.payment_value/((orders.vat + 100)/100))),\n" +
        "         orders.payment_value,\n" +
        "         orders.exchange_rate,\n" +
        "         cp.currency,\n" +
        "         orders.file_name,\n" +
        "         orders.note,\n" +
        "         (CASE orders.charge_result\n" +
        "              WHEN 0 THEN 'Thành công'\n" +
        "              WHEN 2 THEN 'Chờ phê duyệt'\n" +
        "              WHEN 3 THEN 'Chờ xác nhận'\n" +
        "              WHEN 4 THEN 'Từ chối'\n" +
        "          END)\n" +
        "             AS charge_result_view,\n" +
        "         orders.approve_user,\n" +
        "         orders.admin_file_name,\n" +
        "         orders.charge_user,\n" +
        "         orders.finance_file_name,\n" +
        "         (select province_bccs.province_bccs_name from province_bccs where orders.bccs_branch_id = province_bccs.province_bccs_id and rownum = 1) as province_bccs_name ,\n" +
        "         orders.approve_note,\n" +
        "         orders.charge_result\n" +
        "  FROM  orders\n" +
        "  left JOIN cp ON orders.cp_id = cp.cp_id\n" +
        "  where orders.id = :id ", nativeQuery = true)
    List<Object[]> searchDetail(@Param("id") Long id);
}
