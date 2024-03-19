package com.komo.dualdatasource.config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Map;

@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories(
        basePackages = "com.komo.dualdatasource.repository.h2",
        entityManagerFactoryRef = "h2EntityManager",
        transactionManagerRef = "h2TxManager"
)
public class H2DataSourceConfig {

    @Bean("h2DataSourceProps")
    @ConfigurationProperties("custom.h2.ds")
    @Primary
    DataSourceProperties h2DataSourceProps() {
        return new DataSourceProperties();
    }

    @Bean("h2DataSource")
    DataSource h2DataSource(@Qualifier("h2DataSourceProps") DataSourceProperties h2DataSourceProps) {
        return h2DataSourceProps.initializeDataSourceBuilder().build();
    }

    @Bean("h2JpaProps")
    @ConfigurationProperties("custom.h2.jpa")
    JpaProperties h2JpaProps() {
        return new JpaProperties();
    }

    @Bean("h2EntityManager")
    LocalContainerEntityManagerFactoryBean h2EntityManagerFactoryBean(EntityManagerFactoryBuilder builder,
                                                                      @Qualifier("h2DataSource") DataSource h2DataSource,
                                                                      @Qualifier("h2JpaProps") JpaProperties h2JpaProps) {
        return builder.dataSource(h2DataSource)
                .packages("com.komo.dualdatasource.entity.h2")
                .properties(h2JpaProps.getProperties())
                .persistenceUnit("h2")
                .build();
    }

    @Bean("h2TxManager")
    PlatformTransactionManager h2TxManager(@Qualifier("h2EntityManager") EntityManagerFactory h2EntityManagerFactory) {
        return new JpaTransactionManager(h2EntityManagerFactory);
    }

}