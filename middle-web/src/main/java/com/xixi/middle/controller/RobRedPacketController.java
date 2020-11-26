package com.xixi.middle.controller;

import com.xixi.middle.bo.RobPacketBO;
import com.xixi.middle.req.RobPacketReq;
import com.xixi.middle.service.RobRedPacketService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.controller
 * @Description: robber
 * @date Date : 2020年11月25日 1:50 下午
 */
@Validated
@RestController
@RequestMapping("/rob")
public class RobRedPacketController {
    @Autowired
    RobRedPacketService robRedPacketService;

    @PostMapping("/red")
    public BigDecimal rob(@RequestBody RobPacketReq robPacketReq) {
        RobPacketBO robPacketBO = new RobPacketBO();
        BeanUtils.copyProperties(robPacketReq, robPacketBO);
        return robRedPacketService.rob(robPacketBO);
    }
}
