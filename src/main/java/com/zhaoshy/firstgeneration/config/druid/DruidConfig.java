package com.zhaoshy.firstgeneration.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/****************************************
 * @author : zhaoshy
 * @description : 配置Druid
 * @create_time : 2019/12/8 15:43
 *
 *
 *
 ****************************************
 */
@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    /**
     * @Author : zhaoshy
     * @Param : []
     * @Return : org.springframework.boot.web.servlet.ServletRegistrationBean
     * @Description : 配置druid监控中心(黑白名单)
     * @Date : 2019/12/8
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", "root");
        initParams.put("loginPassword", "123");
        initParams.put("allow", "");

        registrationBean.setInitParameters(initParams);

        return registrationBean;
    }

    /**
     * @Author : zhaoshy
     * @Param : []
     * @Return : org.springframework.boot.web.servlet.FilterRegistrationBean
     * @Description : 配置Druid拦截器
     * @Date : 2019/12/8
     */
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new WebStatFilter());

        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", "*.js,*.css,/druid/*");

        registrationBean.setInitParameters(initParams);
        registrationBean.setUrlPatterns((Arrays.asList("/*")));

        return registrationBean;
    }

}
