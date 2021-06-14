package com.viettel.smsbrandname.service.dto;

public class ComboBean {

    private String value;
    private String label;
    private Long valueLong;

    public ComboBean() {
    }

    public ComboBean(Long valueLong, String label) {
        this.label = label;
        this.valueLong = valueLong;
    }

    public ComboBean(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public Long getValueLong() {
        return valueLong;
    }

    public void setValueLong(Long valueLong) {
        this.valueLong = valueLong;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
