package com.kcc.buyer.mapper;

import com.kcc.buyer.domain.Company;

import java.util.List;

public interface CompanyMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Company record);

    Company selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Company record);

    String selectCurrentLastNo(Integer type, String date);

    List<Company> selectBySupplierAll();

    List<Company> selectByBuyerAll();
}