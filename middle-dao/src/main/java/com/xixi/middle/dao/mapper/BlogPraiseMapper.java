package com.xixi.middle.dao.mapper;

import com.xixi.middle.dao.model.BlogPraise;
import com.xixi.middle.dao.model.SysLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.dao.mapper
 * @Description: mapper
 * @date Date : 2020年12月09日 1:54 下午
 */
@Mapper
public interface BlogPraiseMapper {

    List<BlogPraise> selectByPrimaryKey(BlogPraise praise);


   Long selectByBlogCount(Long blogId);


    List<BlogPraise> selectPraiseRank();

    int insertSelective(BlogPraise blogPraise);


    int updateSelective(BlogPraise blogPraise);

}
