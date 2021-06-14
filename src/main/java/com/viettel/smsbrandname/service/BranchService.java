package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.service.dto.ComboBean;

import java.security.Principal;
import java.util.List;

public interface BranchService {
    List<ComboBean> getListBranch(Principal principal);
}
