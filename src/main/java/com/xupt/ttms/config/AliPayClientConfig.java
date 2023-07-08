package com.xupt.ttms.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

import static com.alipay.api.AlipayConstants.*;

@Configuration
public class AliPayClientConfig {
    @Resource
    private Environment config;

    @Bean
    public AlipayClient aliPayClient() throws AlipayApiException {
        AlipayConfig alipayConfig = new AlipayConfig();
        //设置网关地址
        alipayConfig.setServerUrl(config.getProperty("alipay.gateway-url"));
        //设置应用ID
        alipayConfig.setAppId(config.getProperty("alipay.app-id"));
        //设置应用私钥
        alipayConfig.setPrivateKey(config.getProperty("alipay.merchant-private-key"));
        alipayConfig.setAlipayPublicKey(config.getProperty("alipay.public-key"));
        //设置请求格式，固定值json
        alipayConfig.setFormat(FORMAT_JSON);
        //设置字符集
        alipayConfig.setCharset(CHARSET_UTF8);
        //设置签名类型
        alipayConfig.setSignType(SIGN_TYPE_RSA2);
        //构造client
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        return alipayClient;
    }
}
