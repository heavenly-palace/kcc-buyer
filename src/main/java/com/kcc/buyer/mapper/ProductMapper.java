package com.kcc.buyer.mapper;

import com.kcc.buyer.domain.Product;

import java.util.List;
import java.util.Map;

public interface ProductMapper {

    int insertOrUpdateProducts(Map<String,Object> recordMap);

    List<Product> selectByCompanyId(Integer companyId, Integer status);

    int deleteProductById(Integer id);
}