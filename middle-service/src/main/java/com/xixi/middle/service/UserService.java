package com.xixi.middle.service;

import com.alibaba.fastjson.JSON;
import com.xixi.middle.bo.UserBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.service
 * @Description: TODO
 * @date Date : 2020年11月12日 4:26 下午
 */
@Service
public class UserService {
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public void setUser(UserBO userBO) {

        redisTemplate.opsForValue().set("user", JSON.toJSONString(userBO));
        return;
    }

    public UserBO get() {
        String user = redisTemplate.opsForValue().get("user");
        UserBO userBO = JSON.parseObject(user, UserBO.class);
        return userBO;
    }
}
