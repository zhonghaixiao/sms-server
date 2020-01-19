package com.zongzi.smsserver.service;

import com.alibaba.fastjson.JSON;
import com.zongzi.smsserver.domain.SmsResponse;
import com.zongzi.smsserver.domain.SmsSendResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

@Component
public class SmsProvider {

    // 阿里云短信地址
    public static String baseUrl = "http://dysmsapi.aliyuncs.com/";
    //
    public static String accessKeyId = "LTAI4FvJuhsCLM4NvbBU4NU7";
    public static String accessSecret = "RF5TX5Cuh7bf8GQeDe9pJ2BF6n1Qjc";

    private CloseableHttpClient httpClient;

    @PostConstruct
    void init(){
        httpClient = HttpClients.createDefault();
    }

    @PreDestroy
    void destroy(){
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 请求结构：https://dysmsapi.aliyuncs.com/?Action=SendSms&<公共请求参数>
     *
     */

    public SmsSendResponse sendSmsRequest(HashMap<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(baseUrl);
        sb.append("?");
        sb.append(buildParams(params));
        HttpGet httpGet = new HttpGet(sb.toString());
        System.out.println("request = " + httpGet.getRequestLine());
        try {
            SmsSendResponse smsSendResponse = httpClient.execute(httpGet, sendResponseResponseHandler);
            System.out.println("response = " + smsSendResponse);
            return smsSendResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String buildParams(HashMap<String, String> params) {
        String sortQueryString = null;
        try {
            sortQueryString = generateParamStr(params);
            String sign = generateSignature(sortQueryString);
            return sortQueryString + "&Signature=" + specialUrlEncode(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    private ResponseHandler<SmsSendResponse> sendResponseResponseHandler = response -> {
        int status = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        if (status >= 200 && status < 300){
            String temp = EntityUtils.toString(entity, "Utf-8");
            return JSON.parseObject(temp, SmsSendResponse.class);
        }else{
            System.out.println(EntityUtils.toString(entity, "Utf-8"));
            return null;
        }
    };




//    public void initParams()


    /**
     * 请求签名生成
     * 使用AccessKeyID和AccessKeySecret对称加密验证
     */
    private String generateSignature(String sortQueryString) throws Exception {
        // pop签名规则：* HTTPMethod + “&” + specialUrlEncode(“/”) + ”&” + specialUrlEncode(sortedQueryString)
        StringBuilder stringToSign = new StringBuilder();
        stringToSign.append("GET").append("&").append(specialUrlEncode("/")).append("&").append(specialUrlEncode(sortQueryString.toString()));
        System.out.println("stringToSign = " + stringToSign);
        String sign = sign(accessSecret + "&", stringToSign.toString());
        System.out.println("sign = " + sign);
        return sign;
    }

    private String generateParamStr(Map<String, String> params) throws Exception {
        if (params.containsKey("Signature")){
            params.remove("Signature");
        }
        TreeMap<String,String> sortedParams = new TreeMap<>();
        sortedParams.putAll(params);
        Iterator<String> it = sortedParams.keySet().iterator();
        StringBuilder sortQueryString = new StringBuilder();
        boolean first = true;
        while (it.hasNext()){
            String key = it.next();
            if (first){
                first = false;
            }else{
                sortQueryString.append("&");
            }
            sortQueryString.append(specialUrlEncode(key)).append("=").append(specialUrlEncode(params.get(key)));
        }
        System.out.println("sortQueryString = " + sortQueryString.toString());
        return sortQueryString.toString();
    }

    private static String sign(String accessSecret, String stringToSign) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSha1");
        mac.init(new SecretKeySpec(accessSecret.getBytes("UTF-8"), "HmacSha1"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        return new BASE64Encoder().encode(signData);
    }

    private static String specialUrlEncode(String value) throws Exception {
        return URLEncoder.encode(value, "UTF-8")
                .replace("+", "%20")
                .replace("*", "%2A")
                .replace("%7E", "~");
    }
}
















