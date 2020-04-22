package com.kcc.buyer.mapper;

import com.kcc.buyer.domain.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    List<Order> selectOrderAll();

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}