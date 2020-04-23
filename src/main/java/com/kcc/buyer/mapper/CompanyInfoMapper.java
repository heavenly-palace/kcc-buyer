package com.kcc.buyer.mapper;

import com.kcc.buyer.domain.CompanyInfo;

import java.util.List;

public interface CompanyInfoMapper {

    int insertSelective(CompanyInfo record);

    List<CompanyInfo> selectSelective(CompanyInfo companyInfo);

    List<CompanyInfo> selectByOrderId(Integer orderId);
}