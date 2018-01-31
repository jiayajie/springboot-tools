package com.example.baseproject.modules.oAuth;

import com.alibaba.fastjson.JSONObject;
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
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * qq认证登录
 * @Company
 * @Discription
 * @Author guoxiaojing
 * @CreateDate 2018/1/31 10:15
 * @Version 1.0
 */
@Controller
@RequestMapping("/tencent")
public class TencentAuthController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static String client_id="101357797";
    private static String client_secret="6ef3d067aeda978e2b0adda7e24bc8c9";
    private static String authUrl = "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=%s&redirect_uri=%s";
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
        String redirect_uri = "http://127.0.0.1:8080".concat("/tencent/doLogin");
        return String.format(authUrl, client_id, URLEncoder.encode(redirect_uri, "utf-8"));
    }

    @ResponseBody
    @RequestMapping(value = "/doLogin")
    public ModelAndView getMainTask(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String code = request.getParameter("code");
        Map<String , String> token = getAccessToken(code);
        String userOpenId = getUserOpenId(token);
        String userName = getUserWeiBoInfo(token,userOpenId);

        if (StringUtils.isBlank(userName)){
            return new ModelAndView(new RedirectView("/user/doLogin", true));
        }
        HttpSession session = request.getSession();
        //TODO如果userOpenID和userName可以拿到，然后验证此用户是否已经存在，存在就自动登录，否则保存用户信息并自动登录。以后修改

        return new ModelAndView(new RedirectView("/user/doLogin", true));
    }

    /**
     * 获取accestoken
     * @param code 获取accessToken的钥匙
     * @return
     */
    private Map<String , String> getAccessToken(String code){
        Map<String , String> token = new HashMap<String, String>();
        StringBuilder getUri = new StringBuilder();

        getUri.append("https://graph.qq.com/oauth2.0/token");
        getUri.append("?grant_type=authorization_code");
        getUri.append("&code=").append(code);
        getUri.append("&redirect_uri=http://127.0.0.1:8080/tencent/doLogin");
        getUri.append("&client_id=").append(client_id);
        getUri.append("&client_secret=").append(client_secret);

        String responseDate= HttpUtils.getRemotoHttpGetData(getUri.toString());

        if(!responseDate.equals("") && responseDate.indexOf("access_token") != -1){
            String result[] = responseDate.split("&");
            for (String value:result) {
                String item[] = value.split("=");
                token.put(item[0],item[1]);
            }
        }
        return    token;
    }

    private String getUserOpenId(Map<String , String> token){
        String userOpenId = "";
        StringBuilder getUri = new StringBuilder();
        getUri.append("https://graph.qq.com/oauth2.0/me?");
        getUri.append("?access_token=").append(token.get("access_token"));
        try {
            //callback( {"client_id":"101357797","openid":"1EB268DC6A9CF7C07D1F64523BCD5674"} );
            String responseDate=HttpUtils.getRemotoHttpGetData(getUri.toString());
            String result = responseDate.replace("callback( ","").replace(")","");
            JSONObject jsonData = JSONObject.parseObject(result);
            userOpenId = (String)jsonData.get("openid");
        } catch (Exception e) {
            logger.error("QQ oauth获取用户openID",e);
        }
        return userOpenId;
    }

    private String getUserWeiBoInfo(Map<String , String> token,String openid){
        String userName = "";
        StringBuilder getUri = new StringBuilder();
        getUri.append("https://graph.qq.com/user/get_user_info");
        getUri.append("?access_token=").append(token.get("access_token"));
        getUri.append("&oauth_consumer_key=").append(client_id);
        getUri.append("&openid=").append(openid);
        try {
            String userInfo=HttpUtils.getRemotoHttpGetData(getUri.toString());
            JSONObject jsonData = JSONObject.parseObject(userInfo);
            userName = jsonData.getString("nickname").toString();
        } catch (Exception e) {
            logger.error("QQ oauth获取用户信息",e);
        }
        return userName;
    }
}
