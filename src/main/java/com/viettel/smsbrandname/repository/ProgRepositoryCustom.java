package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.Prog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ProgRepositoryCustom extends JpaRepository<Prog,Long> {
    Prog getByProgId(Long progId);
}
