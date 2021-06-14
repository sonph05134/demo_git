package com.viettel.smsbrandname.service.dto.response;

public class ApiResponseDTO {

    private int code;
    private Boolean status;
    private String errors;
    private Object data;
    private Integer page;
    private Integer pageSize;
    private Integer totalRow;

    public ApiResponseDTO(int code, String errors, Object data) {
        super();
        this.code = code;
        this.errors = errors;
        this.data = data;
    }

    public ApiResponseDTO(int code, String errors) {
        super();
        this.code = code;
        this.errors = errors;
    }

    public ApiResponseDTO() {

    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }

    public static ApiResponseDTO build(int code, boolean status, String errors, Object data) {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
        apiResponseDTO.setCode(code);
        apiResponseDTO.setStatus(status);
        apiResponseDTO.setData(data);
        apiResponseDTO.setErrors(errors);
        return apiResponseDTO;
    }

}
