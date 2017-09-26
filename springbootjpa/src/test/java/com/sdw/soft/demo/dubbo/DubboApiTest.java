package com.sdw.soft.demo.dubbo;

import com.alibaba.dubbo.config.*;
import com.sdw.soft.demo.spi.SpiInterface;
import com.sdw.soft.demo.spi.impl.AService;
import org.junit.Test;

/**
 * Created by shangyindong on 2017/8/10.
 */
public class DubboApiTest {

    @Test
    public void testProvider() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("ProviderService");
        ProviderConfig providerConfig = new ProviderConfig();
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(12345);
        protocolConfig.setThreads(10);

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("127.0.0.1:9090");
        registryConfig.setUsername("admin");
        registryConfig.setPassword("admin");

        MonitorConfig monitorConfig = new MonitorConfig();

        ServiceConfig<SpiInterface> serviceConfig = new ServiceConfig<SpiInterface>();
        serviceConfig.setInterface(SpiInterface.class);
        serviceConfig.setApplication(applicationConfig);
        serviceConfig.setProvider(providerConfig);
        serviceConfig.setProtocol(protocolConfig);
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.setMonitor(monitorConfig);
        serviceConfig.setVersion("1.0.0");
        serviceConfig.setRef(new AService());

        serviceConfig.export();
    }

    @Test
    public void testConsumer() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("ConsumerService");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("127.0.0.1:9090");
        registryConfig.setUsername("admin");
        registryConfig.setPassword("admin");

        ReferenceConfig<SpiInterface> referenceConfig = new ReferenceConfig<SpiInterface>();
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface(SpiInterface.class);

        referenceConfig.setVersion("1.0.0");
        SpiInterface spiInterface = referenceConfig.get();
        spiInterface.sayHello();
    }

    public static void main(String[] args) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(contextClassLoader.getClass().getCanonicalName());
    }
}
