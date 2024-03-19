package com.komo.dualdatasource.repository.pg;

import com.komo.dualdatasource.entity.pg.EmployeeSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserSampleRepo extends JpaRepository<EmployeeSample, Long> {
}
