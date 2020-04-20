package com.kcc.buyer.domain;

import java.io.Serializable;

public class Account implements Serializable {

    private static final long serialVersionUID = 1048375373049758887L;

    private Integer id;

    private Integer companyId;

    private String bankName;

    private String bankAccount;

    private String location;

    private String telephone;

    private String tfn;

    public Account() {
    }

    public Account(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
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

    public String getTfn() {
        return tfn;
    }

    public void setTfn(String tfn) {
        this.tfn = tfn == null ? null : tfn.trim();
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", bankName='" + bankName + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", location='" + location + '\'' +
                ", telephone='" + telephone + '\'' +
                ", tfn='" + tfn + '\'' +
                '}';
    }
}