package com.github.quelcom.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class Get extends Request {
    public static final String TAG = Get.class.getSimpleName();

    @Override
    public String fetch(String url) {
        String response = "";
        url = getRequestUrl() + url;
        DefaultHttpClient client = new DefaultHttpClient();
        final HttpParams params = client.getParams();

        try {
            HttpConnectionParams.setConnectionTimeout(params, getTimeout());
            HttpConnectionParams.setSoTimeout(params, getTimeout());
            ConnManagerParams.setTimeout(params, getTimeout());

            HttpGet httpGet = new HttpGet(url);
            HttpResponse execute = client.execute(httpGet);
            InputStream content = execute.getEntity().getContent();

            BufferedReader buffer = new BufferedReader( new InputStreamReader(content) );
            String s = "";
            while ((s = buffer.readLine()) != null) {
                response += s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client = null;
        }

        return response;
    }

}
