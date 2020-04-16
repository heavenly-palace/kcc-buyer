package com.kcc.buyer.mapper;

import com.kcc.buyer.domain.Product;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(List<Product> productList);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    String selectCurrentLastNo(String date);

    List<Product> selectBySelective(Product product);
}