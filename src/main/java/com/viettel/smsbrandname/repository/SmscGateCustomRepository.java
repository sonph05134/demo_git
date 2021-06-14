package com.viettel.smsbrandname.repository;

public interface SmscGateCustomRepository {
    boolean smscGateExist(Long id);

    void changeStatus(Long id, int status);
}
