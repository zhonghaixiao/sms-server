package com.zongzi.smsserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    String code;
    String message;
    Object data;

    public static Result ok(String message){
        return ok(message, null);
    }

    public static Result ok(String message, Object data){
        return Result.builder().code("0").message(message).data(data).build();
    }

    public static Result fail(String message){
        return fail(message, null);
    }

    public static Result fail(String message, Object data){
        return Result.builder().code("1").message(message).data(data).build();
    }


}
