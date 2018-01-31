package com.example.baseproject.common.utils;

import com.example.baseproject.modules.jpa.entity.UserModel;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户注册认证、自动登录
 * @Company
 * @Discription
 * @Author guoxiaojing
 * @CreateDate 2018/1/31 10:59
 * @Version 1.0
 */
public class UserUtil {
    public static boolean validateUserName(String userName){
        if(StringUtils.isBlank(userName)){
            return false;
        }else{
            if(userName.trim().length()<4 || userName.trim().length()>32){
                return false;
            }
        }
        return true;
    }

    public static boolean validatePassword(String password) {
        if(password.trim().length()<6 || password.trim().length()>20){
            return false;
        }

        char  str = password.charAt(0);
        for (int i = 0; i < password.length(); i++) {
            if (str != password.charAt(i)) {
                return  true;
            }
        }
        /*if (password.matches("\\d+")) {
            return false;
        }
        if (password.matches("[A-Za-z]+")) {
            return false;
        }*/
        return true;
    }

    public static boolean validateEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        //验证登录名是不是邮箱格式
        Matcher matcher = regex.matcher(email);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    //验证手机号码是否正确
    public static boolean validatePhone(String phone){
        if(StringUtils.isBlank(phone)){
            return false;
        }
        String check = "^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0135-9])|(14[57])|(166)|(19[89]))\\d{8}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(phone);
        boolean isMatched = matcher.matches();
        return isMatched;
    }


    //设置session信息
    public static boolean setLoginSession(HttpSession session, UserModel user, String[] otherParaNames, String[]otherParams) {
        //设置用户信息
        session.setAttribute("user", user);
        session.setAttribute("loginName", user.getUsername());
        session.setAttribute("userId",user.getPassworld());
        session.setAttribute("userOrg", null);
        session.setMaxInactiveInterval(60*60);
        //设置其他信息
        if (otherParaNames != null && otherParams != null) {
            for (int i = 0; i < otherParaNames.length; i++) {
                session.setAttribute(otherParaNames[i], otherParams[i]);
            }
        }
        return true;
    }

    public static String userPhoneFilter(String phone){
        if(StringUtils.isEmpty(phone)){
            return "";
        }
        char[] array = phone.toCharArray();
        String result="";
        for(int i=0;i<array.length;i++){
            if(i>2&&i<7){
                result+= "*";
            }else{
                result+= array[i];
            }
        }
        return result;
    }
}
