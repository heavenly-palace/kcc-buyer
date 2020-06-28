package com.kcc.buyer.mapper;

import com.kcc.buyer.domain.PayPlan;

import java.util.List;

public interface PayPlanMapper {

    int insertPayPlanBatch(List<PayPlan> payPlans);

    List<PayPlan> selectPayPlanByOrderId(Integer orderId);
}
