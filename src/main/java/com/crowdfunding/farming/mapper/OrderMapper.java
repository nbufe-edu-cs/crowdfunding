package com.crowdfunding.farming.mapper;

import com.crowdfunding.farming.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface OrderMapper extends tk.mybatis.mapper.common.Mapper<Order> {

    List<Order> queryOrderList(
            @Param("userId") Integer userId,
            @Param("status") Integer status);
}
