package com.github.quelcom.net;

public abstract class Request {
    private static final String REQUEST_URL = "http://api.metacpan.org";
    private static final int TIMEOUT = 10000; // 10 secs

    public static String getRequestUrl() {
        return REQUEST_URL;
    }

    public static int getTimeout() {
        return TIMEOUT;
    }

    public abstract String fetch(String url);
}
