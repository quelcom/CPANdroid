package com.github.quelcom.net;

public abstract class Request {
    private static final String REQUEST_URL = "http://api.metacpan.org";

    public static String getRequestUrl() {
        return REQUEST_URL;
    }

    public abstract String fetch(String url);
}
