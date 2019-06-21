package com.sdw.soft.demo.websocket;

import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.engineio.client.Transport;
import okhttp3.OkHttpClient;
import org.json.JSONObject;

import javax.net.ssl.*;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author: shangyd
 * @create: 2019-05-07 15:19:46
 **/
public class WssSpider {

    private HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
    }

    private X509TrustManager x509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    private SSLContext createSSLContext(X509TrustManager trustManager) {
        try {
            TrustManager[] trustManagers = {trustManager};
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagers, new SecureRandom());
            return sslContext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void spider() {
        try {
            X509TrustManager trustManager = x509TrustManager();
            SSLContext sslContext = createSSLContext(trustManager);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .hostnameVerifier(getHostnameVerifier()).sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                    .build();

            IO.setDefaultOkHttpWebSocketFactory(okHttpClient);
            IO.setDefaultOkHttpCallFactory(okHttpClient);
            IO.Options opts = new IO.Options();
            opts.callFactory = okHttpClient;
            opts.webSocketFactory = okHttpClient;
            opts.port = 443;
            opts.secure = true;
//            opts.transports = new String[]{"websocket"};
//            final Socket socket = IO.socket("https://socket-io-tweet-stream.now.sh/socket.io/?EIO=3&transport=websocket&sid=IG9GQCfN-WSkXP9DAzD3",opts);
            final Socket socket = IO.socket("https://premws-pt1.365lpodds.com/zap/?uid=510543396827521",opts);
            socket.io().on(Manager.EVENT_TRANSPORT, objects -> {

                Transport transport = (Transport) objects[0];
                transport.on(Transport.EVENT_REQUEST_HEADERS, args -> {

                    Map<String, List<String>> headers = (Map<String, List<String>>) args[0];
                    headers.put("Sec-WebSocket-Extensions", Arrays.asList("permessage-deflate;client_max_window_bits"));
                    headers.put("Sec-WebSocket-Protocol", Arrays.asList("zap-protocol-v1"));
                    headers.put("Sec-WebSocket-Version", Arrays.asList("13"));

                });
                transport.on(Transport.EVENT_RESPONSE_HEADERS, (args) -> {
                    Map<String, List<String>> headers = (Map<String, List<String>>) args[0];
                    System.out.println("Response headers:"+headers);

                });

            });

            socket.on(Socket.EVENT_CONNECT, args -> {
                System.out.println("connected successful...");
            }).on(Socket.EVENT_DISCONNECT, args -> {
                System.out.println("disconnected ...");
            }).on("OnMsg",args -> {
                JSONObject jsonObject = (JSONObject) args[0];
                System.out.println(jsonObject.toString());
            }).on(Socket.EVENT_CONNECT_ERROR,args -> {
                System.out.println("connect error" + com.alibaba.fastjson.JSONObject.toJSONString(args));
            }).on(Socket.EVENT_RECONNECT_ERROR,args -> {
                System.out.println("reconnect error" + com.alibaba.fastjson.JSONObject.toJSONString(args));
            }).on(Socket.EVENT_ERROR,args -> {
                System.out.println("error");
            });

            socket.connect();
//            String msg = Delimiters.messageSessionId("22B6E3D9729C47209D6F894B67C13A1F000003");

            if (socket.connected()) {
                String msg = fromCharCode(35) + fromCharCode(3) + fromCharCode(80) + fromCharCode(1) + "__time,S_5A013083E0864D1C9421E65BAD45D0A7000003" + fromCharCode(0);
                System.out.println(msg);
                socket.emit("OnMsg", msg);
                System.out.println("success");
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private String fromCharCode(int code) {
        return String.valueOf((char)code);
    }
    public static void main(String[] args) {
        new WssSpider().spider();
    }
}
