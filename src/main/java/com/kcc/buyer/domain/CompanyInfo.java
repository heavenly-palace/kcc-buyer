package com.kcc.buyer.domain;

import java.io.Serializable;

public class CompanyInfo implements Serializable {

    private static final long serialVersionUID = 6631822960438417111L;

    private Integer id;

    private String cellphone;

    private Integer orderId;

    private Integer companyType;

    private String contacts;

    private String email;

    private String fax;

    private String location;

    private String name;

    private String telephone;

    private AccountInfo accountInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone == null ? null : cellphone.trim();
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Integer companyType) {
        this.companyType = companyType;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    @Override
    public String toString() {
        return "CompanyInfo{" +
                "id=" + id +
                ", cellphone='" + cellphone + '\'' +
                ", orderId=" + orderId +
                ", companyType=" + companyType +
                ", contacts='" + contacts + '\'' +
                ", email='" + email + '\'' +
                ", fax='" + fax + '\'' +
                ", location='" + location + '\'' +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", accountInfo=" + accountInfo +
                '}';
    }
}