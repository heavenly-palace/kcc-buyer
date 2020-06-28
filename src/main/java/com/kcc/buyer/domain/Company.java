package com.kcc.buyer.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

public class Company implements Serializable {

    private static final long serialVersionUID = -6453472576791785294L;

    private Integer id;

    private String no;

    private String name;

    private String cellphone;

    private String contacts;

    private String email;

    private String fax;

    private String location;

    private String telephone;

    private Integer type;

    private String comment;

    private Account account;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Product> productList;

    public Company() {
    }

    public Company(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone == null ? null : cellphone.trim();
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", no='" + no + '\'' +
                ", name='" + name + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", contacts='" + contacts + '\'' +
                ", email='" + email + '\'' +
                ", fax='" + fax + '\'' +
                ", location='" + location + '\'' +
                ", telephone='" + telephone + '\'' +
                ", type=" + type +
                ", comment='" + comment + '\'' +
                ", account=" + account +
                ", productList=" + productList +
                '}';
    }
}