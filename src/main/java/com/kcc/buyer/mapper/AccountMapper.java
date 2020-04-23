package com.kcc.buyer.mapper;

import com.kcc.buyer.domain.Account;

public interface AccountMapper {

    int insertSelective(Account record);

    int updateByPrimaryKeySelective(Account record);

    Account selectBySupplierId(Integer supplierId);
}