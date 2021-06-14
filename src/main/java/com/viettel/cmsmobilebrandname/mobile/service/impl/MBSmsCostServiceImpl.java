package com.viettel.cmsmobilebrandname.mobile.service.impl;

import com.viettel.cmsmobilebrandname.mobile.dto.mapper.MBSmsCostMapper;
import com.viettel.cmsmobilebrandname.mobile.repository.MBSmsCostRepository;
import com.viettel.cmsmobilebrandname.mobile.service.MBSmsCostService;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.SMSCost;
import com.viettel.smsbrandname.service.PriceLevelService;
import com.viettel.smsbrandname.service.SmsCostService;
import com.viettel.smsbrandname.service.dto.SMSCostDTO;
import com.viettel.smsbrandname.service.impl.SmsCostServiceImpl;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Service
public class MBSmsCostServiceImpl extends SmsCostServiceImpl implements MBSmsCostService {


    @Autowired
    private SmsCostService smsCostService;

    @Autowired
    private ResponseFactory iResponseFactory;

    @Autowired
    MBSmsCostRepository mbSmsCostRepository;

    @Autowired
    MBSmsCostMapper mbSmsCostMapper;

    @Autowired
    PriceLevelService priceLevelService;

    public Page<SMSCost> getByCondition(SMSCostDTO getByCondition) {
        return smsCostService.getByCondition(getByCondition);
    }


    public void save(SMSCost smsCost) {
        smsCostService.save(smsCost);
    }


    public void delete(Long id) {
        smsCostService.delete(id);
    }

    @Override
    public boolean checkPriceLevelId(Long priceLevel) {
        return priceLevelService.checkPriceLevel(priceLevel);
    }

    @Transactional
    @Override
    public SMSCostDTO searchById(Long id) {

            SMSCostDTO smsCostDTOS = mbSmsCostMapper.toDto(mbSmsCostRepository.getOne(id));
                if(smsCostDTOS.getType() == 0){
                    smsCostDTOS.setNameType("Nội mạng");
                }else {
                    smsCostDTOS.setNameType("Ngoại mạng");
                }

                if(smsCostDTOS.getAliasGroupType() == 0){
                    smsCostDTOS.setNameAliasGroupType("CSKH");
                } else {
                    smsCostDTOS.setNameAliasGroupType("Quảng cáo");
                }


                Locale localeVN = new Locale("en", "EN");
                NumberFormat en = NumberFormat.getInstance(localeVN);

                smsCostDTOS.setFormatPrice(en.format(smsCostDTOS.getPrice()));

                if(!DataUtil.isNullOrEmpty(smsCostDTOS.getPriceUsd()))
                {
                     BigDecimal bigDecimal = BigDecimal.valueOf((Constants.USD_LENGTH));
                     BigDecimal PriceUsd =  smsCostDTOS.getPriceUsd().divide(bigDecimal);
                     String formatPriceUsd = en.format(PriceUsd);
                     smsCostDTOS.setFormatPriceUsd(formatPriceUsd);
                }else {
                    BigDecimal bigDecimal = new BigDecimal(0);
                    smsCostDTOS.setPriceUsd(bigDecimal);
                }

        return smsCostDTOS;
    }

    @Transactional
    @Override
    public boolean update(SMSCostDTO smsCostDTO) {

        try {
            BigDecimal bigDecimal = BigDecimal.valueOf((Constants.USD_LENGTH));
            smsCostDTO.setPriceUsd(smsCostDTO.getPrice().multiply(bigDecimal));
            mbSmsCostRepository.save(mbSmsCostMapper.toEntity(smsCostDTO));
            return true;
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public boolean checkExistCpAliasGroup(SMSCostDTO smsCostDTO) {
       return smsCostService.smsCostExistUpdate(smsCostDTO);
    }
}
