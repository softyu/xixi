package com.xixi.middle.dao.mapper;

import com.xixi.middle.dao.model.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author author
 */
@Mapper
public interface ItemMapper {
    /**
     *
     * @param object
     * @return
     */
    int insertItem(Item object);

    /**
     *
     * @param object
     * @return
     */
    int updateItem(Item object);
    

    /**
     * query
     * @param name
     * @return
     */
    Item query(@Param(value ="name" ) String name);

}