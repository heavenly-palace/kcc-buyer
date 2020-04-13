package com.kcc.buyer.mapper;

import com.kcc.buyer.domain.Company;

import java.util.List;

public interface CompanyMapper {
    int deleteByPrimaryKey(Integer id);

    int insertOrUpdateCompany(Company record);

    Company selectByPrimaryKey(Integer id);

    List<Company> selectByCompanyType(Integer type);
}