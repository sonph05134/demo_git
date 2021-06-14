package com.viettel.smsbrandname.repository;


import java.util.List;

public interface AliasCostRepositoryCustom {

    List<Object[]> onSearch(String telco, Integer aliasType, Integer limit, Integer start);

}
