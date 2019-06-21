package com.sdw.soft.snippets.tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

/**
 * @author: shangyd
 * @create: 2019-06-21 10:52:03
 **/
public class TomcatBootstrap {

    public static void main(String[] args) {
        try {
            Tomcat tomcat = new Tomcat();
            Connector connector = new Connector("HTTP/1.1");
            connector.setPort(8080);
            tomcat.setConnector(connector);
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}
