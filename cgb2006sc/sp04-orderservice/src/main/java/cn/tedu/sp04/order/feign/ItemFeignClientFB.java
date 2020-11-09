package cn.tedu.sp04.order.feign;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.web.util.JsonResult;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ItemFeignClientFB implements ItemFeignClient {
    @Override
    public JsonResult<List<Item>> getItems(String orderId) {
        //模拟缓存数据
        if(Math.random()<0.5){
            //50%概率返回缓存数据
            List<Item> items = Arrays.asList(
                    new Item(1, "缓存商品1", 1),
                    new Item(2, "缓存商品2", 1),
                    new Item(3, "缓存商品3", 1),
                    new Item(4, "缓存商品4", 1),
                    new Item(5, "缓存商品5", 1));
            return JsonResult.ok().data(items);
        }
        return JsonResult.err().msg("获取商品列表失败!");
    }

    @Override
    public JsonResult<?> decreaseNumber(List<Item> items) {
        return JsonResult.err().msg("减少商品库存失败!");
    }
}
