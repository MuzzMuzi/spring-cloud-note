package com.pd;
/*
从Rabbitmq接受订单
收到订单后,调用OrderServiceImpl.savaOrder() 把订单存储到数据库
spring封装rabbitmq,接受消息不用写代码,只需要用注解进行配置
 */

import com.pd.pojo.PdOrder;
import com.pd.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "orderQueue")
public class OrderConsumer {
    @Autowired
    private OrderService orderService;

    @RabbitHandler//指定处理消息的方法,一个类中只能有一个 @RabbitHandler
    public void receiveOrder(PdOrder pdOrder) throws Exception {
        orderService.saveOrder(pdOrder);
        System.out.println("订单已存储");
    }
}
