package com.viettel.cmsmobilebrandname.mobile.service.impl;

import com.viettel.cmsmobilebrandname.mobile.dto.AliasCostDTO;
import com.viettel.cmsmobilebrandname.mobile.dto.mapper.AliasCostMapper;
import com.viettel.cmsmobilebrandname.mobile.repository.MbCpAliasRepository;
import com.viettel.cmsmobilebrandname.mobile.service.MbCpAliasService;
import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.domain.TransLogAlias;
import com.viettel.smsbrandname.repository.CpAliasRepository;
import com.viettel.smsbrandname.repository.TransLogRepository;
import com.viettel.smsbrandname.service.AliasCostService;
import com.viettel.smsbrandname.service.ChargingService;
import com.viettel.smsbrandname.service.CpAliasService;
import com.viettel.smsbrandname.service.dto.CpAliasDTO;
import com.viettel.smsbrandname.service.dto.CpDTO;
import com.viettel.smsbrandname.service.impl.CpAliasServiceImpl;
import com.viettel.smsbrandname.service.mapper.CpAliasMapper;
import com.viettel.smsbrandname.service.mapper.CpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MbCpAliasServiceImpl extends CpAliasServiceImpl implements MbCpAliasService {


    StandardizeLogging standardizeLogging = new MBCpServiceImpl();

    @Autowired
    CpAliasService cpAliasService;

    @Autowired
    MbCpAliasRepository mbCpAliasRepository;

    @Autowired
    CpAliasMapper cpAliasMapper;


    @Autowired
    AliasCostMapper aliasCostMapper;

    @Autowired
    AliasCostService aliasCostService;

    @Autowired
    CpMapper cpMapper;

    @Autowired
    ChargingService chargingService;

    @Autowired
    TransLogRepository transLogRepository;


    public MbCpAliasServiceImpl(CpAliasRepository cpAliasRepository, CpAliasMapper cpAliasMapper) {
        super(cpAliasRepository, cpAliasMapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean minusFees(Long cpId, Long cpAliasId) throws Exception {
        Date date = new Date();

        // lay thong tin thuong hieu theo dieu kien
        CpAliasDTO cpAliasDTO =  checkExistCpAliasByIdAndCpAliasId(cpId,cpAliasId);

        // get ALIAS_COST.CANCEL_ALIAS_COST_USD and ALIAS_COST.CANCEL_ALIAS_COST
        AliasCostDTO aliasCostDTO =  getInformationAliasCost(cpAliasDTO.getTelco(), cpAliasDTO.getAliasType());

        // get information CP chargeType
        CpDTO cpDTO = getInformationCp(cpId);
        if(cpDTO.getChargeType().equals("PRE")){
            if(cpDTO.getCurrency().equals("VND") && aliasCostDTO.getCancelAliasCost() > 0){
                BigDecimal amount = new BigDecimal(aliasCostDTO.getCancelAliasCost());

                boolean check = chargingService.charge(cpDTO.getCpCode(), amount,
                    2,"charge_CancelAliasCost",
                    cpAliasDTO.getCpAlias() + "_aliasfee",
                    cpAliasDTO.getAliasType().intValue()
                );

                if(check == false){
                    standardizeLogging.writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL,"Không trừ được tiền trong tài khoản",date);
                    return check;
                    throw new  Exception("RollBack");
                }else {
                    TransLogAlias transLogAlias = new TransLogAlias();

                    saveTransLogAlias(transLogAlias,cpAliasDTO,aliasCostDTO);

                }
            }
        }else{

        }
    }

    public boolean saveTransLogAlias(TransLogAlias transLogAlias,CpAliasDTO cpAliasDTO,AliasCostDTO aliasCostDTO) throws ParseException {
        BigDecimal bigDecimal = new BigDecimal(-aliasCostDTO.getCancelAliasCost());
        transLogAlias.setAmount(bigDecimal);
        transLogAlias.setCpId(cpAliasDTO.getCpId());

        Format f = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String str = f.format(new Date());

        try {
            String sDate1 = str;
            Date date1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(sDate1);
            transLogAlias.setTransTime(date1);

        }catch (Exception e){
                throw  e;
        }

        transLogAlias.setAlias(cpAliasDTO.getCpAlias() + "_aliasfee");

        transLogAlias.setAliasType(0);
        transLogAlias.setProcess(0);
        transLogAlias.setProgId(0);

        return true;
    }

    @Override
    public CpDTO getInformationCp(Long id) {
        return cpMapper.toDto(mbCpAliasRepository.getInformationCp(id));
    }

    @Override
    public CpAliasDTO checkExistCpAliasByIdAndCpAliasId(Long cpId, Long cpAliasId) {
            return cpAliasMapper.toDto(mbCpAliasRepository.checkExistCpAliasByIdAndCpAliasId(cpId,cpAliasId));
    }

    @Override
    public AliasCostDTO getInformationAliasCost(String teLcoAlias, Long aliasType) {
        return aliasCostService.getInformationAliasCost(teLcoAlias,aliasType);
    }
}
