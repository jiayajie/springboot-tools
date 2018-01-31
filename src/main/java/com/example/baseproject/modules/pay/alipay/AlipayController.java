package com.example.baseproject.modules.pay.alipay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import net.guerlab.sdk.alipay.controller.AlipayAbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dongyaofeng
 * @date 18-2-1 上午12:12
 */
@Controller
@RequestMapping("/pay/alipay")
public class AlipayController extends AlipayAbstractController {

    @Autowired
    private AlipayClient client;//支付宝请求sdk客户端

    /**
     * 支付请求
     */
    @GetMapping("/app/{orderId}")
    public String app(
            @PathVariable Long orderId,
            HttpServletResponse httpResponse) throws AlipayApiException {

        JSONObject data = new JSONObject();
        data.put("out_trade_no", "201701010000001234"); //商户订单号
        data.put("product_code", "QUICK_MSECURITY_PAY"); //产品码, APP支付 QUICK_MSECURITY_PAY, PC支付 FAST_INSTANT_TRADE_PAY, 移动H5支付 QUICK_WAP_PAY
        data.put("total_amount", "0.01"); //订单金额
        data.put("subject", "测试订单"); //订单标题

        //APP支付
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //PC支付
        //AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //移动H5支付
        //AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setNotifyUrl("https://www.baidu.com"); //异步通知地址
        request.setBizContent(data.toJSONString()); //业务参数

        return client.sdkExecute(request).getBody();
    }

    @PostMapping("/notify/{orderId}")
    public String notify(
            @PathVariable Long orderId,
            HttpServletRequest request) {
        if (!notify0(request.getParameterMap())) {
            //这里处理验签失败
        }

        request.getParameter("trade_no");//获取请求参数中的商户订单号

        return "success";
    }

}
