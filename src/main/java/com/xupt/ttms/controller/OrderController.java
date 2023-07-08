package com.xupt.ttms.controller;

import com.github.pagehelper.PageInfo;
import com.xupt.ttms.pojo.Order;
import com.xupt.ttms.pojo.Result;
import com.xupt.ttms.pojo.Seat;
import com.xupt.ttms.service.OrderService;
import com.xupt.ttms.util.QRCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("order")
public class OrderController {
    @Resource
    private OrderService orderService;
    @PostMapping("/insertOrder/{pId}")
    public Result insertOrder(@RequestParam("amount") BigDecimal amount,@RequestBody List<Seat> list , @PathVariable("pId") String pId){
        Order order = orderService.insertOrder(amount,list,pId);
        return Result.success(order);
    }
    @PostMapping("/payOrder")
    public Result payOrder(@RequestBody Order order1){
        return Result.success(orderService.payOrder(order1)>0?"支付成功":"支付失败");
    }
    @PostMapping("/getOrders")
    public Result getOrders(@RequestParam("page") int pageNum,@RequestParam("pageSize") int pageSize){
        PageInfo<Order> orders =  orderService.getOrders(pageNum,pageSize);
        Result result = Result.success(orders);
        if (orders.isIsLastPage()){
            result.setMessage("暂无更多订单");
        }
        return result;
    }
    @PostMapping("/getOrderById/{id}")
    public Result getOrderById(@PathVariable("id") Long id){
        return Result.success(orderService.selectById(id));
    }
    @GetMapping("/order/refund/{id}")
    public Result refund(@PathVariable("id") Long id){
        int result = orderService.refund(id);
        return Result.success(result>0?"退款成功":"退款失败");
    }
    @PostMapping("/deleteOrder/{id}")
    public Result deleteOrder(@PathVariable("id") Long id){
        int result = orderService.deleteOrder(id);
        return Result.success(result>0?"删除成功":"删除失败");
    }
    @PostMapping("/generateQRCode")
    public ResponseEntity<ByteArrayResource> generateQRCode(@RequestBody String ticketInfo) {
        // 生成二维码的逻辑代码
        // ...
        return QRCodeGenerator.generateQRCode(ticketInfo);
    }
    @PostMapping("/getTodayBoxOffice")
    public Result getOrderAmount(){
        return Result.success(orderService.getOrderAmount());
    }
}
