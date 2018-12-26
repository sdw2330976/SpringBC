package com.sdw.soft.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;

@Log4j2
public class PasswordFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        return (boolean)context.get("isSuccess");
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        log.info("--->>>PasswordFilter {},{}", request.getMethod(), request.getRequestURL().toString());
        String password = request.getParameter("password");
        if (null != password && password.equalsIgnoreCase("admin")) {
            context.setSendZuulResponse(true);
            context.setResponseStatusCode(200);
            context.set("isSuccess", true);
            return null;
        } else {
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(400);
            context.setResponseBody("password is wrong.");
            context.set("isSuccess", false);
            return null;
        }
    }
}
