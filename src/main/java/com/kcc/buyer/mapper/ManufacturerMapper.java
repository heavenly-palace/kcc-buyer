package com.kcc.buyer.mapper;

import com.kcc.buyer.domain.Manufacturer;

import java.util.List;

public interface ManufacturerMapper {

    List<Manufacturer> selectBySupplierId(Integer supplierId);

    int deleteById(Integer id);

    int insertManufacturer(Manufacturer manufacturer);
}
