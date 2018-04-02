package com.sdw.soft.test.log4j2;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shangyd on 2018/3/27.
 */
public class Log4j2Test {

    private static final Logger logger = LoggerFactory.getLogger(Log4j2Test.class);

    @Test
    public void test01() {
        while (true) {
            logger.info("test01====");
        }
    }
}
