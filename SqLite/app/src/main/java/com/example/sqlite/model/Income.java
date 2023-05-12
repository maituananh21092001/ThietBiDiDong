package com.example.sqlite.model;

import java.io.Serializable;

public class Income implements Serializable {
    private int id;
    private int salary;
    private int month;
    private User user;
    private String typeIncome;

    public Income(int salary, int month, User user, String typeIncome) {
        this.salary = salary;
        this.month = month;
        this.user = user;
        this.typeIncome = typeIncome;
    }

    public Income(int id, int salary, int month, User user, String typeIncome) {
        this.id = id;
        this.salary = salary;
        this.month = month;
        this.user = user;
        this.typeIncome = typeIncome;
    }

    public Income() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTypeIncome() {
        return typeIncome;
    }

    public void setTypeIncome(String typeIncome) {
        this.typeIncome = typeIncome;
    }
}
