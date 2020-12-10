package com.xixi.middle.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.dao.model
 * @Description: praise
 * @date Date : 2020年12月09日 5:24 下午
 */
public class BlogPraise implements Serializable {

    private Long id;

    private Long blogId;

    private Long praiseId;

    private Integer status;

    private Date praiseTime;

    private Date createTime;

    private Date updateTime;

    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getPraiseId() {
        return praiseId;
    }

    public void setPraiseId(Long praiseId) {
        this.praiseId = praiseId;
    }

    public Date getPraiseTime() {
        return praiseTime;
    }

    public void setPraiseTime(Date praiseTime) {
        this.praiseTime = praiseTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
