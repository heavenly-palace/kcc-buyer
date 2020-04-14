package com.kcc.buyer.mapper;

import com.kcc.buyer.domain.Company;

import java.util.Date;
import java.util.List;

public interface CompanyMapper {
    int deleteByPrimaryKey(Integer id);

    int insertOrUpdateCompany(Company record);

    Company selectByPrimaryKey(Integer id);

    List<Company> selectByCompanyType(Integer type);

    String selectCompanyByDateLast(Integer companyType, String date);
}