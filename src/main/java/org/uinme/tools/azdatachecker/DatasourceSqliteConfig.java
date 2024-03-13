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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@MapperScan(basePackages = {"org.uinme.tools.azdatachecker.mapper_sqlite"}, sqlSessionFactoryRef = "sqlSessionFactorySqlite")
public class DatasourceSqliteConfig {
    @Bean(name = {"datasourcePropertiesSqlite"})
    @ConfigurationProperties(prefix = "spring.datasource.datasource-sqlite")
    DataSourceProperties datasourceSqliteProperties() {
        return new DataSourceProperties();
    }
    
    @Bean(name = {"datasourceSqlite"})
    DataSource datasourceMart(
        @Qualifier("datasourcePropertiesSqlite") DataSourceProperties properties) {
      return properties.initializeDataSourceBuilder().build();
    }
    
    @Bean(name = {"transactionMangerSqlite"})
    PlatformTransactionManager transactionMangerSqlite(DataSource datasource) {
      return new DataSourceTransactionManager(datasource);
    }
    
    @Bean(name = "sqlSessionFactorySqlite")
    SqlSessionFactory sqlSessionFactory(@Qualifier("datasourceSqlite") DataSource datasource) {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(datasource);
        try {
            return (SqlSessionFactory) sqlSessionFactory.getObject();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
