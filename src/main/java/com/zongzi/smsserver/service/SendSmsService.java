package com.zongzi.smsserver.service;

import com.alibaba.fastjson.JSON;
import com.zongzi.smsserver.domain.SmsResponse;
import com.zongzi.smsserver.domain.SmsSendRequest;
import com.zongzi.smsserver.domain.SmsSendResponse;
import org.apache.commons.beanutils.BeanMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SendSmsService {

    @Autowired
    private SmsProvider smsProvider;

    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    {
        format.setTimeZone(new SimpleTimeZone(0, "GMT"));
    }

    public SmsSendResponse sendSms(String phoneNumber, String code) throws IllegalAccessException {
        HashMap<String,String> templateParamMap = new HashMap<>();
        templateParamMap.put("code", code);
        SmsSendRequest request = SmsSendRequest.builder()
                .OutId("test out id")
                .AccessKeyId(SmsProvider.accessKeyId)
                .Action("SendSms")
                .Format("json")
                .PhoneNumbers(phoneNumber)
                .SignName("沂涛大排档")
                .RegionId("cn-hangzhou")
                .SignatureNonce(UUID.randomUUID().toString())
                .SignatureMethod("HMAC-SHA1")
                .SignatureVersion("1.0")
                .SmsUpExtendCode("123")
                .TemplateCode("SMS_182672611")
                .TemplateParam(JSON.toJSONString(templateParamMap))
                .Timestamp(format.format(new Date()))
                .Version("2017-05-25").build();

        HashMap<String, String> params = new HashMap<>();
        for (Field field : SmsSendRequest.class.getDeclaredFields()){
            field.setAccessible(true);
            String fieldName = field.getName();
            String value = (String)field.get(request);
            params.put(fieldName, value);
        }
        SmsSendResponse response = smsProvider.sendSmsRequest(params);
        System.out.println("SmsSendResponse = " + response);
        return response;
    }

}
