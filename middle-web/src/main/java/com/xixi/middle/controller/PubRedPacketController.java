package com.xixi.middle.controller;

import com.xixi.middle.bo.RedPacketBO;
import com.xixi.middle.req.RedPacketReq;
import com.xixi.middle.service.PubRedPacketService;
import org.springframework.beans.BeanUtils;
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
 * @Description: 发布
 * @date Date : 2020年11月23日 5:57 下午
 */
@Validated
@RestController
@RequestMapping("/pub")
public class PubRedPacketController {

    @Autowired
    PubRedPacketService pubRedPacketService;

    @PostMapping("/red")
    public String publishRed(@RequestBody RedPacketReq redPacketReq) {
        RedPacketBO redPacketBO = new RedPacketBO();
        BeanUtils.copyProperties(redPacketReq, redPacketBO);
        return pubRedPacketService.publishRed(redPacketBO);
    }
}
