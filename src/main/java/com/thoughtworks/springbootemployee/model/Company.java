package com.thoughtworks.springbootemployee.model;

public class Company {
    private final long companyId;
    private final String name;

    public Company(long companyId, String name) {
        this.companyId = companyId;
        this.name = name;
    }
    public long getCompanyId() {
        return companyId;
    }

    public String getName() {
        return name;
    }
}