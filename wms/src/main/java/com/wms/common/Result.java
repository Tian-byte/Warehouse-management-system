package com.wms.common;

import io.swagger.models.auth.In;
import lombok.Data;

import javax.websocket.OnClose;
import java.nio.file.LinkOption;
import java.time.LocalDate;

@Data
public class Result {
    private int code;
    private String msg;   //成功，失败
    private Long total;//总记录数
    private Object data; // 数据

    public static Result fail(){
        return result(400,"失败",0L,null);
    }
    public static Result success(){
        return result(200,"成功 ",0L,null);
    }

    public static Result success(Object data){
        return result(200,"成功",0L,data);
    }


    public static Result success(Object data, Long total){
        return result(200,"成功 ",total,data);
    }

    private static Result result(Integer code, String msg, Long total,Object data){
        Result res = new Result();
        res.setData(data);
        res.setMsg(msg);
        res.setCode(code);
        res.setTotal(total);
        return res;
    }
}
