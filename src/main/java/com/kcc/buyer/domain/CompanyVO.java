package com.kcc.buyer.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class CompanyVO extends Company {

    private Account account;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Product> productList;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
