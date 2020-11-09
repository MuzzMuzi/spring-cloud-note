package cn.tedu.sp04.order.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.sp01.service.OrderService;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderId}")
    public JsonResult<Order> getOrder(@PathVariable String orderId) {
        log.info("获取订单: order= " + orderId);
        Order order = orderService.getOrder(orderId);
        return JsonResult.ok().data(order);
    }

    //添加订单,demo演示,不接受任何参数,数据在方法中写死
    @GetMapping("/")
    public JsonResult<?> addOrder() {
        Order order = new Order(
                "s65s6d5s65d6s",
                new User(8, "user8", "pwd8"),
                Arrays.asList(
                        new Item(1, "item1", 2),
                        new Item(2, "item2", 5),
                        new Item(3, "item3", 2),
                        new Item(4, "item4", 6),
                        new Item(5, "item5", 2)
                )
        );
        orderService.addOrder(order);
        return JsonResult.ok().msg("保存订单成功!");
    }
}
