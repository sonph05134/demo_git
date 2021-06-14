package com.viettel.cmsmobilebrandname.mobile.rest;


import com.viettel.cmsmobilebrandname.mobile.service.MBSmsCostService;
import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.domain.SMSCost;
import com.viettel.smsbrandname.service.dto.SMSCostDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/mb-sms-cost/")
public class MBSmsCostResource extends StandardizeLogging {

    @Autowired
    MBSmsCostService mbSmsCostService;

    @Autowired
    private ResponseFactory iResponseFactory;

    public MBSmsCostResource() {
        super(MBSmsCostResource.class);
    }

    @PostMapping("search")
    public ResponseEntity<Object> search(@RequestBody SMSCostDTO smsCostDTO) {
        Date date = new Date();
        try {
            return iResponseFactory.success(mbSmsCostService.getByCondition(smsCostDTO), Page.class);
        }
        catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @PostMapping("searchById/{id}")
    public ResponseEntity<Object> searchById(@PathVariable Long id){
        Date date = new Date();

        try {
            return iResponseFactory.success(mbSmsCostService.searchById(id),SMSCostDTO.class);
        }catch (Exception e){
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            return iResponseFactory.failure("Có lỗi trong quá trình xử lý. Vui lòng liên hệ sbr@viettel.com.vn!",String.class);
        }

    }

    @PostMapping("update")
    public ResponseEntity<Object> update(@RequestBody SMSCostDTO smsCostDTO){
        Date date = new Date();

        if(mbSmsCostService.checkPriceLevelId(smsCostDTO.getPriceLevel()) == false){
            return iResponseFactory.failure("Loại mức giá không hợp lệ",String.class);
        }
        if(mbSmsCostService.checkExistCpAliasGroup(smsCostDTO)){
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "existData", date);
            iResponseFactory.failure("Nhóm giá Brand đã tồn tại",String.class);
        }
        try {
            mbSmsCostService.update(smsCostDTO);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "updateOk", date);
            return iResponseFactory.success("Cập nhật thành công", String.class);
        }catch (Exception e){
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            return iResponseFactory.failure("Cập nhật thất bại",String.class);
        }
    }


    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody SMSCost smsCost) {
        Date date = new Date();
        if(mbSmsCostService.checkPriceLevelId(smsCost.getPriceLevel()) == false){
            return iResponseFactory.failure("Loại mức giá không hợp lệ",String.class);
        }
        try {
            mbSmsCostService.save(smsCost);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "insertOk", date);
            return iResponseFactory.success("Thêm mới thành công", String.class);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                return iResponseFactory.failBusiness(((BusinessException) e).getErrorCode(), "Thêm mới thất bại");
            }
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        Date date = new Date();

        try {
            mbSmsCostService.delete(id);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "deleteOk", date);
            return iResponseFactory.success("Xóa nhóm giá thành công", String.class);
        }
        catch (Exception e) {
            if (e instanceof BusinessException) {
                return iResponseFactory.failBusiness(((BusinessException) e).getErrorCode(), "Xóa nhóm giá thất bại");
            }
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }

    }



}
