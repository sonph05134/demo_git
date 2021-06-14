package com.viettel.cmsmobilebrandname.mobile.rest;


import com.viettel.cmsmobilebrandname.mobile.dto.AliasCostDTO;
import com.viettel.cmsmobilebrandname.mobile.service.MbCpAliasService;
import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.service.ChargingService;
import com.viettel.smsbrandname.service.dto.CpAliasDTO;
import com.viettel.smsbrandname.service.dto.CpDTO;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import liquibase.pro.packaged.bo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("/api/alias/")
public class MbCpAliasResource extends StandardizeLogging {

    @Autowired
    MbCpAliasService mbCpAliasService;

    @Autowired
    private ResponseFactory iResponseFactory;






    public MbCpAliasResource() {
        super(MbCpAliasResource.class);
    }


    @DeleteMapping("delete/{cpAliasId}/{cpId}")
    public ResponseEntity<Object> deleteCpAlias(@PathVariable Long cpId, @PathVariable Long cpAliasId) {
        Date date = new Date();
        try {
            CpAliasDTO cpAliasDTO =  mbCpAliasService.checkExistCpAliasByIdAndCpAliasId(cpId,cpAliasId);
            if(cpAliasDTO == null){
              return iResponseFactory.failure("Không tìm thấy thương hiệu của khách hàng",String.class);
            }else {
                if (mbCpAliasService.minusFees(cpId, cpAliasId) == false) {
                    return iResponseFactory.failure("CP không đủ tiền để trừ phí đăng ký/ hủy brandname",String.class);
                }


            }
        }catch (Exception exception){
                throw  exception;
        }
        return null;
    }
}
