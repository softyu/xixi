package com.xixi.middle.dao.mapper;
import org.apache.ibatis.annotations.Param;

import com.xixi.middle.dao.model.RedRobRecord;

public interface RedRobRecordMapper {

    RedRobRecord selectByPrimaryKey(Long id);

    int insertSelective(RedRobRecord redRobRecord);


    int updateId(@Param("id") Long id);
}