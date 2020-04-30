package com.atd.official.exception;

import com.atd.official.tools.CommonUtil;
import com.atd.official.tools.ErrorEnum;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public JSONObject defaultErrorHandler(HttpServletRequest req, Exception e) {
        String errorPosition = "";
        Boolean debug = true;
        //如果错误堆栈信息存在
        if (e.getStackTrace().length > 0) {
            StackTraceElement element = e.getStackTrace()[0];
            String fileName = element.getFileName() == null ? "未找到错误文件" : element.getFileName();
            int lineNumber = element.getLineNumber();
            errorPosition = fileName + ":" + lineNumber;
        }
        logger.error("错误位置  "+errorPosition);
        logger.error("错误堆栈",e);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Status", 0);
        if(debug){
            JSONObject errorObject = new JSONObject();
            errorObject.put("ErrorLocation", e.toString() + "    错误位置:" + errorPosition);
            jsonObject.put("Info", errorObject);
            return jsonObject;
        }
        else {
            jsonObject.put("Result", "服务器错误");
            return jsonObject;
        }
    }


    /**
     * GET/POST请求方法错误的拦截器
     * 因为开发时可能比较常见,而且发生在进入controller之前,上面的拦截器拦截不到这个错误
     * 所以定义了这个拦截器
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public JSONObject httpRequestMethodHandler() {
        return CommonUtil.errorJson(ErrorEnum.E_500);
    }

    /**
     * 本系统自定义错误的拦截器
     * 拦截到此错误之后,就返回这个类里面的json给前端
     * 常见使用场景是参数校验失败,抛出此错,返回错误信息给前端
     */
    @ExceptionHandler(CommonJsonException.class)
    public JSONObject commonJsonExceptionHandler(CommonJsonException commonJsonException) {
        return commonJsonException.getResultJson();
    }

    /**
     * 权限不足报错拦截
     */
    @ExceptionHandler(UnauthorizedException.class)
    public JSONObject unauthorizedExceptionHandler() {
        return CommonUtil.errorJson(ErrorEnum.E_502);
    }

    /**
     * 未登录报错拦截
     * 在请求需要权限的接口,而连登录都还没登录的时候,会报此错
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public JSONObject unauthenticatedException() {
        return CommonUtil.errorJson(ErrorEnum.E_20011);
    }

}
