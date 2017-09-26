package com.sdw.soft.demo.consul;

import com.google.common.base.Optional;
import com.orbitz.consul.*;
import com.orbitz.consul.async.ConsulResponseCallback;
import com.orbitz.consul.cache.ConsulCache;
import com.orbitz.consul.cache.ServiceHealthCache;
import com.orbitz.consul.cache.ServiceHealthKey;
import com.orbitz.consul.model.ConsulResponse;
import com.orbitz.consul.model.agent.Agent;
import com.orbitz.consul.model.health.ServiceHealth;
import com.orbitz.consul.model.kv.Value;
import com.orbitz.consul.option.QueryOptions;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by shangyd on 2017/5/11.
 */
public class TestConsulClient {

    private Consul consul = null;

    @Before
    public void setup(){
        consul = Consul.builder().build();
    }
    /**
     * Register and check you servive in with Consul
     */
    @Test
    public void test01() throws Exception{

        AgentClient agentClient = consul.agentClient();
        String serviceName = "myService";
        String serviceID = "1";
        agentClient.register(8081,10L,serviceName,serviceID);//register with a TTL of 3 seconds
        //check in with consul,serviceId require only. client will prepend "service:" for service level checks.
        //note that you need to continually check in before the TTL expires,otherwise your service's state will be marked as "critical".
        agentClient.pass(serviceID);

    }

    /**
     * Find available (healthy) services
     */
    @Test
    public void test02(){
        HealthClient healthClient = consul.healthClient();
        //discovery only "passing" nodes
        List<ServiceHealth> nodes = healthClient.getHealthyServiceInstances("myService").getResponse();
        System.out.println(nodes);
    }

    /**
     * Store Key/Value
     */
    @Test
    public void test03(){
        KeyValueClient kvClient = consul.keyValueClient();
        kvClient.putValue("foo", "bar");
        System.out.println(kvClient.getValueAsString("foo").get());
    }

    /**
     * Blocking call for value
     */
    @Test
    public void test04() throws Exception{
        final KeyValueClient kvClient = consul.keyValueClient();
        kvClient.putValue("foo", "bar");

        ConsulResponseCallback<Optional<Value>> callback = new ConsulResponseCallback<Optional<Value>>() {

            AtomicReference<BigInteger> index = new AtomicReference<BigInteger>(null);

            @Override
            public void onComplete(ConsulResponse<Optional<Value>> consulResponse) {
                if (consulResponse.getResponse().isPresent()) {
                    Value v = consulResponse.getResponse().get();
                    System.out.println("value is :" + v.getValue());
                }
                index.set(consulResponse.getIndex());
                watch();
            }

            void watch() {
                kvClient.getValue("foo", QueryOptions.blockMinutes(5, index.get()).build(), this);
            }
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("occur error:" + throwable);
                watch();
            }
        };
        kvClient.getValue("foo", QueryOptions.blockMinutes(5, new BigInteger("0")).build(), callback);
    }


    /**
     * Subscribe to healthy services
     */
    @Test
    public void test05(){
        Agent agent = consul.agentClient().getAgent();
        HealthClient healthClient = consul.healthClient();
        String serviceName = "myService";
        ServiceHealthCache serviceHealthCache = ServiceHealthCache.newCache(healthClient,serviceName);
        serviceHealthCache.addListener(new ConsulCache.Listener<ServiceHealthKey, ServiceHealth>() {
            @Override
            public void notify(Map<ServiceHealthKey, ServiceHealth> newValues) {
                //do something with updated server map
            }
        });
    }

    /**
     * Find Raft peers
     */
    @Test
    public void test06(){
        StatusClient statusClient = consul.statusClient();
        for (String peer : statusClient.getPeers()) {
            System.out.println(peer);
        }
    }

    /**
     * Find Raft leader
     */
    @Test
    public void test07(){
        StatusClient statusClient = consul.statusClient();
        System.out.println(statusClient.getLeader());
    }
}
