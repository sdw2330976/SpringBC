package com.sdw.soft.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by shangyindong on 2017/9/26.
 */
@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello() {
        return "hello eureka from";// + discoveryClient.getLocalServiceInstance().getPort();
    }

    @RequestMapping(value = "/discovery/{applicationName:.+}",method = RequestMethod.GET)
    public List<ServiceInstance> discovery(@PathVariable(value = "applicationName")String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

    @RequestMapping(value = "/discovery",method = RequestMethod.GET)
    public String discoveryService() {
        StringBuilder sb = new StringBuilder();
        List<String> serviceIds = discoveryClient.getServices();
        if (!CollectionUtils.isEmpty(serviceIds)) {
            for (String id : serviceIds) {
                List<ServiceInstance> instances = discoveryClient.getInstances(id);
                if (!CollectionUtils.isEmpty(instances)) {
                    for (ServiceInstance instance : instances) {
                        sb.append("[" + instance.getServiceId() + " host=" + instance.getHost() + " port=" + instance.getPort() + " uri=" + instance.getUri() + "]");
                    }
                } else {
                    sb.append("[no service]");
                }
            }
        }
        return sb.toString();
    }
}
