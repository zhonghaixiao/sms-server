package com.zongzi.smsserver.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendSmsBatchReq {
    String PhoneNumber;
    String SignName;
    String TemplateCode;
    Map<String,String> TemplateParam;
}
