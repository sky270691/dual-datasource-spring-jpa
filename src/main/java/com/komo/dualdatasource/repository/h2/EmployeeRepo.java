package com.komo.dualdatasource.repository.h2;

import com.komo.dualdatasource.entity.h2.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
}
