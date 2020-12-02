package com.xixi.middle.controller;

import com.xixi.middle.service.RabbitMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.controller
 * @Description: mq
 * @date Date : 2020年12月02日 4:36 下午
 */
@Validated
@RestController
@RequestMapping("/mq")
public class RabbitMqController {
    @Autowired
    RabbitMqService rabbitMqService;

    @PostMapping("/pub")
    public Boolean pubMqMsg(@RequestBody String hello) {
        return rabbitMqService.pubMqMsg(hello);
    }

}
