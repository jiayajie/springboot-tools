package com.example.baseproject.config.datasource;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dongyaofeng on 2017/12/22.
 * DruidConfig 配置 (关于servlet 的配置)
 * <p>
 * Druid官方源码                请参考: https://github.com/alibaba/druid
 * springboot Druid 的更多配置  请参考: https://www.cnblogs.com/niejunlei/p/5977895.html
 * springboot Servlet更多配置   请参考: http://blog.csdn.net/catoop/article/details/50501686
 */
@Configuration
public class DruidConfig {

    /**
     * 注册Servlet
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        /*  一个标准的servlet
             <servlet>
                 <servlet-name>DruidStatView</servlet-name>
                 <servlet-class>com.alibaba.druid.support.http.StatViewServlet</servlet-class>
             </servlet>

             <servlet-mapping>
                 <servlet-name>DruidStatView</servlet-name>
                 <url-pattern>/druid/*</url-pattern>
             </servlet-mapping>
         */

        /*StatViewServlet是一个标准的javax.servlet.http.HttpServlet 内置web页面  */

        // 相当于  <servlet-class>com.alibaba.druid.support.http.StatViewServlet</servlet-class>
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet());

        // 相当于  <url-pattern>/druid*/*</url-pattern>
        servletRegistrationBean.addUrlMappings("/druid/*");

        //servlet 的初始化参数 是Map的形式设置的
        Map<String, String> initParameters = new HashMap<>();

        initParameters.put("loginUsername", "admin");   // 用户名
        initParameters.put("loginPassword", "admin");   // 密码
        initParameters.put("resetEnable", "false");     // 禁用HTML页面上的“Reset All”功能

        /*存在共同时，deny优先于allow*/
//        initParameters.put("allow", "127.0.0.1");     // IP白名单 (没有配置或者为空，则允许所有访问)
//        initParameters.put("deny", "192.168.20.38");  // IP黑名单

        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }


    /**
     * 注册过滤器
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }


    /**
     * Druid和Spring关联监控配置
     *  (按类型拦截配置 , 方法名正则匹配拦截配置 , 按照BeanId来拦截配置)
     * 按照BeanId来拦截配置 用来bean的监控
     */
    @Bean(value = "druid-stat-interceptor")
    public DruidStatInterceptor DruidStatInterceptor() {
        DruidStatInterceptor druidStatInterceptor = new DruidStatInterceptor();
        return druidStatInterceptor;
    }

    /**
     * 按照BeanId来拦截配置
     */
    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setProxyTargetClass(true);
        // 设置要监控的bean的id
        beanNameAutoProxyCreator.setBeanNames("dataProductController", "domobDataServiceImpl", "domobDataRepositoryImpl");
        beanNameAutoProxyCreator.setInterceptorNames("druid-stat-interceptor");
        return beanNameAutoProxyCreator;
    }

}
