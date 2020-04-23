package com.kcc.buyer.mapper;

import com.kcc.buyer.domain.OrderDetail;

import java.util.List;

public interface OrderDetailMapper {

    int insertBatch(List<OrderDetail> orderDetailList);

    List<OrderDetail> selectSelective(OrderDetail orderDetail);

    List<OrderDetail> selectByOrderId(Integer orderId);

}