package com.kcc.buyer.mapper;

import com.kcc.buyer.domain.UnitPack;

import java.util.List;

public interface UnitPackMapper {

    List<UnitPack> selectUnitPack(UnitPack unitPack);

    int insertUnitPack(UnitPack unitPack);

    int deleteUnitOrPack(Integer id);
}
