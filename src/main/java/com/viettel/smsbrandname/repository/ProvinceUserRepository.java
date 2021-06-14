package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.ProvinceUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProvinceUserRepository extends JpaRepository<ProvinceUsers, Long>, ProvinceUserRepositoryCustom {

    Optional<ProvinceUsers> getByUserName(String userName);

    Optional<ProvinceUsers> getByUserNameContains(String userName);

    Optional<ProvinceUsers> getByUserNameAndStatus(String userName, Integer status);

}
