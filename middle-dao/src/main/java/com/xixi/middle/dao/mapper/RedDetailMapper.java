package com.xixi.middle.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xixi.middle.dao.model.RedDetail;

import java.util.List;

@Mapper
public interface RedDetailMapper {

    RedDetail selectByPrimaryKey(Long id);

    int insertSelective(RedDetail redDetail);

    int updateRecordId(@Param("recordId") Long recordId);


    int insertBatch(@Param("redDetailCollection") List<RedDetail> redDetailCollection);



}