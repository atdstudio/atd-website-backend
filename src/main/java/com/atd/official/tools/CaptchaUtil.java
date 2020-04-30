package com.atd.official.tools;

import com.atd.official.exception.CommonJsonException;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

public class CaptchaUtil {

    @Resource
    private RedisUtil redisUtil;

    public String createCaptcha(HttpServletRequest request,HttpServletResponse response) throws IOException,NullPointerException {
        HttpSession session = request.getSession();

        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        Captcha specCaptcha = new SpecCaptcha(130, 48, 4);
        String verCode = specCaptcha.text().toLowerCase();

        specCaptcha.out(response.getOutputStream());

        return verCode;
    }

    public void check(String value, Object codeInRedis){
        if (StringUtils.isBlank(value)) {
            throw new CommonJsonException(ErrorEnum.E_3001);
        }
        if (codeInRedis == null) {
            throw new CommonJsonException(ErrorEnum.E_3002);
        }
        if (!StringUtils.equalsIgnoreCase(value, String.valueOf(codeInRedis))) {
            throw new CommonJsonException(ErrorEnum.E_3003);
        }
    }

}
