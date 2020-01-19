package com.zongzi.smsserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsResponse {
    String RequestId; //请求 ID。无论调用接口成功与否，都会返回请求 ID。
}
