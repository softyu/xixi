package com.xixi.middle.controller;

import com.xixi.middle.bo.PraiseBO;
import com.xixi.middle.bo.RedPacketBO;
import com.xixi.middle.req.PraiseReq;
import com.xixi.middle.req.RedPacketReq;
import com.xixi.middle.service.PraiseService;
import com.xixi.middle.service.PubRedPacketService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.controller
 * @Description: praise
 * @date Date : 2020年12月10日 3:56 下午
 */
@RestController
@RequestMapping("/praise")
public class PraiseController {

    @Autowired
    PraiseService praiseService;

    @PostMapping("/add")
    public String add(@RequestBody PraiseReq praiseReq) {
        PraiseBO praiseBO = new PraiseBO();
        BeanUtils.copyProperties(praiseReq, praiseBO);
        return praiseService.add(praiseBO);
    }


    @PostMapping("/cancel")
    public String cancel(@RequestBody PraiseReq praiseReq) {
        PraiseBO praiseBO = new PraiseBO();
        BeanUtils.copyProperties(praiseReq, praiseBO);
        return praiseService.cancel(praiseBO);
    }
}
