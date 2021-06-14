package com.viettel.smsbrandname.service.impl;

import com.viettel.smsbrandname.commons.ExcelUtils;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.service.OrdersService;
import com.viettel.smsbrandname.domain.Orders;
import com.viettel.smsbrandname.repository.OrdersRepository;
import com.viettel.smsbrandname.service.dto.*;
import com.viettel.smsbrandname.service.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Orders}.
 */
@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {

    private final Logger log = LoggerFactory.getLogger(OrdersServiceImpl.class);

    private final OrdersRepository ordersRepository;

    private final OrderMapper orderMapper;

    private final ExcelUtils excelUtils;


    public OrdersServiceImpl(OrdersRepository ordersRepository, OrderMapper orderMapper, ExcelUtils excelUtils) {
        this.ordersRepository = ordersRepository;
        this.orderMapper = orderMapper;
        this.excelUtils = excelUtils;
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        log.debug("Request to save Order : {}", orderDTO);
        Orders orders = orderMapper.toEntity(orderDTO);
        orders = ordersRepository.save(orders);
        OrderDTO result = orderMapper.toDto(orders);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        return ordersRepository.findAll(pageable)
            .map(orderMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDTO> findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        return ordersRepository.findById(id)
            .map(orderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        ordersRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResultQueryDTO> search(SearchListOrderParam searchListOrderParam, Pageable pageable) {
        log.debug("Request to search for a page of Orders for query {}", searchListOrderParam.toString());
        return ordersRepository.search(searchListOrderParam.getBalancetype(),
            searchListOrderParam.getOrdertype(),
            searchListOrderParam.getChargeresult(),
            searchListOrderParam.getStaffname(),
            searchListOrderParam.getProvincebccsid(),
            pageable).map(bean -> new OrderResultQueryDTO(bean));
    }

    @Override
    public ByteArrayInputStream export(SearchListOrderParam searchListOrderParam) {
        log.debug("Request to export Orders for query {}", searchListOrderParam.toString());
        try{
            List<OrderResultQueryDTO> lst =  ordersRepository.search(searchListOrderParam.getBalancetype(),
                searchListOrderParam.getOrdertype(),
                searchListOrderParam.getChargeresult(),
                searchListOrderParam.getStaffname(),
                searchListOrderParam.getProvincebccsid()).stream().map(bean -> new OrderResultQueryDTO(bean)).collect(Collectors.toList());
            for(OrderResultQueryDTO dto: lst){
                if(0== dto.getBalanceType()){
                    dto.setBalanceTypeView(Translator.toLocale("orders.cskh"));
                }else if(1== dto.getBalanceType()){
                    dto.setBalanceTypeView(Translator.toLocale("orders.qc"));
                }
                if(1== dto.getOrderType()){
                    dto.setOrderTypeView("ViettelPay");
                }else if(2== dto.getOrderType()){
                    dto.setOrderTypeView(Translator.toLocale("orders.ck"));
                }
                if(0 == dto.getChargeResult()){
                    dto.setChargeResultView(Translator.toLocale("orders.success"));
                }else if(2 == dto.getChargeResult()){
                    dto.setChargeResultView(Translator.toLocale("orders.waitApprove"));
                }else if(3 == dto.getChargeResult()){
                    dto.setChargeResultView(Translator.toLocale("orders.waitConfirm"));
                }else if(4 == dto.getChargeResult()){
                    dto.setChargeResultView(Translator.toLocale("orders.reject"));
                }
            }
            List<String> lstHeader = Arrays.asList(
                Translator.toLocale("orders.cpName"),
                Translator.toLocale("orders.amount"),
                Translator.toLocale("orders.discount"),
                Translator.toLocale("orders.vat"),
                Translator.toLocale("orders.balanceType"),
                Translator.toLocale("orders.orderType"),
                Translator.toLocale("orders.chargeTime"),
                Translator.toLocale("orders.chargeResult"),
                Translator.toLocale("orders.provinceBccsName"),
                Translator.toLocale("orders.orderCode"),
                Translator.toLocale("orders.approveNote"));
            List<String> lstColumn = Arrays.asList(
                "cpName",
                "amount",
                "discountPercent",
                "vat",
                "balanceTypeView",
                "orderTypeView",
                "chargeTime",
                "chargeResultView",
                "provinceBccsName",
                "orderCode",
                "approveNote");
            String title = Translator.toLocale("orders.title");
            List<Integer> lstSize = new ArrayList<>();
            lstSize.addAll(Arrays.asList(
                ExcelUtils.WIDTH * 20,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30));
            List<Integer> lstAlign = new ArrayList<>();
            lstAlign.addAll(Arrays.asList(
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_RIGHT,
                ExcelUtils.ALIGN_RIGHT,
                ExcelUtils.ALIGN_RIGHT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT));
            return excelUtils.export(lst, lstHeader, lstColumn, title, lstSize, lstAlign);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public OrderDetailDTO searchDetail(Long id) {
        List<Object[]> objs = ordersRepository.searchDetail(id);
        if(objs != null && objs.size() > 0) {
            return objs.stream().map(OrderDetailDTO::new).collect(Collectors.toList()).get(0);
        }
        return null;
    }
}
