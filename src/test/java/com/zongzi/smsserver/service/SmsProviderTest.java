package com.zongzi.smsserver.service;

import com.zongzi.smsserver.req.SendSmsBatchReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SmsProviderTest {

    @Autowired
    SendSmsService sendSmsService;

    @Test
    void sendSmsRequest() throws IllegalAccessException {
        sendSmsService.sendSms("18260631671", "1234");
    }

    @Test
    void sendSmsBatchRequest() {
        List<SendSmsBatchReq> reqs = new ArrayList<>();
        HashMap<String,String> params1 = new HashMap<>();
        params1.put("code", "1111");
        HashMap<String,String> params2 = new HashMap<>();
        params2.put("code", "2222");
        reqs.add(SendSmsBatchReq.builder()
                .SignName("沂涛大排档")
                .TemplateCode("SMS_182672611")
                .PhoneNumber("18260631671")
                .TemplateParam(params1).build());
        reqs.add(SendSmsBatchReq.builder()
                .SignName("沂涛大排档")
                .TemplateCode("SMS_182672611")
                .PhoneNumber("18168411671")
                .TemplateParam(params2).build());
        try {
            sendSmsService.sendSmsBatch(reqs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}