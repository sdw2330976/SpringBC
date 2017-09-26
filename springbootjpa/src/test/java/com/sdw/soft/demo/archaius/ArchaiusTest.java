package com.sdw.soft.demo.archaius;

import com.netflix.config.*;
import com.netflix.config.jmx.ConfigJMXManager;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by shangyd on 2017/4/27.
 */
public class ArchaiusTest {


    private static final int SESSION_TIMEOUT = 5000;
    private static final String CONNECTION_STRING = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    private static final String ROOT_PATH = "/relottery";


    public static class ArchaiusJunit{

        public static CuratorFramework curatorClient;
        public static ZooKeeperConfigurationSource zkSource;
//        @Before
        public void setup() throws Exception {
            curatorClient = CuratorFrameworkFactory.newClient(CONNECTION_STRING, new ExponentialBackoffRetry(1000, 3));
            curatorClient.start();

            zkSource = new ZooKeeperConfigurationSource(curatorClient,ROOT_PATH);
            zkSource.start();
            DynamicWatchedConfiguration dynamicWatchedConfiguration = new DynamicWatchedConfiguration(zkSource);
            ConcurrentCompositeConfiguration concurrentCompositeConfiguration = new ConcurrentCompositeConfiguration();
            concurrentCompositeConfiguration.addConfiguration(dynamicWatchedConfiguration);
            setZKProperty("keepalive","123456");
            ConfigurationManager.install(concurrentCompositeConfiguration);
        }


        @Test
        public void test20(){

        }
        @Test
        public void test019() throws Exception{
            SystemConfiguration systemConfiguration = new SystemConfiguration();
            PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration("config.properties");
            propertiesConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());

            ConcurrentCompositeConfiguration configuration = new ConcurrentCompositeConfiguration();
            configuration.addConfiguration(propertiesConfiguration);
            configuration.addConfiguration(systemConfiguration);
            ConfigurationManager.install(configuration);

            System.out.println(DynamicPropertyFactory.getInstance().getStringProperty("java.home","NAN").get());
            System.out.println(DynamicPropertyFactory.getInstance().getIntProperty("keepalive", 10, new Runnable() {
                @Override
                public void run() {
                    System.out.println("current property has changed.");
                }
            }).get());

        }

        @Test
        public void test01() {
            DBConfigurationSource source = new DBConfigurationSource();
            AbstractPollingScheduler scheduler = new FixedDelayPollingScheduler();
            DynamicConfiguration dynamicConfiguration = new DynamicConfiguration(source, scheduler);
            ConfigurationManager.install(dynamicConfiguration);
            ConfigJMXManager.registerConfigMbean(dynamicConfiguration);
            DynamicIntProperty property = DynamicPropertyFactory.getInstance().getIntProperty("keepalive", 1);

            System.out.println("keepalive=" + property.get());


        }

        @Test
        public void test02() throws Exception{
            String result = curatorClient.create().creatingParentsIfNeeded().forPath("/relottery/properties.keepalive", "123456".getBytes());
            System.out.println("result=" + result);
        }

        @Test
        public void test03() throws Exception {
//            setZKProperty("keepalive","11111");
            DynamicStringProperty property = DynamicPropertyFactory.getInstance().getStringProperty("keepalive", "0");
            while (true) {
                System.out.println("property=" + property.get());
                Thread.sleep(2000);
            }
        }

        @Test
        public void test04() throws Exception{
            setZKProperty("keepalive","3333");
        }


        @Test
        public void test05() throws Exception {
            String fileName = "config.properties";
            ConcurrentMapConfiguration configFromPropertiesFile = new ConcurrentMapConfiguration(new PropertiesConfiguration(fileName));
            ConcurrentMapConfiguration configFromSystemProperties = new ConcurrentMapConfiguration(new SystemConfiguration());
            PolledConfigurationSource source = new DBConfigurationSource();
            AbstractPollingScheduler scheduler = new FixedDelayPollingScheduler();
            DynamicConfiguration dynamicConfiguration = new DynamicConfiguration(source, scheduler);

            ConcurrentCompositeConfiguration concurrentCompositeConfiguration = new ConcurrentCompositeConfiguration();
            concurrentCompositeConfiguration.addConfigurationListener(new ConfigurationListener() {
                @Override
                public void configurationChanged(ConfigurationEvent event) {
                    System.out.println("-----------configuration occur changed :" + event.getPropertyName() + "," + event.getPropertyValue());
                }
            });
            concurrentCompositeConfiguration.addConfiguration(dynamicConfiguration,"dynamicConfig");
            concurrentCompositeConfiguration.addConfiguration(configFromSystemProperties,"systemConfig");
            concurrentCompositeConfiguration.addConfiguration(configFromPropertiesFile,"fileConfig");

            ConfigurationManager.install(concurrentCompositeConfiguration);

            DynamicStringProperty keepalive = DynamicPropertyFactory.getInstance().getStringProperty("keepalive", "0");
            System.out.println("result=" + keepalive.get());

        }

        public void setZKProperty(String key, String value) throws Exception {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            zkSource.addUpdateListener(new WatchedUpdateListener() {
                @Override
                public void updateConfiguration(WatchedUpdateResult result) {
                    countDownLatch.countDown();
                }
            });
            zkSource.setZkProperty(key, value);
            countDownLatch.await();
        }
    }
}
