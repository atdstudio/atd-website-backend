package com.atd.official.tools;

public enum ErrorEnum {
    /*
     * 错误信息
     * */
    E_400("400", "请求处理异常，请稍后再试"),
    E_500("500", "请求方式错误"),
    E_501("501", "请求路径不存在"),
    E_502("502", "权限不足"),


    E_3001("3001","请输入验证码"),
    E_3002("3002","验证码已过期"),
    E_3003("3003","验证码不正确"),


    E_4001("4001","用户名长度应在3至16位"),
    E_4002("4002","密码长度应在8至16位"),
    E_4003("4003","邮箱格式不正确"),


    E_10009("10009", "账户已存在"),
    E_10010("10010", "该邮箱已被注册"),
    E_10011("10011","该邮箱还没有注册"),
    E_20012("20012", "您已经登录过了"),
    E_20011("20011", "请登录后操作"),
    E_20010("20010","账号或密码错误"),


    E_00000("00000","服务器错误"),

    E_90003("90003", "缺少必填参数");


    private String errorCode;

    private String errorMsg;

    ErrorEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
