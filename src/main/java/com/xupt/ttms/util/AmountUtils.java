package com.xupt.ttms.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmountUtils {
    /**
     * 计算电影的总票房。
     *
     * @param amounts 电影每笔订单的金额列表
     * @param m       保留小数点后的位数
     * @return 包含总票房数据和单位的Map对象
     */
    public static Map<String, Object> calculateTotalAmount(List<BigDecimal> amounts, int m) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (BigDecimal amount : amounts) {
            totalAmount = totalAmount.add(amount);
        }
        if (totalAmount.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        Map<String, Object> resultMap = new HashMap<>();
        String totalAmountString;
        String unit;
        if (totalAmount.compareTo(BigDecimal.valueOf(100000000)) >= 0) {
            totalAmountString = totalAmount.divide(BigDecimal.valueOf(100000000), m, RoundingMode.HALF_UP).toString();
            unit = "亿";
        } else if (totalAmount.compareTo(BigDecimal.valueOf(10000)) >= 0) {
            totalAmountString = totalAmount.divide(BigDecimal.valueOf(10000), m, RoundingMode.HALF_UP).toString();
            unit = "万";
        } else {
            totalAmountString = totalAmount.setScale(m, RoundingMode.HALF_UP).toString();
            unit = "";
        }

        resultMap.put("totalAmount", totalAmountString);
        resultMap.put("unit", unit);
        return resultMap;
    }

}
