package com.sdw.soft.demo.websocket;

import com.google.common.base.Joiner;

/**
 * @author: shangyd
 * @create: 2019-05-07 15:41:03
 **/
public class Delimiters {
    private static final String DELIMITERS_RECORD = "\\x01";
    private static final String DELIMITERS_FIELD = "\\x02";
    private static final String DELIMITERS_HANDSHAKE = "\\x03";
    private static final String DELIMITERS_MESSAGE = "\\x08";


    private static final String ENCODING_NONE = "\\x00";

    private static final String TYPES_TOPIC_LOAD_MESSAGE = "\\x14";
    private static final String TYPES_DELTA_MESSAGE = "\\x15";

    private static final String TYPES_SUBSCRIBE = "\\x16";
    private static final String TYPES_PING_CLIENT = "\\x19";
    private static final String TYPES_TOPIC_STATUS_NOTIFICATION = "\\x23";


    public static String messageSessionId(String sessionId) {
        return Joiner.on("").useForNull("").join(TYPES_TOPIC_STATUS_NOTIFICATION, DELIMITERS_HANDSHAKE, "P", DELIMITERS_RECORD, "__time,S_", sessionId, ENCODING_NONE);
    }


}
