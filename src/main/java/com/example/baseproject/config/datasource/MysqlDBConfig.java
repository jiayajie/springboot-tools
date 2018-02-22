package com.example.baseproject.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 多数据源源配置  (Mysql)
 */
//@PropertySource(value = {"db.properties"}, ignoreResourceNotFound = false)  //加载自定义配置文件 可用 @valeu 注解 接收
//@Configuration
//@EnableJpaRepositories(basePackages = "com.example.baseproject.repostitory")  /*使用SpringData JPA*/
public class MysqlDBConfig {

    /**
     * 数据源配置对象
     */
    @Bean
    @Primary /*表示默认的对象，Autowire可注入，不是默认的得明确名称注入*/
    @ConfigurationProperties("mysql.datasource")  /*使用标准的yml 格式书写 配置文件 , DataSourceProperties 会自己找到对应关系 */
    public DataSourceProperties mysqlDataSourceProperties() {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        return dataSourceProperties;
    }

    /**
     * 数据源对象
     */
    @Bean(name = "mysqlDruidDataSource", destroyMethod = "close", initMethod = "init")
    @Primary
    @ConfigurationProperties(prefix = "mysql.datasource")  //配置文件的信息，读取并自动封装成实体类
    public DruidDataSource firstDataSource() {
        DruidDataSource build = (DruidDataSource) mysqlDataSourceProperties().initializeDataSourceBuilder().build();
        return build;
    }


    @Bean(name = "hiveJdbcTemplate")
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(firstDataSource());
    }
}
