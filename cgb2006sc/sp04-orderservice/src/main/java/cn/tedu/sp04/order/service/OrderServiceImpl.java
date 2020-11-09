package cn.tedu.sp04.order.service;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.sp01.service.OrderService;
import cn.tedu.sp04.order.feign.ItemFeignClient;
import cn.tedu.sp04.order.feign.UserFeignClient;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemFeignClient itemFeignClient;
    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public Order getOrder(String orderId) {
        /*
        获取订单
        调用用户获取用户数据,调用商品获取商品列表
         */
        // TODO: 远程调用商品服务,获取商品
        JsonResult<List<Item>> items = itemFeignClient.getItems(orderId);
        // TODO: 远程调用用户,获取用户数据
        JsonResult<User> user = userFeignClient.getUser(8);

        Order order = new Order();
        order.setId(orderId);
//        order.setUser(new User(10,"halfho","fafha"));
//        order.setItems(Arrays.asList(new Item(1, "", 2),
//                new Item(2, "", 5),
//                new Item(3, "", 2),
//                new Item(4, "", 6),
//                new Item(5, "", 2)));
        order.setUser(user.getData());
        order.setItems(items.getData());
        return order;
    }

    @Override
    public void addOrder(Order order) {
        //调用远程服务,减少商品库存
        itemFeignClient.decreaseNumber(order.getItems());
        //抵用远程服务,增加用户积分
        userFeignClient.addScore(order.getUser().getId(), 1000);
        log.info("保存订单:"+order);
    }
}