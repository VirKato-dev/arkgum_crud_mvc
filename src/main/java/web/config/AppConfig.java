package web.config;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan(value = "web")
public class AppConfig  {
    private final Environment env;

    public AppConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean getLocalEntityManager() {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(getDataSource());
        entityManager.setPackagesToScan("web");
        entityManager.setJpaProperties(getHibernateProperties());
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return entityManager;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put(org.hibernate.cfg.Environment.CURRENT_SESSION_CONTEXT_CLASS, env.getProperty("hibernate.current_session_context_class"));
        properties.put(org.hibernate.cfg.Environment.DIALECT, env.getProperty("hibernate.dialect"));
        properties.put(org.hibernate.cfg.Environment.SHOW_SQL, env.getProperty("hibernate.show_sql"));
        properties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, env.getProperty("hibernate.hbm2ddl.auto"));
        return properties;
    }

    @Bean
    public JpaTransactionManager getTransactionalManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(getLocalEntityManager().getObject());
        transactionManager.setDataSource(getDataSource());
        return transactionManager;
    }




}
