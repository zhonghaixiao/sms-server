package com.zongzi.smsserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmsSendRequest{
    String PhoneNumbers;  //接收短信的手机号码。
    String SignName; //短信签名名称
    String TemplateCode; //短信模板ID
    String OutId;//外部流水扩展字段。
    String SmsUpExtendCode; //上行短信扩展码，无特殊需要此字段的用户请忽略此字段。
    String TemplateParam; //短信模板变量对应的实际值，JSON格式 ,, {"code":"1111"}
    //公共参数
    String Signature; //请求签名，即最终生成的签名结果值。
    String AccessKeyId; //访问密钥 ID。AccessKey 用于调用 API。
    String Format; //返回参数的语言类型。取值范围：json | xml。默认值：json。
    String RegionId; //API支持的RegionID，如短信API的值为：cn-hangzhou。
    String SignatureMethod;//签名方式。取值范围：HMAC-SHA1。
    String SignatureNonce;//签名唯一随机数。用于防止网络重放攻击，建议您每一次请求都使用不同的随机数。
    // JAVA语言建议用：java.util.UUID.randomUUID()生成。
    String SignatureVersion;// 签名算法版本。取值范围：1.0。
    String Timestamp;// 请求的时间戳。按照ISO8601 标准表示，并需要使用UTC时间，格式为yyyy-MM-ddTHH:mm:ssZ。
    // 示例：2018-01-01T12:00:00Z 表示北京时间 2018 年 01 月 01 日 20 点 00 分 00 秒。
    String Version; //API 的版本号，格式为 YYYY-MM-DD。取值范围：2017-05-25。
    String Action; //API 的名称。 SendSms
}

