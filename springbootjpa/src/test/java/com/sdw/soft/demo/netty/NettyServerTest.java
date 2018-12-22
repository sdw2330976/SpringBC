package com.sdw.soft.demo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shangyd on 2018/7/16.
 */
public class NettyServerTest {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerTest.class);
    @Test
    public void test01() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();//apect thread
        EventLoopGroup workGroup = new NioEventLoopGroup();//deal SocketChannel read and write

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .handler(new LoggingServerHandler())
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {

                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(8099).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    class LoggingServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            logger.info("logging channel active");
        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            logger.info("logging channel registered");
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            logger.info("logging handler added");
        }
    }
}
