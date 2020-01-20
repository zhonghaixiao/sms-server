package com.zongzi.smsserver.service;

import com.alibaba.fastjson.JSON;
import com.zongzi.smsserver.domain.SmsSendBatchRequest;
import com.zongzi.smsserver.domain.SmsSendRequest;
import com.zongzi.smsserver.domain.SmsSendResponse;
import com.zongzi.smsserver.req.SendSmsBatchReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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

        SmsSendResponse response = smsProvider.sendSmsRequest(objectToMap(request, SmsSendRequest.class));
        System.out.println("SmsSendResponse = " + response);
        return response;
    }

    public SmsSendResponse sendSmsBatch(List<SendSmsBatchReq> req) throws IllegalAccessException {
        Map<String, String> params = buildSmsBatchRequest(req);
        SmsSendResponse response = smsProvider.sendSmsRequest(params);
        return response;
    }

    private Map<String,String> objectToMap(Object request, Class clazz) throws IllegalAccessException {
        HashMap<String, String> params = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()){
            field.setAccessible(true);
            String fieldName = field.getName();
            String value = (String)field.get(request);
            params.put(fieldName, value);
        }
        return params;
    }

    private Map<String, String> buildSmsBatchRequest(List<SendSmsBatchReq> req) throws IllegalAccessException {

        List<String> phoneNumberList = new ArrayList<>();
        List<String> signNameList = new ArrayList<>();
        List<String> signUpExtendCodeList = new ArrayList<>();
        List<String> templateParamList = new ArrayList<>();

        for (SendSmsBatchReq smsBatchReq: req){
            phoneNumberList.add(smsBatchReq.getPhoneNumber());
            signNameList.add(smsBatchReq.getSignName());
            signUpExtendCodeList.add("123");
            templateParamList.add(JSON.toJSONString(smsBatchReq.getTemplateParam()));
        }

        SmsSendBatchRequest request = SmsSendBatchRequest.builder()
                .AccessKeyId(SmsProvider.accessKeyId)
                .Action("SendBatchSms")
                .Format("json")
                .PhoneNumberJson(JSON.toJSONString(phoneNumberList))
                .SignNameJson(JSON.toJSONString(signNameList))
                .RegionId("cn-hangzhou")
                .SignatureNonce(UUID.randomUUID().toString())
                .SignatureMethod("HMAC-SHA1")
                .SignatureVersion("1.0")
                .SmsUpExtendCodeJson(JSON.toJSONString(signUpExtendCodeList))
                .TemplateCode(req.get(0).getTemplateCode())
                .TemplateParamJson(JSON.toJSONString(templateParamList))
                .Timestamp(format.format(new Date()))
                .Version("2017-05-25").build();

        return objectToMap(request, SmsSendBatchRequest.class);
    }

}
