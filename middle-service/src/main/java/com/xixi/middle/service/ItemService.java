package com.xixi.middle.service;

import com.alibaba.fastjson.JSON;
import com.xixi.middle.dao.mapper.ItemMapper;
import com.xixi.middle.dao.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.service
 * @Description: service
 * @date Date : 2020年11月12日 4:26 下午
 */
@Service
public class ItemService {
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Autowired
    ItemMapper itemMapper;

    public void setItem(Item item) {
        itemMapper.insertItem(item);
        return;
    }

    public Item get(String name) {
        String item = redisTemplate.opsForValue().get("name");
        Item info = null;
        if (StringUtils.isEmpty(item)) {
            info = itemMapper.query(name);
            redisTemplate.opsForValue().set(name, JSON.toJSONString(info));
        } else {
            info = JSON.parseObject(item, Item.class);
        }
        return info;
    }
}
