package com.kcc.buyer.mapper;

import com.kcc.buyer.domain.Account;

import java.util.Map;

public interface AccountMapper {

    int insertOrUpdateAccount(Map<String,Object> recordMap);

    Account selectByPrimaryKey(Integer id);
}