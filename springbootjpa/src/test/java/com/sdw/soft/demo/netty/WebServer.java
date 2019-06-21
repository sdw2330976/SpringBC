package com.sdw.soft.demo.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: shangyd
 * @create: 2019-04-17 19:27:59
 **/
public class WebServer {

    private WebServer() {

    }

    private static class WebServerHolder{
        private static WebServer server = new WebServer();
    }
    public static WebServer getInstance() {
        return WebServerHolder.server;
    }

    public void init() throws Exception {
        //打开一个选择器
        Selector selector = Selector.open();
        //打开ServerSocketChannel通道
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //得到ServerSocket对象
        ServerSocket socket = ssc.socket();
        socket.bind(new InetSocketAddress(8089));
        socket.setReuseAddress(true);
        //设置通道为非阻塞
        ssc.configureBlocking(false);
        //将ServerSocketChannel注册给选择器，并绑定ACCEPT事件
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        while (selector.isOpen()) {
            //查询准备就绪的通道数
            int readyChannels = selector.select();
            //没有就绪通道则继续循环
            if (readyChannels == 0) {
                continue;
            }
            //获得就绪的SelectionKey的set集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();//安全删除原集合中的元素
                iterator.remove();
                try {
                    if (!next.isValid()) {//当前的key是否有效
                        continue;
                    } else if (next.isAcceptable()) {//ACCEPT事件
                        SocketChannel accept = ssc.accept();
                        accept.configureBlocking(false);
                        accept.register(selector, SelectionKey.OP_READ).attach(new Worker(accept));
                    } else if (next.isReadable()) {//Read事件
                        Worker worker = (Worker) next.attachment();
                        executor.execute(worker);
                    } else if (next.isWritable()) {//Write事件

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println(next.channel());
                }
            }
        }
    }


    private static class Worker implements Runnable {
        private SocketChannel socketChannel;
        private ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);

        public Worker(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {

            try {
                int read = socketChannel.read(byteBuffer);
                while (read > 0) {
                    byteBuffer.flip();
                    byte[] buf = new byte[byteBuffer.limit()];
                    byteBuffer.get(buf);
                    System.out.println(new String(buf));
                    byteBuffer.clear();
                    read = socketChannel.read(byteBuffer);
                }

                StringBuilder sb = new StringBuilder();
                sb.append("HTTP/1.1 200 ok").append("\n")
                        .append("Server: NIO WebServer By shangyd\n\n")
                        .append("<html><head><title>hello</title></head><body>hello world</body></html>");
                byteBuffer.put(sb.toString().getBytes());
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
                byteBuffer.clear();
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        WebServer server = WebServer.getInstance();
        try {
            server.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}