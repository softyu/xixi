package com.xixi.middle.dao.mapper;

import com.xixi.middle.dao.model.SysLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.dao.mapper
 * @Description: mapper
 * @date Date : 2020年12月09日 1:54 下午
 */
@Mapper
public interface SysLogMapper {

    SysLog selectByPrimaryKey(Long id);

    int insertSelective(SysLog sysLog);

}
