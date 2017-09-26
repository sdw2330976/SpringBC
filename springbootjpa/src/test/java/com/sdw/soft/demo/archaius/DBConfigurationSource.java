package com.sdw.soft.demo.archaius;

import com.google.common.collect.Maps;
import com.netflix.config.PollResult;
import com.netflix.config.PolledConfigurationSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Created by shangyd on 2017/4/27.
 */
public class DBConfigurationSource implements PolledConfigurationSource {

    @Override
    public PollResult poll(boolean initial, Object checkPoint) throws Exception {

        Map<String, Object> props = Maps.newHashMap();
        Properties properties = new Properties();
//        properties.load(DBConfigurationSource.class.getResourceAsStream("/config.properties"));
        InputStream is = new FileInputStream(new File("/Users/shangyd/mywork/workspace/workspace_github/springbootjpa/src/test/resources/file.properties"));
        properties.load(is);
        System.out.println("poll properties----------");
        for (Object key : properties.keySet()) {
            props.put(String.valueOf(key), properties.get(key));
        }
        return PollResult.createFull(props);
    }
}
