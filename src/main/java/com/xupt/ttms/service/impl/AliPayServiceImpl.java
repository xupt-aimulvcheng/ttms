package com.xupt.ttms.service.impl;

import com.alipay.api.AlipayApiException;
import com.xupt.ttms.pojo.AlipayBean;
import com.xupt.ttms.pojo.Order;
import com.xupt.ttms.service.AliPayService;
import com.xupt.ttms.service.OrderService;
import com.xupt.ttms.util.AlipayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

@Service
@Slf4j
@Transactional
public class AliPayServiceImpl implements AliPayService{

    @Resource
    private OrderService orderService;
    @Resource
    private AlipayUtil alipayUtil;
    @Override
    public String aliPay(String orderNo) throws AlipayApiException{
        Order order = orderService.getNoBoughtOrderByOrderNo(orderNo);
        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setSubject("开心影院影票购买");
        alipayBean.setTotalAmount(order.getAmount().stripTrailingZeros().toPlainString());
        alipayBean.setOutTradeNo(orderNo);
        alipayBean.setBody("开心影院影票购买");
        return alipayUtil.alipay(alipayBean);
    }

    @Override
    public String rsaCheck(Map<String, String> params) {
        return alipayUtil.rsaCheck(params);
    }


//    @Override
//    public String alipayRefound(AlipayBean alipayBean) throws AlipayApiException {
//        return null;
//    }
}
