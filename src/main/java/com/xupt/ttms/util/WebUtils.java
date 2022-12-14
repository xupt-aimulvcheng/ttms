package com.xupt.ttms.util;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

public class WebUtils {
    /**
     * 把Map中的值注入到对应的JavaBean属性中。
     * @param value
     * @param bean
     */
    public static <T> T copyParamToBean(Map value , T bean ){
        try {
            /**
             * 把所有请求的参数都注入到user对象中
             */
            BeanUtils.populate(bean, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
}
