package com.komo.dualdatasource;

import com.komo.dualdatasource.entity.h2.Employee;
import com.komo.dualdatasource.entity.pg.EmployeeSample;
import com.komo.dualdatasource.repository.h2.EmployeeRepo;
import com.komo.dualdatasource.repository.pg.UserSampleRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class DualDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DualDatasourceApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(UserSampleRepo repo, EmployeeRepo emRepo) {
        return (e) -> {
            EmployeeSample es = new EmployeeSample();
            es.setAge(23);
            es.setName("KOmo");
            es.setSex("male");
            repo.save(es);

            Employee em = new Employee();
            em.setName("Risky");
            em.setAge(22);
            emRepo.save(em);
        };
    }

}
