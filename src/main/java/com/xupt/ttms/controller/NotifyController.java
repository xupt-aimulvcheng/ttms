package com.xupt.ttms.controller;

import com.xupt.ttms.service.AliPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * 支付回调
 * </p>
 *
 * @SINCE 2022/2/20 2:53
 * @AUTHOR CHENKAIFANG
 * @Date: 2022/2/20 02:53
 */
@RestController()
@RequestMapping("notify")
public class NotifyController {

    @Autowired
    private AliPayService aliPayService;

    /**
     * 功能描述:  支付成功回调
     *
     * @author CHENKAIFANG
     * @date 2022/2/20 14:14
     * @since JDK 1.8
     */
    @RequestMapping(value = "alipay")
    public String pay(HttpServletRequest request) {
//        System.out.println("支付成功回调！");
        int im = 0;
        im++;
        //测试请求次数
//        System.out.println("次数:" + im);
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String outTradeNo = request.getParameter("out_trade_no");
//        System.out.println("商户订单号: " + outTradeNo);
        //支付宝交易号
        String tradeNo = request.getParameter("trade_no");
//        System.out.println("支付宝交易号: " + tradeNo);
        //支付宝交易金额
        String totalAmount = request.getParameter("total_amount");
//        System.out.println("支付宝交易金额: " + totalAmount);
        //交易状态
        String tradeStatus = request.getParameter("trade_status");
//        System.out.println("交易状态: " + tradeStatus);
//        System.out.println("params: " + JSONObject.toJSONString(params));
        return aliPayService.rsaCheck(params);
    }
}
