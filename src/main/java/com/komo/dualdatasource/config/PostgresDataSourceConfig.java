package com.komo.dualdatasource.config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
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
        basePackages = "com.komo.dualdatasource.repository.pg",
        entityManagerFactoryRef = "pgEntityManager",
        transactionManagerRef = "pgTxManager"
)
public class PostgresDataSourceConfig {

    @Bean("pgDataSourceProps")
    @ConfigurationProperties("custom.pg.ds")
    @Primary
    DataSourceProperties pgDataSourceProps() {
        return new DataSourceProperties();
    }

    @Bean("postgresDataSource")
    @Primary
    DataSource postgresDataSource(@Qualifier("pgDataSourceProps") DataSourceProperties pgDataSourceProps) {
        return pgDataSourceProps.initializeDataSourceBuilder()
                .build();
    }

    @Bean("pgJpaProperties")
    @Primary
    @ConfigurationProperties("custom.pg.jpa")
    JpaProperties pgJpaProperties() {
        return new JpaProperties();
    }

    @Bean("pgEntityManager")
    @Primary
    LocalContainerEntityManagerFactoryBean pgEntityManagerFactoryBean(EntityManagerFactoryBuilder builder,
                                                                      DataSource postgresDataSource,
                                                                      @Qualifier("pgJpaProperties") JpaProperties jpaProperties) {
        return builder.dataSource(postgresDataSource)
//                .properties(Map.of(AvailableSettings.PHYSICAL_NAMING_STRATEGY, CamelCaseToUnderscoresNamingStrategy.class.getName()))
                .properties(jpaProperties.getProperties())
                .packages("com.komo.dualdatasource.entity.pg")
                .persistenceUnit("pg")
                .build();
    }

    @Bean
    @Primary
    PlatformTransactionManager pgTxManager(@Qualifier("pgEntityManager") EntityManagerFactory pgEntityManagerFactoryBean) {
        return new JpaTransactionManager(pgEntityManagerFactoryBean);
    }

}