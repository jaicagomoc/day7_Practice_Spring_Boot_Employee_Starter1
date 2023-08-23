package com.thoughtworks.springbootemployee.model;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private final long id;
    private String name;
    private List<Employee> employees;

    public Company(long id, String name) {
        this.id = id;
        this.name = name;
        this.employees = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}