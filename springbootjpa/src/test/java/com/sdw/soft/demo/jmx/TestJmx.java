package com.sdw.soft.demo.jmx;

import com.sun.jdmk.comm.HtmlAdaptorServer;
import org.junit.Test;

import javax.management.*;
import javax.management.remote.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by shangyindong on 2018/5/5.
 */
public class TestJmx {

    @Test
    public void test01() throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, IOException {
        MBeanServer server = MBeanServerFactory.createMBeanServer();
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        String domainName = "MyMBean";
        //ObjectName中的取名是有一定规范的，格式为：“域名：name=MBean名称”，其中域名和MBean的名称可以任意取。这样定义后，就可以唯一标识我们定义的这个MBean的实现类了。
        ObjectName userName = new ObjectName(domainName + ":name=HelloWorld");
        platformMBeanServer.registerMBean(new User(), userName);

        ObjectName adapterName = new ObjectName(domainName + ":name=htmlAdapter,port=8082");
        HtmlAdaptorServer adaptorServer = new HtmlAdaptorServer();
        adaptorServer.start();
        platformMBeanServer.registerMBean(adaptorServer, adapterName);

        int rmiPort = 1099;
        Registry registry = LocateRegistry.createRegistry(rmiPort);

        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + rmiPort + "/" + domainName);
        JMXConnectorServer jmxConnectorServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, platformMBeanServer);
        jmxConnectorServer.start();
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test02() throws IOException, MBeanException, InstanceNotFoundException, ReflectionException, IntrospectionException, AttributeNotFoundException, InvalidAttributeValueException, MalformedObjectNameException {
        String domainName = "MyMBean";
        int rmiPort = 1099;
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:"+rmiPort+"/"+domainName);
        JMXConnector jmxc = JMXConnectorFactory.connect(url);
        MBeanServerConnection mBeanServerConnection = jmxc.getMBeanServerConnection();

        //print domains
        System.out.println("Domains:------------------");
        String domains[] = mBeanServerConnection.getDomains();
        for(int i=0;i<domains.length;i++){
            System.out.println("\tDomain["+i+"] = "+domains[i]);
        }
        //MBean count
        System.out.println("MBean count = "+mBeanServerConnection.getMBeanCount());
        //process attribute
        ObjectName mBeanName = new ObjectName(domainName+":name=HelloWorld");
        mBeanServerConnection.setAttribute(mBeanName, new Attribute("Name","Jack"));//注意这里是Name而不是name
        System.out.println("Name = "+mBeanServerConnection.getAttribute(mBeanName, "Name"));

        //接下去是执行Hello中的printHello方法，分别通过代理和rmi的方式执行
        //via proxy
        UserMBean proxy = MBeanServerInvocationHandler.newProxyInstance(mBeanServerConnection, mBeanName, UserMBean.class, false);
        proxy.printHello();
        proxy.printHello("Tom");
        //via rmi
        mBeanServerConnection.invoke(mBeanName, "printHello", null, null);
        mBeanServerConnection.invoke(mBeanName, "printHello", new String[]{"Rose"}, new String[]{String.class.getName()});

        //get mbean information
        MBeanInfo info = mBeanServerConnection.getMBeanInfo(mBeanName);
        System.out.println("User Class: "+info.getClassName());
        for(int i=0;i<info.getAttributes().length;i++){
            System.out.println("User Attribute:"+info.getAttributes()[i].getName());
        }
        for(int i=0;i<info.getOperations().length;i++){
            System.out.println("User Operation:"+info.getOperations()[i].getName());
        }

        //ObjectName of MBean
        System.out.println("all ObjectName:--------------");
        Set<ObjectInstance> set = mBeanServerConnection.queryMBeans(null, null);
        for(Iterator<ObjectInstance> it = set.iterator(); it.hasNext();){
            ObjectInstance oi = it.next();
            System.out.println("\t"+oi.getObjectName());
        }
        jmxc.close();
    }
}
