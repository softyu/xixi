package com.xixi.middle.controller;

import com.xixi.middle.service.RedissonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.controller
 * @Description: redisson
 * @date Date : 2020年12月08日 11:27 上午
 */
@RestController
@RequestMapping("/redisson")
public class RedissonController {
    @Autowired
    RedissonService redissonService;

    @GetMapping("/bloom/filter")
    public Object filter(Long userId) {
        return redissonService.filter(userId);
    }
}
