package com.sdw.soft.snippets.zookeeper;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * @author: shangyd
 * @create: 2019-07-15 09:44:50
 **/
public class ZkClientTest {


    public static void main(String[] args) {
        zkClientTest01();
    }
    private static void zkClientTest01() {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);
        String path = "/zk-test";

        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println("父节点 " + parentPath + "下的子节点变更  子节点为:" + currentChilds);
            }
        });

        zkClient.createPersistent("/zk-test/a1", true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(zkClient.getChildren(path));
    }

}
