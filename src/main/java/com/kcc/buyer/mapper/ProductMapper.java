package com.kcc.buyer.mapper;

import com.kcc.buyer.domain.Product;
import com.kcc.buyer.domain.ProductType;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insertProductBatch(List<Product> productList);

    int insertSelective(Product record);

    int updateByPrimaryKeySelective(Product record);

    String selectCurrentLastNo(String date);

    List<Product> selectBySupplier(Integer supplierId);

    List<Product> selectByProductType(Integer supplierId, String productType);

    int updateProductBatch(List<Product> list);

    List<ProductType> selectProductType(Integer supplierId);

    int deleteProductTypeById(Integer productTypeId);

    ProductType selectProductTypeByBean(ProductType productType);

    int insertProductType(ProductType product);
}