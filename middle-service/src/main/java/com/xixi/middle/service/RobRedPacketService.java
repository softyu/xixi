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
        boolean clicked = click(robPacketBO.getRedId());
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
        if (Objects.isNull(total)||total<=0){
            throw new RuntimeException("money rob ending");
        }

        redisTemplate.opsForValue().set(totalKey, total-curMoney>=0?0:total-curMoney);


        // async db
        redService.asyncUserRobRecordWriteToDb(robPacketBO.getUserId(),robPacketBO.getRedId(),curMoney);

        BigDecimal userMoney = new BigDecimal(curMoney).divide(new BigDecimal(100));
        return userMoney;
    }

    /**
     * @param redId
     * @return
     */
    private boolean click(String redId) {
        String totalMoney = new StringBuffer(redId).append(":").append("total").toString();
        Integer tMoney = (Integer) redisTemplate.opsForValue().get(totalMoney);
        if (Objects.isNull(tMoney) || tMoney <= 0) {
            return false;
        }

        return true;

    }
}
