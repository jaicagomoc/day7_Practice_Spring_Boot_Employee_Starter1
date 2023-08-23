package com.thoughtworks.springbootemployee.model;

public class Company {
    private final long companyId;
    private String companyName;

    public Company(long companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }
    public long getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}