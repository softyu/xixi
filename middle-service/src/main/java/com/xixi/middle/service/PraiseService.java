package com.xixi.middle.service;

import com.xixi.middle.bo.PraiseBO;
import com.xixi.middle.dao.mapper.BlogPraiseMapper;
import com.xixi.middle.dao.model.BlogPraise;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.service
 * @Description: praise-service
 * @date Date : 2020年12月10日 3:59 下午
 */
@Service
public class PraiseService {
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    BlogPraiseMapper blogPraiseMapper;

    /**
     * add
     *
     * @param praiseBO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String add(PraiseBO praiseBO) {

        String redisLockKey = new StringBuffer().append("redisLockKey").append(":").append(praiseBO.getBlogId()).append(praiseBO.getUserId()).toString();
        RLock lock = redissonClient.getLock(redisLockKey);

        try {
            boolean locked = lock.tryLock(100, 10, TimeUnit.SECONDS);
            if (locked) {
                BlogPraise blogPraise = new BlogPraise();
                blogPraise.setBlogId(praiseBO.getBlogId());
                blogPraise.setPraiseId(praiseBO.getUserId());
                List<BlogPraise> blogPraises = blogPraiseMapper.selectByPrimaryKey(blogPraise);
                if (CollectionUtils.isEmpty(blogPraises)) {
                    // insert
                    BlogPraise newBlogPraise = new BlogPraise();
                    newBlogPraise.setBlogId(praiseBO.getBlogId());
                    newBlogPraise.setPraiseId(praiseBO.getUserId());
                    newBlogPraise.setStatus(1);
                    blogPraiseMapper.insertSelective(newBlogPraise);
                    // update cache
                }

                return null;
            }
            throw new RuntimeException("已经点赞了");
        } catch (InterruptedException e) {
            throw new RuntimeException();
        } finally {
            lock.unlock();
        }
    }

    /**
     * cancel
     *
     * @param praiseBO
     * @return
     */
    public String cancel(PraiseBO praiseBO) {

        return null;
    }
}
