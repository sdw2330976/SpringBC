package com.sdw.soft.demo.quartz;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by shangyd on 2018/6/3.
 */
public class ScheduleTest {


    @Test
    public void test01() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    long time = System.currentTimeMillis() / 1000 * 1000;
//                    URL url = new URL("http://www.310tv.com/data/change.xml?" + time);
                    URL url = new URL("http://www.310tv.com/data/change.xml?1528036604000");
                    HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    urlConnection.setUseCaches(false);
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                    urlConnection.setRequestProperty("Host","www.310tv.com");
                    urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36");
                    urlConnection.connect();
                    int responseCode = urlConnection.getResponseCode();
//                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = urlConnection.getInputStream();
                        int len = 0;
                        byte[] buffer = new byte[1024];
                        StringBuffer sb = new StringBuffer();
                        while ((len = inputStream.read(buffer)) != -1) {
                            sb.append(new String(buffer,0,len));
                        }
                        System.out.println(sb.toString());
                        inputStream.close();
                        urlConnection.disconnect();
//                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 5, TimeUnit.SECONDS);

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
