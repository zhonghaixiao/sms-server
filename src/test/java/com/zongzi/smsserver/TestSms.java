package com.zongzi.smsserver;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.junit.jupiter.api.Test;

public class TestSms {

    @Test
    void sendSms(){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FvJuhsCLM4NvbBU4NU7", "RF5TX5Cuh7bf8GQeDe9pJ2BF6n1Qjc");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test1(){
        String str1 = "GET&%2F&AccessKeyId%3DLTAI4FvJuhsCLM4NvbBU4NU7%26Action%3DSendSms%26Format%3Djson%26OutId%3Dtest%2520out%2520id%26PhoneNumbers%3D18260631671%26RegionId%3Dcn-hangzhou%26SignName%3D%25E6%25B2%2582%25E6%25B6%259B%25E5%25A4%25A7%25E6%258E%2592%25E6%25A1%25A3%26SignatureMethod%3DHMAC-SHA1%26SignatureNonce%3Deb48d509-2f46-4367-a4d4-bca22f500943%26SignatureVersion%3D1.0%26SmsUpExtendCode%3DSmsUpExtendCode%26TemplateCode%3DSMS_182672611%26TemplateParam%3D%257B%2522code%2522%253A%25221234%2522%257D%26Timestamp%3D2020-01-19T15%253A45%253A33Z%26Version%3D2017-05-25";
        String str2 = "GET&%2F&AccessKeyId%3DLTAI4FvJuhsCLM4NvbBU4NU7%26Action%3DSendSms%26Format%3Djson%26OutId%3Dtest%2520out%2520id%26PhoneNumbers%3D18260631671%26RegionId%3Dcn-hangzhou%26SignName%3D%25E6%25B2%2582%25E6%25B6%259B%25E5%25A4%25A7%25E6%258E%2592%25E6%25A1%25A3%26SignatureMethod%3DHMAC-SHA1%26SignatureNonce%3Deb48d509-2f46-4367-a4d4-bca22f500943%26SignatureVersion%3D1.0%26SmsUpExtendCode%3DSmsUpExtendCode%26TemplateCode%3DSMS_182672611%26TemplateParam%3D%257B%2522code%2522%253A%25221234%2522%257D%26Timestamp%3D2020-01-19T15%253A45%253A33Z%26Version%3D2017-05-25";
        System.out.println(str1.equals(str2));
        for (int i = 0; i < str1.length(); i++){
            if (str1.charAt(i)==str2.charAt(i)){
                continue;
            }else{
                System.out.println(i);
                break;
            }
        }
    }

}
