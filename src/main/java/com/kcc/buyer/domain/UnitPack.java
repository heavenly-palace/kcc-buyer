package com.kcc.buyer.domain;

public class UnitPack {

    private Integer id;

    private String name;

    private Integer supplierId;

    private Integer flag;

    public UnitPack() {
    }

    public UnitPack(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public UnitPack(Integer supplierId, Integer flag) {
        this.supplierId = supplierId;
        this.flag = flag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "UnitPack{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", supplierId=" + supplierId +
                ", flag=" + flag +
                '}';
    }
}
