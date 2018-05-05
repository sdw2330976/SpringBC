package com.sdw.soft.springbc.cat;

import com.dianping.cat.servlet.CatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shangyindong on 2017/12/26.
 */
//@Configuration
public class CatAutoConfiguration {

    @Bean
    public FilterRegistrationBean catFilter() {
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        CatFilter catFilter = new CatFilter();
        filterBean.setFilter(catFilter);
        filterBean.addUrlPatterns("/*");
        filterBean.setName("cat-filter");
        filterBean.setOrder(1);
        return filterBean;
    }
}
