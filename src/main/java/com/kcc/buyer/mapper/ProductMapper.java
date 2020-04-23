package com.kcc.buyer.mapper;

import com.kcc.buyer.domain.Product;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insertProductBatch(List<Product> productList);

    int insertSelective(Product record);

    int updateByPrimaryKeySelective(Product record);

    String selectCurrentLastNo(String date);

    List<Product> selectBySupplier(Integer supplierId);

    int updateProductBatch(List<Product> list);
}