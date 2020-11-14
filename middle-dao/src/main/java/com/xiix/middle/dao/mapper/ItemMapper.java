package com.xiix.middle.dao.mapper;

import com.xiix.middle.dao.model.Item;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author author
 */
@Mapper
public interface ItemMapper {
    int insertItem(Item object);


    int updateItem(Item object);


}