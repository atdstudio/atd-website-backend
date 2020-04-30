package com.atd.official.tools;

import com.atd.official.exception.CommonJsonException;
import com.alibaba.fastjson.JSONObject;

public class ParameterChecker {

    public static void loginCheck(JSONObject requestJson){
        if(     requestJson.getString("username") == null ||
                requestJson.getString("password")== null ||
                requestJson.getString("code")== null ||
                requestJson.getString("rememberMe")== null
        ){
            throw new CommonJsonException(ErrorEnum.E_90003);
        }

    }

    public static void alterPassCheck(JSONObject requestJson){
        if(     requestJson.getString("newpass") == null ||
                requestJson.getString("newpass").length() < 8 ||
                requestJson.getString("newpass").length() > 16
        ){
            throw new CommonJsonException(ErrorEnum.E_90003);
        }
    }

    public static void forgetPassCheck(JSONObject requestJson){
        if(     requestJson.getString("emailCode")== null ||
                requestJson.getString("newpass") == null ||
                requestJson.getString("newpass").length() < 8 ||
                requestJson.getString("newpass").length() > 16
        ){
            throw new CommonJsonException(ErrorEnum.E_90003);
        }
    }

    public static void registerCheck(JSONObject requestJson){
        if(     requestJson.getString("username") == null ||
                requestJson.getString("password") == null ||
                requestJson.getString("emailCode")== null
        ){
            throw new CommonJsonException(ErrorEnum.E_90003);
        }

        else if(requestJson.getString("username").length() < 3 ||
                requestJson.getString("username").length() > 16
        ){
            throw new CommonJsonException(ErrorEnum.E_4001);
        }

        else if(requestJson.getString("password").length() < 8 ||
                requestJson.getString("password").length() > 16
        ){
            throw new CommonJsonException(ErrorEnum.E_4002);
        }
    }

    public static void emailCheck(JSONObject requestJson){
        if(     requestJson.getString("code") == null ||
                requestJson.getString("email")== null
        ){
            throw new CommonJsonException(ErrorEnum.E_90003);
        }
        else if(!CommonUtil.EmailChecker(requestJson.getString("email"))){
            throw new CommonJsonException(ErrorEnum.E_4003);
        }
    }

    public static void feedbackCheck(JSONObject requestJson){
        if(     requestJson.getString("name") == null ||
                requestJson.getString("title")== null ||
                requestJson.getString("contact")== null ||
                requestJson.getString("description")== null
        ){
            throw new CommonJsonException(ErrorEnum.E_90003);
        }
    }

    public static void imageCheck(JSONObject requestJson){
        if(     requestJson.getString("Name") == null ||
                requestJson.getString("Location")== null ||
                requestJson.getString("Display")== null ||
                requestJson.getString("Link")== null
        ){
            throw new CommonJsonException(ErrorEnum.E_90003);
        }
    }

    public static void imageTextCheck(JSONObject requestJson){
        if(     requestJson.getString("Id") == null ||
                requestJson.getString("Name")== null
        ){
            throw new CommonJsonException(ErrorEnum.E_90003);
        }
    }

    public static void imageFlagCheck(JSONObject requestJson){
        if(     requestJson.getString("Id") == null ||
                requestJson.getString("Display")== null
        ){
            throw new CommonJsonException(ErrorEnum.E_90003);
        }
    }

    public static void idCheck(JSONObject requestJson){
        if(     requestJson.getString("Id") == null){
            throw new CommonJsonException(ErrorEnum.E_90003);
        }
    }

    public static void videoCheck(JSONObject requestJson){
        if(     requestJson.getString("Name") == null ||
                requestJson.getString("Intro")== null ||
                requestJson.getString("Author")== null ||
                requestJson.getString("Video_class")== null ||
                requestJson.getString("Cover_img")== null ||
                requestJson.getString("Location")== null
        ){
            throw new CommonJsonException(ErrorEnum.E_90003);
        }
    }

    public static void softWareCheck(JSONObject requestJson){
        if(     requestJson.getString("Name") == null ||
                requestJson.getString("Intro")== null ||
                requestJson.getString("Software_class")== null ||
                requestJson.getString("Cover_img")== null
        ){
            /*当Location为空时，Tmp必须为1且Temp_link不为空*/
            if(requestJson.getString("Location")== null && requestJson.getString("Temp")== null){
                throw new CommonJsonException(ErrorEnum.E_90003);
            }
            else if(requestJson.getString("Location")== null &&
                    !(requestJson.getString("Temp").equals("1")) &&
                    requestJson.getString("Temp_link") == null
            ){
                throw new CommonJsonException(ErrorEnum.E_90003);
            }
        }
    }

    public static void tempCheck(JSONObject requestJson){
        if(     requestJson.getString("Id") == null ||
                requestJson.getString("Temp") == null ||
                requestJson.getString("Temp_link") == null
        ){
            throw new CommonJsonException(ErrorEnum.E_90003);
        }
    }

}
