package com.zongzi.smsserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSendResponse {
    String BizId; //发送回执ID，可根据该ID在接口QuerySendDetails中查询具体的发送状态。
    String Code; //请求状态码。返回OK代表请求成功。其他错误码详见错误码列表。
    String Message; // 状态码的描述。
    String RequestId; //请求 ID。无论调用接口成功与否，都会返回请求 ID。
}
