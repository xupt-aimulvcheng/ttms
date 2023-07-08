package com.xupt.ttms.service;

import com.alipay.api.AlipayApiException;

import java.util.Map;

public interface AliPayService {
//    /**
//     * 功能描述: 支付宝支付
//     *
//     * @param alipayBean 支付实体
//     * @return {@link String}
//     * @author CHENKAIFANG
//     * @date 2022/2/20 2:46
//     * @since JDK 1.8
//     */
    String aliPay(String orderNo) throws AlipayApiException;

    /**
     * 功能描述: 支付回调验签
     *
     * @param params 获取支付宝POST过来反馈信息
     * @return {@link Boolean}
     * @author CHENKAIFANG
     * @date 2022/2/20 15:20
     */
    String rsaCheck(Map<String, String> params);

    /**
     * 功能描述: 支付宝退款
     *
     * @param alipayBean 支付实体
     * @return {@link String}
     * @author CHENKAIFANG
     * @date 2022/2/20 14:18
     * @since JDK 1.8
     */
//    String alipayRefound(AlipayBean alipayBean) throws AlipayApiException;
}
