package com.xupt.ttms.pojo;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
public class AlipayBean {
    /**
     * 功能描述: 创建交易传入的商户订单号 ，必填  示例：20150320010101001
     * 商户订单号。
     * 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。 trade_no,out_trade_no如果同时存在优先取trade_no
     */
    private String outTradeNo;
    /**
     * 功能描述: 订单名称，必填
     */
    private String subject;
    /**
     * 功能描述: 付款金额，必填
     */
    private String totalAmount;
    /**
     * 功能描述: 商品描述，必填
     */
    private String body;

    /**
     * 功能描述: 支付宝交易号 示例：2014112611001004680073956707
     * 支付宝交易号。
     * 和商户订单号不能同时为空
     */
    private String tradeNo;

    /**
     * 功能描述: 本笔退款对应的退款请求号 示例：20150320010101001
     * 退款请求号。
     * 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的商户订单号。
     */
    private String outRequestNo;
    /**
     * 功能描述: 退款金额。
     * 需要退款的金额，该金额不能大于订单金额，单位为元，支持两位小数。
     * 注：如果正向交易使用了营销，该退款金额包含营销金额，支付宝会按业务规则分配营销和买家自有资金分别退多少，默认优先退买家的自有资金。
     * 如交易总金额100元，用户支付时使用了80元自有资金和20元无资金流的营销券，商家实际收款80元。如果首次请求退款60元，则60元全部从商家收款资金扣除退回给用户自有资产；
     * 如果再请求退款40元，则从商家收款资金扣除20元退回用户资产以及把20元的营销券退回给用户（券是否可再使用取决于券的规则配置。
     */
    private String refundAmount;

    public AlipayBean() {
    }

    /**
     * 功能描述: 超时时间参数
     */
    private String timeoutExpress = "30m";
    private String productCode = "FAST_INSTANT_TRADE_PAY";
}
