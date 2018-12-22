package com.sdw.soft.transaction;

import org.junit.Test;

/**
 * Created by shangyd on 2018/7/6.
 */
public class StringTest {

    @Test
    public void test01() {
        String a = "a";
        final String b = "a";
        String compare = "ab";
        String c = a + "b";
        String d = b + "b";
        System.out.println(c == compare);
        System.out.println(d == compare);
        String e = new String("ab");
        System.out.println(e == compare);
        System.out.println(e.intern() == compare);
    }
}
