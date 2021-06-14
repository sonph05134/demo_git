package com.viettel.smsbrandname.service.dto.province;

import com.viettel.smsbrandname.service.dto.response.ApiResponseDTO;

public class ProvinceManagementDTO {

    private ApiResponseDTO provinces;
    private ApiResponseDTO provinceBCCSs;

    public ApiResponseDTO getProvinces() {
        return provinces;
    }

    public void setProvinces(ApiResponseDTO provinces) {
        this.provinces = provinces;
    }

    public ApiResponseDTO getProvinceBCCSs() {
        return provinceBCCSs;
    }

    public void setProvinceBCCSs(ApiResponseDTO provinceBCCSs) {
        this.provinceBCCSs = provinceBCCSs;
    }
}
