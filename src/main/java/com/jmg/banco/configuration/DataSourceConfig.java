package com.jmg.banco.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.jmg.banco.repository.hibernate",
        entityManagerFactoryRef = "ordersEntityManagerFactory",
        transactionManagerRef = "ordersTransactionManager"
)
public class DataSourceConfig {

    @Autowired
    private Environment env;


    @Bean
    public DataSource ordersDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(env.getProperty("datasource.orders.driver.class"))
                .url(env.getProperty("datasource.orders.url"))
                .username(env.getProperty("datasource.orders.username"))
                .password(env.getProperty("datasource.orders.password"))
                .build();
    }

    @Bean
    public PlatformTransactionManager ordersTransactionManager() {
        EntityManagerFactory factory = ordersEntityManagerFactory().getObject();
        return new JpaTransactionManager(factory);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean ordersEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(ordersDataSource());
        factory.setPackagesToScan(new String[]{
                "com.jmg.banco.domain"
        });
        factory.setPersistenceUnitName("org.hibernate.jpa.HibernatePersistenceProvider");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        factory.setJpaVendorAdapter(vendorAdapter);


        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
        jpaProperties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        factory.setJpaProperties(jpaProperties);

        return factory;

    }

    @Bean
    public DataSourceInitializer ordersDataSourceInitializer() {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(ordersDataSource());
//	        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
//	        databasePopulator.addScript(new ClassPathResource("orders-data.sql"));
//	        dataSourceInitializer.setDatabasePopulator(databasePopulator);
//	        dataSourceInitializer.setEnabled(env.getProperty("datasource.orders.initialize", Boolean.class, false));
        return dataSourceInitializer;
    }
}