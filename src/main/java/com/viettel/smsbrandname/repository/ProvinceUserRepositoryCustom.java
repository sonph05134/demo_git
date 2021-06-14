package com.viettel.smsbrandname.repository;

import java.util.List;

public interface ProvinceUserRepositoryCustom {

    List<Object[]> onSearch(Long provinceId, String username,
                            String email, String keyWord,
                            Integer start, Integer limit);

    boolean isExistsVsaUser(String username);

    boolean isValidAccountLevel(Long userType, Long provinceId);

    boolean updateStatus(Long id, Integer status);

    void updateStatusUser(String username, Integer status);

}
