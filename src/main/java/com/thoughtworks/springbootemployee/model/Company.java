package com.thoughtworks.springbootemployee.model;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private long id;
    private String name;

    public Company(long id, String name) {
        this.id = id;
        this.name = name;

    }

    public Company() {
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