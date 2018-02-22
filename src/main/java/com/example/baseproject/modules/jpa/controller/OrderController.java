package com.example.baseproject.modules.jpa.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.example.baseproject.common.Const;
import com.example.baseproject.common.enums.ResultEnum;
import com.example.baseproject.common.model.ResultEntity;
import com.example.baseproject.common.utils.ResultUtil;
import com.example.baseproject.modules.jpa.entity.UserModel;
import com.example.baseproject.modules.jpa.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 订单接口
 *
 * @author dongyaofeng
 * @date 2018/2/7 23:26
 */
@RestController
@RequestMapping("/orders/")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private OrderService orderService;

    @GetMapping("pay/{userId}/{orderNo}")
    public ResultEntity pay(@PathVariable Long orderNo, @PathVariable Integer userId) {
        ResultEntity pay = orderService.pay(orderNo, userId, "/code");
        return pay;
    }

    @GetMapping("alipay_callback")
    public ResultEntity alipayCallback(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Iterator<String> iterator = parameterMap.keySet().iterator(); iterator.hasNext(); ) {

            String name = iterator.next();
            String[] values = parameterMap.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1 ? valueStr + values[i] : valueStr + values[i] + ",");
            }
            params.put(name, valueStr);
        }

        logger.info("支付宝回调,sign{},trade_status:{},参数:{}", params.get("sign"), params.get("trade_status"), params.toString());

        //验证回调正确性
        params.remove("sign_type");
        try {
            boolean checkV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());

            if (!checkV2) {
                return ResultUtil.error(400, "非法请求!!!");
            }

        } catch (AlipayApiException e) {
            logger.error("支付宝回调异常", e);
        }

        //todo  验证各种数据

        ResultEntity callback = orderService.aliCallback(params);

        //回调成功
        if (callback.isSuccess()) {
            return ResultUtil.success(Const.AlipayCallback.RESPONSE_SUCCESS);
        }

        //回调失败
        return ResultUtil.error(400, Const.AlipayCallback.RESPONSE_FAILED);
    }


    /**
     * 查询订单状态
     */
    @GetMapping("status/{orderNo}")
    public ResultEntity<Boolean> queryOrderPayStatus(HttpSession httpSession, @PathVariable Long orderNo) {
        UserModel user = (UserModel) httpSession.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ResultUtil.error(ResultEnum.UNAUTHORIZE.getCode(), "没有登录");
        }

        ResultEntity resultEntity = orderService.queryOrderPayStatus(user.getId(), orderNo);

        if (resultEntity.isSuccess()) {
            return ResultUtil.success(true);
        }
        return ResultUtil.success(false);
    }
}
