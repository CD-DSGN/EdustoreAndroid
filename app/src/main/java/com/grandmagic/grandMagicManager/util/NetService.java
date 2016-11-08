package com.grandmagic.grandMagicManager.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by zhangmengqi on 2016/11/2.
 */
public class NetService {
    public NetService() {
    }

    public static byte[] sendPostRequest(String path, Map<String, String> params, String encoding) throws Exception {
        StringBuilder sb = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            Iterator info = params.entrySet().iterator();

            while (info.hasNext()) {
                Entry data = (Entry) info.next();
                sb.append((String) data.getKey()).append("=");
                sb.append(URLEncoder.encode((String) data.getValue(), encoding));
                sb.append("&");
            }

            sb.deleteCharAt(sb.length() - 1);
        }

        String info1 = sb.toString();
        byte[] data1 = info1.getBytes();
        HttpURLConnection conn = (HttpURLConnection) (new URL(path)).openConnection();
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
        conn.setRequestProperty("Accept-Language", "zh-CN");
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(data1.length));
        conn.setRequestProperty("Connection", "Keep-Alive");
        OutputStream outStream = conn.getOutputStream();
        byte[] buffer = new byte[1024];
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data1);
        boolean len = true;

        int len1;
        while ((len1 = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len1);
        }

        outStream.flush();
        outStream.close();
        return conn.getResponseCode() == 200 ? readStream(conn.getInputStream()) : null;
    }

    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        boolean len = true;

        int len1;
        while ((len1 = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len1);
        }

        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }
}
