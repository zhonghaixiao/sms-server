package com.zongzi.smsserver.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SmsProviderTest {

    @Autowired
    SendSmsService sendSmsService;

    @Test
    void sendSmsRequest() throws IllegalAccessException {
        sendSmsService.sendSms("18260631671", "1234");
    }
}