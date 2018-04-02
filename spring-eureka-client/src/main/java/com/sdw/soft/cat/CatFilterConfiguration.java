package com.sdw.soft.cat;

import com.dianping.cat.servlet.CatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shangyd on 2017/12/17.
 */
@Configuration
public class CatFilterConfiguration {

    @Bean
    public FilterRegistrationBean catFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CatFilter catFilter = new CatFilter();
        registrationBean.setFilter(catFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("cat-Filter");
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
