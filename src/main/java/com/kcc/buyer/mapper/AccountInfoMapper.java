package com.kcc.buyer.mapper;

import com.kcc.buyer.domain.AccountInfo;

public interface AccountInfoMapper {

    int insertSelective(AccountInfo record);

    AccountInfo selectByCompanyInfoId(Integer companyInfoId);
}