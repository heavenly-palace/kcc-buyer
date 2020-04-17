package com.kcc.buyer.mapper;

import com.kcc.buyer.domain.CompanyInfo;

import java.util.List;

public interface CompanyInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompanyInfo record);

    int insertSelective(CompanyInfo record);

    CompanyInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompanyInfo record);

    int updateByPrimaryKey(CompanyInfo record);

    List<CompanyInfo> selectSelective(CompanyInfo companyInfo);
}