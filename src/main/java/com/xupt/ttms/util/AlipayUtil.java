package com.xupt.ttms.util;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xupt.ttms.pojo.AlipayBean;
import com.xupt.ttms.pojo.Order;
import com.xupt.ttms.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 功能描述: 支付宝常用类
 *
 * @author CHENKAIFANG
 * @date 2022/2/20 2:43
 * @since JDK 1.8
 */
@Component
@Slf4j
@PropertySource("classpath:alipay.properties")
public class AlipayUtil {

    public static final String TRADE_NO = "trade_no";
    public static final String OUT_REQUEST_NO = "out_request_no";
    public static final String REFUND_AMOUNT = "refund_amount";
    public static final String OUT_TRADE_NO = "out_trade_no";
    public static final String TOTAL_AMOUNT = "total_amount";
    public static final String SUBJECT = "subject";
    public static final String BODY = "body";
    public static final String TIMEOUT_EXPRESS = "timeout_express";
    public static final String PRODUCT_CODE = "product_code";
    private final String FORMAT = "json";
    @Value("${app_id}")
    private String appId;
    @Value("${gatewayUrl}")
    private String gatewayUrl;
    @Value("${merchant_private_key}")
    private String merchantPrivateKey;
    @Value("${charset}")
    private String charset;
    @Value("${alipay_public_key}")
    private String alipayPublicKey;
    @Value("${sign_type}")
    private String signType;
    @Value("${return_url}")
    private String returnUrl;
    @Value("${notify_url}")
    private String notifyUrl;
    @Resource
    private OrderService orderService;
    @Resource
    private Environment config;

    /**
     * 功能描述:  支付测试
     *
     * @param alipayBean 支付实体对象
     * @return {@link String}
     * @author CHENKAIFANG
     * @date 2022/2/20 2:43
     * @since JDK 1.8
     */
    public String alipay(AlipayBean alipayBean) throws AlipayApiException {
        // 1.订单参数组装
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);
        JSONObject bizContent = new JSONObject();
        bizContent.put(OUT_TRADE_NO, alipayBean.getOutTradeNo());
        bizContent.put(TOTAL_AMOUNT, alipayBean.getTotalAmount());
        bizContent.put(SUBJECT, alipayBean.getSubject());
        bizContent.put(BODY, alipayBean.getBody());
        bizContent.put(TIMEOUT_EXPRESS, alipayBean.getTimeoutExpress());
        bizContent.put(PRODUCT_CODE, alipayBean.getProductCode());
        alipayRequest.setBizContent(bizContent.toJSONString());

        // 2.接口参数调用
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                appId,
                merchantPrivateKey,
                FORMAT,
                charset,
                alipayPublicKey,
                signType);

        // 3.接口调用<原理就是返回的form表单。然后执行而已>
        String body = alipayClient.pageExecute(alipayRequest).getBody();
        return body;
    }

    /**
     * 功能描述: 支付回调验签
     *
     * @param params      获取支付宝POST过来反馈信息
     * @return {@link Boolean}
     * @author CHENKAIFANG
     * @date 2022/2/20 15:20
     */
    public String rsaCheck(Map<String, String> params) {
        String result = "";
        try {
            boolean verifyResult = AlipaySignature.rsaCheckV1(params,
                    alipayPublicKey,
                    charset,
                    signType);
            if (verifyResult) {
                //验证成功
                //请在这里加上商户的业务逻辑程序代码
                //1.验证通知数据中的out_trade_no 是否为商户系统中创建的订单号
                String outTradeNo = params.get("out_trade_no");
                LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Order::getOrderNo, outTradeNo);
                Order order = orderService.getOne(wrapper);
                if (order == null) {
                    log.error("订单不存在");
                    return result;
                }
                //2.判断total_amount 是否确认为该订单的实际金额
                String totalAmount = params.get("total_amount");
                int totalAmountInt = new BigDecimal(totalAmount).stripTrailingZeros().intValue();
                int amount = order.getAmount().intValue();
                if (amount != totalAmountInt) {
                    log.error("金额不对");
                    return result;
                }
                //3.验证通知中的seller_id(或者 sell_email) 是否为 out_trade_no 这笔单据的对应的操作方
                String sellerId = params.get("seller_id");
                String sellerIdProperty = config.getProperty("alipay.seller-id");
                if (!sellerId.equals(sellerIdProperty)) {
                    log.error("商家pid校验失败");
                    return result;
                }
                //4.验证app_id 是否为该商户本身
                String appId = params.get("app_id");
                String appIdProperty = config.getProperty("alipay.app-id");
                if (!appId.equals(appIdProperty)) {
                    log.error("app_id校验失败");
                    return result;
                }
                String tradeStatus = params.get("trade_status");
                if (!"TRADE_SUCCESS".equals(tradeStatus)) {
                    log.error("支付未成功");
                    return result;
                }
                log.info("returnUrl_params: 验证成功");
                //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                boolean flg = false;
                if ("TRADE_FINISHED".equals(tradeStatus)) {
                    log.info("returnUrl_params: 交易结束");
                    //交易结束,不可退款
                    //注意：
                    //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                } else if ("TRADE_SUCCESS".equals(tradeStatus)) {
                    //交易支付成功
                    //注意：
                    //付款完成后，支付宝系统发送该交易状态通知
                    //根据订单号将订单状态和支付宝记录表中状态都改为已支付
                    log.info("returnUrl_params: 交易支付成功");
                    flg = true;
                }
                //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                if (flg) {
                    orderService.payOrder(order);
                    return "success";
                } else {
                    return "fail";
                }
            } else {
                return "fail";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "fail";
        }
    }


    /**
     * 功能描述: 支付宝退款
     *
     * @param alipayBean 支付实体对象
     * @return {@link String}
     * @author CHENKAIFANG
     * @date 2022/2/20 14:21
     * @since JDK 1.8
     */
    public String alipayRefound(AlipayBean alipayBean) throws AlipayApiException {
        Map map = new LinkedHashMap();
        // 1.接口参数调用
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                appId,
                merchantPrivateKey,
                FORMAT,
                charset,
                alipayPublicKey,
                signType);
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put(TRADE_NO, alipayBean.getTradeNo());
        bizContent.put(OUT_REQUEST_NO, alipayBean.getOutRequestNo());
        bizContent.put(REFUND_AMOUNT, alipayBean.getRefundAmount());
        AlipayTradeRefundResponse response = null;
        request.setBizContent(bizContent.toString());
        try {
            response = alipayClient.execute(request);
            System.out.println(JSONObject.toJSONString(response));
        } catch (AlipayApiException e) {
            String massage = "alipay.trade.refund退款接口：订单签名错误";
            System.out.println(massage);
        }
        if (response.isSuccess()) {
            System.out.println("调用成功");
            // 订单退款  status：0 成功 1:失败
            map.put("status", 0);
            log.info("支付宝：支付订单支付结果查询：订单out_trade_no----" + alipayBean.getTradeNo() + "---订单退款成功！");
        } else {
            System.out.println("调用失败");
            // 订单退款  status：0 成功 1:失败
            map.put("status", 1);
            log.info("支付宝：支付订单支付结果查询：订单out_trade_no----" + alipayBean.getTradeNo() + "---订单退款失败！");
        }
        return JSONObject.toJSONString(map);
    }
}
