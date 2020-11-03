package cn.tedu.sp06.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.web.util.JsonResult;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Slf4j
public class RibbonController {

    @Autowired
    private RestTemplate restTemplate;

    //调用远程的商品服务
    //如果调用方法失败,跳到另一个方法去执行
    @HystrixCommand(fallbackMethod = "getItemFB")
    @GetMapping("/item-service/{orderId}")
    public JsonResult<List<Item>> getItems(@PathVariable String orderId){
        //调用远程服务
        //  {1} 是RestTemplate定义的一种占位符格式,后面的第三个参数 orderId会对占位符进行填充
        return restTemplate.getForObject(
                "http://item-service/{1}",
                JsonResult.class,
                orderId);
    }

    @PostMapping("/item-service/decreaseNumber")
    @HystrixCommand(fallbackMethod = "decreaseNumberFB")
    public JsonResult<?> decreaseNumber(@RequestBody List<Item> items){
        //调用商品服务,减少商品库存
        return restTemplate.postForObject(
                "http://item-service/decreaseNumber",
                items,  //post请求协议体数据
                JsonResult.class );
    }

    @GetMapping("/user-service/{userId}")
    @HystrixCommand(fallbackMethod = "getUserFB")
    public JsonResult<User> getUser(@PathVariable Integer userId){
        return restTemplate.getForObject(
                "http://user-service/{1}",
                JsonResult.class,
                userId);
    }

    @GetMapping("/user-service/{userId}/score")
    @HystrixCommand(fallbackMethod = "addScoreFB")
    public JsonResult<?> addScore(@PathVariable Integer userId,Integer score){
        return restTemplate.getForObject("http://user-service/{1}/score?score={2}",
                JsonResult.class,
                userId,
                score);
    }

    @GetMapping("/order-service/{orderId}")
    @HystrixCommand(fallbackMethod = "getOrderFB")
    public JsonResult<Order> getOrder(@PathVariable String orderId){
        return restTemplate.getForObject(
                "http://order-service/{1}",
                JsonResult.class,
                orderId);
    }

    @GetMapping("/order-service/")
    @HystrixCommand(fallbackMethod = "addOrderFB")
    public JsonResult<?> addOrder(){
        return restTemplate.getForObject(
                "http://order-service/",
                JsonResult.class);
    }

    public JsonResult<List<Item>> getItemFB(String orderId){
        return JsonResult.err().msg("获取订单的商品列表失败!");
    }

    public JsonResult<?> decreaseNumberFB(List<Item> items){
        return JsonResult.err().msg("减少订单的商品库存失败!");
    }

    public JsonResult<User> getUserFB(Integer userId){
        return JsonResult.err().msg("获取用户信息失败!");
    }

    public JsonResult<?> addScoreFB(Integer userId,Integer score){
        return JsonResult.err().msg("增加用户积分失败!");
    }

    public JsonResult<Order> getOrderFB(String orderId){
        return JsonResult.err().msg("获取订单失败!");
    }

    public JsonResult<?> addOrderFB(){
        return JsonResult.err().msg("保存订单失败!");
    }

}
