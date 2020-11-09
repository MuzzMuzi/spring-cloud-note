package cn.tedu.sp02.item.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.service.ItemService;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@Slf4j
public class ItemController {

    @Value("${server.port}")
    private int port;

    @Autowired
    private ItemService itemService;

    @GetMapping("/{orderId}")
//    @RequestMapping(path = "/{orderId}",method = RequestMethod.GET)
    public JsonResult<List<Item>> getItem(@PathVariable String orderId) throws InterruptedException {
        log.info("获取商品的订单列表,orderId: "+orderId);

        //随机的延迟
        if(Math.random()<0.3){
            //随机时长的延迟
            long time = new Random().nextInt(3000);
            log.info("延迟: "+time);
            Thread.sleep(time);
        }
        List<Item> items = itemService.getItems(orderId);
        return JsonResult.ok().msg("port=" + port).data(items);
    }

    @PostMapping("/decreaseNumber")
    public JsonResult<?> decreaseNumber(@RequestBody List<Item> items) {
        itemService.decreaseNumber(items);
        return JsonResult.ok().msg("减少商品库存成功!");
    }
}
