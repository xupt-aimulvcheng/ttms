package com.xupt.ttms.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: Map《=》javaBean 转换工具类
 * @author: zyb
 * @date: 2021/6/16 18:24
 */
public class BeanMapUtils {

    /**
     * 把JavaBean转化为map
     * @param bean JavaBean
     * @return Map
     * @throws Exception 异常
     */
    public static Map<String, Object> beanToMap(Object bean) {
        Map<String, Object> map = null;
        try {
            map = new HashMap<>();
            //获取JavaBean的描述器
            BeanInfo b = Introspector.getBeanInfo(bean.getClass(), Object.class);
            //获取属性描述器
            PropertyDescriptor[] pds = b.getPropertyDescriptors();
            //对属性迭代
            for (PropertyDescriptor pd : pds) {
                //属性名称
                String propertyName = pd.getName();
                //属性值,用getter方法获取
                Method m = pd.getReadMethod();
                Object properValue = m.invoke(bean);//用对象执行getter方法获得属性值
                if (properValue != null) {
                    map.put(propertyName, properValue.toString());
                }
                else {//把属性名-属性值 存到Map中
                    map.put(propertyName, null);
                }
            }
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    /**
     * 把Map转化为JavaBean
     * @param map Map
     * @param clz 实体类
     * @param <T> 泛型
     * @return JavaBean
     * @throws Exception 异常
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clz) throws Exception {
        //创建一个需要转换为的类型的对象
        T obj = clz.newInstance();
        //从Map中获取和属性名称一样的值，把值设置给对象(setter方法)

        //得到属性的描述器
        BeanInfo b = Introspector.getBeanInfo(clz, Object.class);
        PropertyDescriptor[] pds = b.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            //得到属性的setter方法
            Method setter = pd.getWriteMethod();
            //得到key名字和属性名字相同的value设置给属性
            setter.invoke(obj, map.get(pd.getName()));
        }
        return obj;
    }
}
