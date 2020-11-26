package com.xixi.middle.service;

import com.xixi.middle.bo.RedPacketBO;
import com.xixi.middle.dao.mapper.RedDetailMapper;
import com.xixi.middle.dao.mapper.RedRecordMapper;
import com.xixi.middle.dao.mapper.RedRobRecordMapper;
import com.xixi.middle.dao.model.RedDetail;
import com.xixi.middle.dao.model.RedRecord;
import com.xixi.middle.dao.model.RedRobRecord;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.service
 * @Description: red async db
 * @date Date : 2020年11月23日 10:15 下午
 */
@Slf4j
@Service
@EnableAsync
public class RedService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    RedRecordMapper redRecordMapper;

    @Autowired
    RedDetailMapper redDetailMapper;

    @Autowired
    RedRobRecordMapper redRobRecordMapper;


    /**
     * @param redId
     * @param redPacketBO
     * @param sumRedPackets
     */
    @Transactional(rollbackFor = Exception.class)
    @Async
    public void asyncWriteToDb(String redId, RedPacketBO redPacketBO, List<Integer> sumRedPackets) {
        try {
            // record
            RedRecord redRecord = new RedRecord();
            redRecord.setRedPacket(redId);
            redRecord.setAmount(new BigDecimal(redPacketBO.getTotalPeople()));
            redRecord.setTotal(redPacketBO.getTotalPeople());
            redRecord.setCreateTime(new Date());
            redRecord.setUserId(redPacketBO.getUserId());
            redRecordMapper.insertSelective(redRecord);

            //detail
            List<RedDetail> redDetails = Lists.newArrayList();
            for (Integer onePacket : sumRedPackets) {
                RedDetail redDetail = new RedDetail();
                redDetail.setAmount(new BigDecimal(onePacket));
                redDetail.setCretaeTime(new Date());
                redDetail.setRecordId(redRecord.getId());
                redDetails.add(redDetail);
            }
            redDetailMapper.insertBatch(redDetails);
        } catch (Throwable e) {
            log.error("operate db error", e);
            //delete packets list
            redisTemplate.delete(redId);
            String redTotal = new StringBuffer(redId).append(":").append("total").toString();
            // delete red-total
            redisTemplate.delete(redTotal);
        }

    }

    /**
     * @param userId
     * @param redId
     * @param curMoney
     */
    @Transactional(rollbackFor = Exception.class)
    @Async
    public void asyncUserRobRecordWriteToDb(Long userId, String redId, Integer curMoney) {
        try {
            // record
            RedRobRecord redRobRecord = new RedRobRecord();
            redRobRecord.setUserId(userId);
            redRobRecord.setRobTime(new Date());
            redRobRecord.setRedPacket(redId);
            redRobRecord.setAmount(new BigDecimal(curMoney));
            redRobRecordMapper.insertSelective(redRobRecord);


        } catch (Throwable e) {
            //retry db  and  record log  and warning....
            log.error("write to db is error userid:{},redId:{},curMoney:{}", userId, redId, curMoney, e);
        }

    }
}
