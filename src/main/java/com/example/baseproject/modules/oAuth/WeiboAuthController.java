package com.example.baseproject.modules.oAuth;

import com.alibaba.fastjson.JSONObject;
import com.example.baseproject.advice.exception.custom.AppException;
import com.example.baseproject.common.utils.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Company
 * @Discription
 * @Author guoxiaojing
 * @CreateDate 2018/1/31 11:05
 * @Version 1.0
 */
@Controller
@RequestMapping("/weibo")
public class WeiboAuthController {
    private static Logger log = LoggerFactory.getLogger(WeiboAuthController.class);
    //微博认证登录跳转链接
    private static String authUrl="https://api.weibo.com/oauth2/authorize?client_id=%s&response_type=code&redirect_uri=%s";
    private static String client_id="4205048682";
    private static String client_secret="73178b4010c6dedbcc5e9a1081b5b2ae";

    /**
     * http://localhost:8080/index.html
     * 点击提交跳到微博登录页
     * 生成微博认证URL认证
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/toAuth")
    public String toAuth() throws Exception {
        String redirect_uri = "http://127.0.0.1:8080".concat("/weibo/doLogin");
        return String.format(authUrl, client_id, URLEncoder.encode(redirect_uri, "utf-8"));
    }

    @ResponseBody
    @RequestMapping(value = "/doLogin")
    public ModelAndView getMainTask(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String code = request.getParameter("code");

        if(StringUtils.isBlank(code)){
            return new ModelAndView(new RedirectView("/user/toLogin", true));
        }
        Map<String , String> token = getAccessTokenAndUid(code);
        Map<String , String> userInfo = getUserWeiBoInfo(token);
        if (StringUtils.isBlank(userInfo.get("name"))){
            log.info("微博登录用户名为空，转注册页面登录");
            return new ModelAndView(new RedirectView("/user/toLogin", true));
        }
        //TODO如果userOpenID和userName可以拿到，然后验证此用户是否已经存在，存在就自动登录，否则保存用户信息并自动登录。以后修改
        return new ModelAndView(new RedirectView("/user/toLogin", true));
    }

    /**
     * 获取accestoken和用户在新浪微博的uid
     * @param code 获取accessToken的钥匙
     * @return
     */
    private Map<String , String> getAccessTokenAndUid(String code) throws Exception {
        Map<String , String> token = new HashMap<String, String>();
        StringBuilder getUri = new StringBuilder();

        getUri.append("https://api.weibo.com/oauth2/access_token");
        getUri.append("?grant_type=authorization_code");
        getUri.append("&code=").append(code);
        getUri.append("&redirect_uri=http://127.0.0.1:8080/weibo/doLogin");
        getUri.append("&client_id=").append(client_id);
        getUri.append("&client_secret=").append(client_secret);

        String responseDate= HttpUtils.getRemotoHttpGetData(getUri.toString());

        log.info("获取accestoken和用户在新浪微博的uid\r\n"+responseDate);
        if(StringUtils.isNotBlank(responseDate) && responseDate.indexOf("access_token") > -1){
            JSONObject jsonData = JSONObject.parseObject(responseDate);
            token.put("accessToken", (String)jsonData.get("access_token"));
            token.put("uid", jsonData.getString("uid"));
        }else{
            throw new AppException("获取accestoken和用户在新浪微博的uid失败");
        }
        return    token;
    }

    private Map<String , String> getUserWeiBoInfo(Map<String , String> token){
        Map<String , String> userData = new HashMap<String, String>();
        String url = "https://api.weibo.com/2/users/show.json?access_token="+token.get("accessToken")+"&uid="+token.get("uid");
        try {
            String UserInfo = HttpUtils.getRemotoHttpGetData(url);
            log.info("获取微博用户信息:\r\n"+UserInfo);
            JSONObject jsonData = JSONObject.parseObject(UserInfo);
            userData.put("name",jsonData.getString("name"));
//            userData.put("headImg", jsonData.getString("profile_image_url"));
        } catch (Exception e) {
            log.error("获取微博用户信息",e);
        }
        return userData;
    }
}
