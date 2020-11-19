package com.xixi.middle.dao.mapper;
import org.apache.ibatis.annotations.Param;

import com.xixi.middle.dao.model.RedRecord;

public interface RedRecordMapper {

    RedRecord selectByPrimaryKey(Long id);

    int insertSelective(RedRecord redRecord);

    int updateId(@Param("id") Long id);
}