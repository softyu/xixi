package com.xixi.middle.controller;

import com.alibaba.fastjson.JSON;
import com.xixi.middle.bo.UserBO;
import com.xixi.middle.req.User;
import com.xixi.middle.service.UserService;
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
 * @Description: TODO
 * @date Date : 2020年11月12日 4:23 下午
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/add")
    public String setUser(@RequestBody User user) {
        UserBO userBO = new UserBO();
        userBO.setId(user.getId());
        userBO.setName(user.getName());
        userService.setUser(userBO);

        UserBO ruser = userService.get();
        return JSON.toJSONString(ruser);
    }


    @PostMapping("/get")
    public String getUser() {
        UserBO ruser = userService.get();
        return JSON.toJSONString(ruser);
    }
}
