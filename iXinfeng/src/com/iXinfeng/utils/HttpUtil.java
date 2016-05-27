package com.iXinfeng.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;


public class HttpUtil {

    /**
     * get Url Content
     * 
     * @param urlStr
     * @return
     */
    public static String getHttpUrlContent(String urlStr, String encoding) {
        return getHttpUrlContent(urlStr, encoding, false, null);
    }

    /**
     * 
     * @param urlStr
     * @param encoding
     * @param isPost
     * @param postBody
     * @return
     */
    public static String getHttpUrlContent(String urlStr, String encoding,
            boolean isPost, String postBody) {
        return getHttpUrlContent(urlStr, encoding, isPost, postBody, null);
    }

    /**
     * 
     * @param urlStr
     * @param encoding
     * @param isPost
     * @param postBody
     * @param requestProperty
     * @return
     */
    public static String getHttpUrlContent(String urlStr, String encoding,
            boolean isPost, String postBody, Map<String, String> requestProperty) {
        if (urlStr == null) {
            return "";
        }

        URL url = null;
        HttpURLConnection conn = null;

        // long startTime=System.currentTimeMillis();
        // log.debug("get "+urlStr+" start with "+startTime);
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();

            // 设置request header
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
            if (requestProperty != null) {
                for (Iterator<String> it = requestProperty.keySet().iterator(); it
                        .hasNext();) {
                    String key = it.next();
                    conn.setRequestProperty(key,
                            (String) requestProperty.get(key));
                }
            }
            // conn.setInstanceFollowRedirects(false);
            // post方法
            if (isPost) {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true); // 标识要向链接里输入内容
                conn.setDoInput(true);

                // 获得连接输出流
                OutputStreamWriter out = new OutputStreamWriter(
                        conn.getOutputStream(), encoding);
                // 把数据写入
                out.write(postBody);
                out.flush();
                out.close();

                // OutputStream out = conn.getOutputStream();
                // BufferedWriter writer = new BufferedWriter(new
                // OutputStreamWriter(out, encoding));
                // writer.write(postBody);
                // out.flush();
                // out.close();
                // writer.flush();
                // writer.close();
            }
            conn.connect();
            // System.out.println(conn.getResponseCode());
            // System.out.println(conn.getURL());
            InputStream is = conn.getInputStream();
            BufferedReader breader = new BufferedReader(new InputStreamReader(
                    is, encoding));
            char[] cBuf = new char[8192];
            StringBuffer buf = new StringBuffer("");
            int len = breader.read(cBuf, 0, 8192);
            while (len > 0) {
                buf.append(cBuf, 0, len);
                cBuf = new char[8192];
                len = breader.read(cBuf, 0, 8192);
            }
            breader.close();
            // log.info(urlStr+" cost "+(System.currentTimeMillis()-startTime));
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
            url = null;
        }
        // log.info(urlStr+" cost "+(System.currentTimeMillis()-startTime));
        return "";
    }

    /**
     * 
     * @param urlStr
     * @param encoding
     * @param isPost
     * @param postBody
     * @return
     */
    public static String getHttpsUrlContent(String urlStr, String encoding,
            boolean isPost, String postBody) {
        return getHttpsUrlContent(urlStr, encoding, isPost, postBody, null);
    }

    /**
     * 
     * @param urlStr
     * @param encoding
     * @param isPost
     * @param postBody
     * @param requestProperty
     * @return
     */
    public static String getHttpsUrlContent(String urlStr, String encoding,
            boolean isPost, String postBody, Map<String, String> requestProperty) {
        if (urlStr == null) {
            return "";
        }
        URL url = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[] { new X509TrustManager() {

                public void checkClientTrusted(X509Certificate[] chain,
                        String authType) throws CertificateException {
                    // TODO Auto-generated method stub

                }

                public void checkServerTrusted(X509Certificate[] chain,
                        String authType) throws CertificateException {
                    // TODO Auto-generated method stub

                }

                public X509Certificate[] getAcceptedIssuers() {
                    // TODO Auto-generated method stub
                    return null;
                }

            } }, new SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String arg0, SSLSession arg1) {
                // TODO Auto-generated method stub
                return true;
            }
        });
        HttpsURLConnection conn = null;

        // long startTime=System.currentTimeMillis();
        // log.debug("get "+urlStr+" start with "+startTime);
        try {
            url = new URL(urlStr);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setConnectTimeout(60000);
            // 设置request header
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
            if (requestProperty != null) {
                for (Iterator<String> it = requestProperty.keySet().iterator(); it
                        .hasNext();) {
                    String key = it.next();
                    conn.setRequestProperty(key,
                            (String) requestProperty.get(key));
                }
            }

            // conn.setInstanceFollowRedirects(false);
            // post方法
            if (isPost) {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true); // 标识要向链接里输入内容
                conn.setDoInput(true);

                // 获得连接输出流
                OutputStreamWriter out = new OutputStreamWriter(
                        conn.getOutputStream(), encoding);
                // 把数据写入
                out.write(postBody);
                out.flush();
                out.close();

                // OutputStream out = conn.getOutputStream();
                // BufferedWriter writer = new BufferedWriter(new
                // OutputStreamWriter(out, encoding));
                // writer.write(postBody);
                // out.flush();
                // out.close();
                // writer.flush();
                // writer.close();
            }
            conn.connect();
            // System.out.println(conn.getResponseCode());
            // System.out.println(conn.getURL());
            InputStream is = conn.getInputStream();
            BufferedReader breader = new BufferedReader(new InputStreamReader(
                    is, encoding));
            char[] cBuf = new char[8192];
            StringBuffer buf = new StringBuffer("");
            int len = breader.read(cBuf, 0, 8192);
            while (len > 0) {
                buf.append(cBuf, 0, len);
                cBuf = new char[8192];
                len = breader.read(cBuf, 0, 8192);
            }
            breader.close();
            // log.info(urlStr+" cost "+(System.currentTimeMillis()-startTime));
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
            url = null;
        }
        // log.info(urlStr+" cost "+(System.currentTimeMillis()-startTime));
        return "";
    }

    public static void main(String[] args) {

        String url = "";
        System.out.println(HttpUtil.genRetUrlParamEncoded(url));

        // String html = HttpUtil.getHttpUrlContent(url, "utf-8");
        // System.out.println("html:【" + html + "】");

    }

    /**
     * www.chuanke.com 传递过来的 retUrl 中，参数有可能带有 空格 等未编码的信息，这里需要强行编码一下
     * 
     * 为了以防以后他们会编码，这里的做法是 现对参数进行一轮解码，然后再进行编码 这样就能保证不会被2次编码
     * 
     * @param retUrl
     * @return
     */
    public static String genRetUrlParamEncoded(String retUrl) {
        int index = retUrl.indexOf('?');
        if (index > 0) {
            String[] strs = retUrl.split("\\?", 2); // 仅仅第一个问号才起作用

            String[] params = strs[1].split("&"); // 拆分所有的 参数，如果参数没有
            StringBuffer sbUrl = new StringBuffer();
            sbUrl.append(strs[0]);
            sbUrl.append('?');
            for (int j = 0; j < params.length; j++) {
                if (params[j].indexOf('=') > 0) {
                    String[] strs1 = params[j].split("=", 2); // 仅仅第一个=号才起作用
                    sbUrl.append(strs1[0]);
                    sbUrl.append("=");
                    try {
                        sbUrl.append(URLEncoder.encode(
                                URLDecoder.decode(strs1[1], "utf-8"), "utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    sbUrl.append("&");
                }
            }
            sbUrl.deleteCharAt(sbUrl.length() - 1); // 去除尾部的 &
            return sbUrl.toString();

        } else { // 没有参数，直接返回
            return retUrl;
        }
    }

    /**
     * 获取 htps 协议下的网站内容
     * 
     * 使用了 Not-Yet-Commons-SSL.jar
     * http://juliusdavies.ca/commons-ssl/download.html
     * 
     * @param urlvalue
     * @return
     */
//    public static String getHttpsContent(String urlvalue) {
//        String ret = null;
//
//        HttpClient client = new HttpClient(); // 设置代理服务器地址和端口
//
//        // 有时访问https网站时是不需要证书的。有时使用客户端证书不一定成功，
//        // 抛各种各样与证书相关的错误。这时可以使用服务端证书（self-signed
//        // certificate）。方法很简单，只需在httpClient调用之前先注销默认的Socket Factory，使用自定义的Socket
//        // Factory：
//        Protocol.unregisterProtocol("https");
//        try {
//            Protocol.registerProtocol("https", new Protocol("https",
//                    (ProtocolSocketFactory) new EasySSLProtocolSocketFactory(),
//                    443));
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
//        // 使用GET方法，如果服务器需要通过HTTPS连接，那只需要将下面URL中的http换成https
//        HttpMethod method = new GetMethod(urlvalue);
//        // 使用POST方法
//        // HttpMethod method = new PostMethod("http://java.sun.com";);
//        try {
//            client.executeMethod(method);
//            // 打印服务器返回的状态
//            // System.out.println(method.getStatusLine());
//            // 打印返回的信息
//            // System.out.println(method.getResponseBodyAsString());
//            ret = method.getResponseBodyAsString();
//            // 释放连接
//            method.releaseConnection();
//        } catch (HttpException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return ret;
//    }
}
