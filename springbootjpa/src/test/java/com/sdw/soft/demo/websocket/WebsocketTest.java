package com.sdw.soft.demo.websocket;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.junit.Test;

/**
 * Created by shangyd on 2017/9/22.
 */

public class WebsocketTest {

    @Test
    public void test01() {
        try {
            final Socket socket = IO.socket("http://localhost:3000");
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    socket.emit("login","{userid:123, username:admin}");
                    socket.disconnect();
                }
            }).on("login", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {

                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... objects) {

                }
            });
            socket.connect();
            socket.emit("login","{userid:123, username:admin}");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
