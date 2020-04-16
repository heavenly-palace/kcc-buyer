package com.kcc.buyer.mapper;

import com.kcc.buyer.domain.Company;

import java.util.List;

public interface CompanyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);

    String selectCurrentLastNo(Integer type, String date);

    List<Company> selectSelective(Company company);
}