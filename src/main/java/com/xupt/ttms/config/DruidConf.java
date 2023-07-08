package com.xupt.ttms.config;


import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration//在项目启动时可以初始化配置
public class DruidConf {
  //使用连接池dataSource
  @Primary
  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.druid")
  public DataSource druidDataSource() {
    DruidDataSource druidDataSource = new DruidDataSource();
    List<Filter> filterList = new ArrayList<>();
    filterList.add(wallFilter());
    filterList.add(statFilter());
    druidDataSource.setProxyFilters(filterList);
    return druidDataSource;

  }
  @Bean
  public WallFilter wallFilter() {
    WallFilter wallFilter = new WallFilter();
    wallFilter.setConfig(wallConfig());
    return wallFilter;
  }

  @Bean
  public StatFilter statFilter(){
    StatFilter statFilter = new StatFilter();
    return statFilter;
  }

  @Bean
  public WallConfig wallConfig() {
    WallConfig config = new WallConfig();
    config.setMultiStatementAllow(true);//允许一次执行多条语句
    return config;
  }
}