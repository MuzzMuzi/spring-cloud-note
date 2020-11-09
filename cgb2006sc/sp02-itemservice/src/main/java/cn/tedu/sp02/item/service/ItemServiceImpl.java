package cn.tedu.sp02.item.service;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    @Override
    public List<Item> getItems(String orderId) {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(1, "item01", 5));
        items.add(new Item(2, "item02", 8));
        items.add(new Item(3, "item03", 6));
        items.add(new Item(4, "item04", 7));
        items.add(new Item(5, "item05", 9));
        items.add(new Item(6, "item06", 15));
        return items;
    }

    @Override
    public void decreaseNumber(List<Item> items) {
        for (Item item : items) {
            log.info("减少商品库存:  " + item);
        }
    }
}
