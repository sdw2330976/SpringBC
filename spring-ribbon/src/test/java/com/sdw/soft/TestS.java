package com.sdw.soft;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by shangyd on 2017/9/28.
 */
public class TestS {
    public static void main(String[] args) {
        try {
            URI uri = new URI("http://EUREKA.SERVICE-01/hello");
            System.out.println(uri.getHost());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
