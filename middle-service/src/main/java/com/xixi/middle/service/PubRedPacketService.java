package com.xixi.middle.service;

import com.xixi.middle.bo.RedPacketBO;
import com.xixi.middle.bo.RobPacketBO;
import com.xixi.util.RedPacketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.service
 * @Description: red service
 * @date Date : 2020年11月23日 6:02 下午
 */
@Service
public class PubRedPacketService {

    private static  String redPrefix = "redis:red:packet:";

    private static  Long zero = 0L;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    RedService redService;

    public String publishRed(RedPacketBO redPacketBO) {
        if (redPacketBO.getAmountMoney()<=zero||redPacketBO.getTotalPeople()<=zero){
            throw  new RuntimeException("params invalid");
        }

        // binary
        List<Integer> sumRedPackets = RedPacketUtil.divideRedPacket(redPacketBO.getAmountMoney(), redPacketBO.getTotalPeople());

        //red-unique-key
        String redUniqueKey = String.valueOf(System.nanoTime());

        //redis-key

        String redId = new StringBuffer(redPrefix).append(redPacketBO.getUserId()).append(":").append(redUniqueKey).toString();
        redisTemplate.opsForList().leftPushAll(redId, sumRedPackets);

        String  redTotal = new StringBuffer(redId).append(":").append("total").toString();
        redisTemplate.opsForValue().set(redTotal, redPacketBO.getAmountMoney());

        // async 异步失败了，缓存的数据一致存在，这不是很坑吗？？？？？

        // sync save db
        redService.asyncWriteToDb(redId,redPacketBO,sumRedPackets);

        return redId;


    }

}
