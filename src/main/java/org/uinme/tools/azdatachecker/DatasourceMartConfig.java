package org.uinme.tools.azdatachecker;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@MapperScan(basePackages = {"org.uinme.tools.azdatachecker.mapper"}, sqlSessionFactoryRef = "sqlSessionFactoryMart")
public class DatasourceMartConfig {
    @Bean(name = {"datasourcePropertiesMart"})
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.datasource-mart")
    DataSourceProperties datasourceMartProperties() {
        return new DataSourceProperties();
    }
    
    @Bean(name = {"datasourceMart"})
    @Primary
    DataSource datasourceMart(
        @Qualifier("datasourcePropertiesMart") DataSourceProperties properties) {
      return properties.initializeDataSourceBuilder().build();
    }
    
    @Bean(name = {"transactionMangerMart"})
    @Primary
    PlatformTransactionManager transactionMangerMart(DataSource datasource) {
      return new DataSourceTransactionManager(datasource);
    }
    
    @Bean(name = "sqlSessionFactoryMart")
    @Primary
    SqlSessionFactory sqlSessionFactory(@Qualifier("datasourceMart") DataSource datasource) {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(datasource);
        try {
            return (SqlSessionFactory) sqlSessionFactory.getObject();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    
}
