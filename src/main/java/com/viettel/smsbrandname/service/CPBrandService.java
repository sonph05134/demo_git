package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.service.dto.cpbrand.*;
import com.viettel.smsbrandname.service.dto.response.ApiResponseDTO;
import com.viettel.smsbrandname.service.dto.response.ComboboxResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface CPBrandService {

    ApiResponseDTO search(Integer aliasType, String brandName, String telco, Integer start, Integer limit, Long cpId);

    List<CPBrandTelcoDTO> getTelcos();

    List<ComboboxResponseDTO> getAliasGroup(Integer type);

    List<ComboboxResponseDTO> getWS(Integer status);

    List<ComboboxResponseDTO> getKeepFee();

    void activeOrInactiveBrand(CpBrandActionDTO cpBrandActionDTO);

    void deleteCpBrand(CpBrandActionDTO cpBrandActionDTO);

    ByteArrayInputStream exportBrands(CPBrandSearchDTO cpBrandSearchDTO) throws Exception;

    ApiResponseDTO createCpAlias(String data, String username, String userId, String commissionDefault, String checked, MultipartFile file);

    ApiResponseDTO editCpAlias(String data, String username, String userId, String commissionDefault, String checked, MultipartFile file);

    ByteArrayInputStream downloadAttachFile(String fileName) throws IOException;
}
