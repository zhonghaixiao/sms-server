package com.zongzi.smsserver.controller;

import com.zongzi.smsserver.domain.Result;
import com.zongzi.smsserver.req.SendSmsReq;
import com.zongzi.smsserver.service.SendSmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("sms")
@RestController
public class SmsController {

    @Autowired
    SendSmsService sendSmsService;

    /**
     * 发送验证码
     * @return
     */
    @RequestMapping("/sendSms")
    public Result sendSms(@RequestBody SendSmsReq req){
        try {
            sendSmsService.sendSms(req.getPhoneNumber(), req.getCode());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return Result.ok("success");
    }

}
