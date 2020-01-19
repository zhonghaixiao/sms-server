package com.zongzi.smsserver.controller;

import com.zongzi.smsserver.domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class HelloController {

    @RequestMapping("/hello")
    public Object test(){
        return new User("zhong", 27);
    }

    @PostMapping("newUser")
    public String testPost(@RequestParam String userName, @RequestParam int age){
        return "success";
    }

}
