package com.xixi.middle.service;

import com.xixi.middle.bo.RobPacketBO;
import com.xixi.util.RedPacketUtil;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.service
 * @Description: rob service
 * @date Date : 2020年11月23日 6:02 下午
 */
@Service
public class RobRedPacketService {

    private static String redPrefix = "redis:red:packet:";

    private static Long zero = 0L;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    RedService redService;


    public BigDecimal rob(RobPacketBO robPacketBO) {
        if (robPacketBO.getUserId() <= zero || StringUtils.isEmpty(robPacketBO.getRedId())) {
            throw new RuntimeException("params invalid");
        }
        String curUser = new StringBuffer(robPacketBO.getRedId()).append(":").append(robPacketBO.getUserId()).append(":").append("rob").toString();
        Object robed = redisTemplate.opsForValue().get(curUser);
        if (Objects.nonNull(robed)) {
            return new BigDecimal(robed.toString());
        }

        //click
        boolean clicked = click(robPacketBO.getRedId(), robPacketBO.getUserId());
        if (clicked) {
            //split
            return split(robPacketBO);
        }
        throw new RuntimeException("money rob ending");
    }

    private BigDecimal split(RobPacketBO robPacketBO) {
        Integer curMoney = (Integer) redisTemplate.opsForList().rightPop(robPacketBO.getRedId());
        if (Objects.isNull(curMoney) || curMoney <= 0) {
            throw new RuntimeException("money rob ending");
        }
        // update redis
        String totalKey = new StringBuffer(robPacketBO.getRedId()).append(":").append("total").toString();
        Integer total = (Integer) redisTemplate.opsForValue().get(totalKey);
        if (Objects.isNull(total) || total <= 0) {
            throw new RuntimeException("money rob ending");
        }

        redisTemplate.opsForValue().set(totalKey, total - curMoney >= 0 ? 0 : total - curMoney);


        // async db
        redService.asyncUserRobRecordWriteToDb(robPacketBO.getUserId(), robPacketBO.getRedId(), curMoney);

        BigDecimal userMoney = new BigDecimal(curMoney).divide(new BigDecimal(100));
        return userMoney;
    }

    /**
     * @param redId
     * @return
     */
    private boolean click(String redId, Long userId) {
        // 分布式锁
        String lock = new StringBuffer(redId).append(":").append(userId).append(":").append("lock").toString();
        if (!redisTemplate.opsForValue().setIfAbsent(lock, redId)) {
            return false;
        }

        redisTemplate.expire(lock, 10, TimeUnit.MINUTES);

        // FIXME: 2020/11/30  加锁和设置超时时间是两步操作，不是原子性，也是有问题的, 就算是一步操作，线上的Redis一般都是集群，集群环境也会有问题，推荐看看Redis之父关于分布式锁的定义

        String totalMoney = new StringBuffer(redId).append(":").append("total").toString();
        Integer tMoney = (Integer) redisTemplate.opsForValue().get(totalMoney);
        if (Objects.isNull(tMoney) || tMoney <= 0) {
            return false;
        }

        return true;

    }
}
