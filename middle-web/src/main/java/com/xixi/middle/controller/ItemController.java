package com.xixi.middle.controller;

import com.alibaba.fastjson.JSON;
import com.xixi.middle.dao.model.Item;
import com.xixi.middle.service.ItemService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.controller
 * @Description: TODO
 * @date Date : 2020年11月12日 4:23 下午
 */
@RestController
@RequestMapping("/user")
public class ItemController {

    @Autowired
    ItemService itemService;

    @PostMapping("/add")
    public Boolean setItem(@RequestBody Item item) {
        itemService.setItem(item);
        return Boolean.TRUE;
    }


    @GetMapping("/get")
    public String getItem(@Param(value ="name" ) String name) {
        Item item = itemService.get(name);
        return JSON.toJSONString(item);
    }
}
