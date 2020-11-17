package com.xiix.middle.dao.mapper;

import com.xiix.middle.dao.model.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author author
 */
@Mapper
public interface ItemMapper {
    int insertItem(Item object);


    int updateItem(Item object);

    /**
     * query
     * @param name
     * @return
     */
    Item query(@Param(value ="name" ) String name);
}