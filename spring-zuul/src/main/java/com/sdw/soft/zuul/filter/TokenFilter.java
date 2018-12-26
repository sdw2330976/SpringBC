package com.sdw.soft.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Log4j2
public class TokenFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        log.info("--->>TokenFilter {},{}", request.getMethod(), request.getRequestURL().toString());
        String token = request.getParameter("token");
        if (StringUtils.isNotBlank(token)) {
            context.setSendZuulResponse(true);
            context.setResponseStatusCode(200);
            context.set("isSuccess", true);
            return null;
        } else {
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(400);
            context.setResponseBody("token is empty.");
            context.set("isSuccess", false);
        }
        return null;
    }
}
