package com.example.baseproject.modules.oAuth;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.baseproject.advice.exception.custom.AppException;
import com.example.baseproject.common.utils.HttpUtils;
import com.example.baseproject.common.utils.ResultUtil;
import com.example.baseproject.common.utils.WxQrCode;
import com.example.baseproject.common.utils.WxResultUtil;
import com.example.baseproject.modules.jpa.entity.WeiXinResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Company
 * @Discription
 * @Author guoxiaojing
 * @CreateDate 2018/2/1 9:21
 * @Version 1.0
 */
@Controller
@RequestMapping("/wechat")
public class WechatAuthController {
    private static Logger logger = LoggerFactory.getLogger(WeiboAuthController.class);
    private static String path = "F:\\wxQrcode";
    private static String app_id = "wxe73802f1b19fe312";
    private static String app_secret="13d73ceddc07b56381c7219970d7e676";
//    private static String app_id = "wxc426c90c0b59d7a4";
//    private static String app_secret="f1116eab0d321a796d8c6f3d35e58ed7";

    /**
     * 获取access_token
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
    public static String getAccessToken(String appid, String appsecret) {
        String result = WxResultUtil.getAccessToken(appid,appsecret);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (null != jsonObject) {
            try {
                result = jsonObject.getString("access_token");
            } catch (JSONException e) {
                logger.info("获取token失败 errcode:"+jsonObject.getInteger("errcode") +",errmsg:"+ jsonObject.getString("errmsg"));
            }
        }
        return result;
    }

    //获取二维码ticket后，通过ticket换取二维码图片
    @ResponseBody
    @RequestMapping("/getQrcode")
    public ResultUtil getQrcode(){
        ResultUtil resultUtil = new ResultUtil();
        String accessToken = getAccessToken(app_id, app_secret);
        try {
            WeiXinResult weiXinResult = WxQrCode.showQrcode(accessToken, path);
            resultUtil.put(weiXinResult);
        }catch (Exception e){
            e.printStackTrace();
        }

        return resultUtil;
    }

    /**
     * 微信登录
     * @param regType
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/doLogin")
    public ModelAndView getMainTask(@RequestParam(defaultValue = "common") String regType,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception{
        //用户扫码登录后会传个code
        String code = request.getParameter("code");
        Map<String , String> token = getAccessToken(code);
        Map<String , String> userInfo = tokengetUserWechatInfo(token);
        if (StringUtils.isBlank(userInfo.get("name"))){
            return new ModelAndView(new RedirectView("/user/doLogin", true));
        }
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

        getUri.append("https://api.weixin.qq.com/sns/oauth2/access_token");
        getUri.append("?grant_type=authorization_code");
        getUri.append("&code=").append(code);
        getUri.append("&appid=").append(app_id);
        getUri.append("&secret=").append(app_secret);

        String responseDate= HttpUtils.getRemotoHttpGetData(getUri.toString());
        try {
            logger.info("微信oauth获取accestoken：\r\n"+responseDate);
            if(StringUtils.isNotBlank(responseDate) && responseDate.indexOf("access_token") != -1){
                JSONObject jsonData = JSONObject.parseObject(responseDate);
                token.put("accessToken", jsonData.getString("access_token"));
                token.put("uid", jsonData.getString("openid"));
            }else{
                throw new AppException("微信oauth获取accestoken失败 \r\n"+responseDate);
            }
        } catch (Exception e) {
            logger.error("微信oauth获取accestoken异常",e);
        }
        return    token;
    }

    private Map<String , String> tokengetUserWechatInfo(Map<String , String> token){
        Map<String , String> userData = new HashMap<String, String>();
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+token.get("accessToken")+"&openid="+token.get("uid") + "&lang=zh_CN";
        try {
            String  UserInfo = HttpUtils.getRemotoHttpGetData(url);
            logger.info("微信oauth获取用户信息\r\n"+UserInfo);
            JSONObject jsonData = JSONObject.parseObject(UserInfo);
            userData.put("name",jsonData.getString("nickname"));
            userData.put("uid", jsonData.getString("unionid"));
        } catch (Exception e) {
            logger.error("微信oauth获取用户信息异常",e);
        }
        return userData;
    }
}
