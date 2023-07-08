package com.xupt.ttms.controller;

import com.alipay.api.AlipayApiException;
import com.xupt.ttms.service.AliPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("alipay")
public class AlipayController {

    @Resource
    private AliPayService alipayService;

    @PostMapping("/tarde/pay/{orderNo}")
    public String TradePagePay(@PathVariable String orderNo) throws AlipayApiException {
        return alipayService.aliPay(orderNo);
    }

    @PostMapping("alipay")
    public String tradeNotify(@RequestParam Map<String, String> map) {
        log.info("通知参数" + map);
        String result = alipayService.rsaCheck(map);
        log.info("验签的结果为"+result);
        return result;
    }

}
