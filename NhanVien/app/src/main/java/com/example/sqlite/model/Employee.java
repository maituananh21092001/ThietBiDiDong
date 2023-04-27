package com.example.sqlite.model;

import java.io.Serializable;

public class Employee implements Serializable {
    private int id;
    private String name;
    private String phone;
    private int dob;
    private String gender;
    private String skill;

    public Employee() {
    }

    public Employee(int id, String name, String phone, int dob, String gender, String skill) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.dob = dob;
        this.gender = gender;
        this.skill = skill;
    }

    public Employee(String name, String phone, int dob, String gender, String skill) {
        this.name = name;
        this.phone = phone;
        this.dob = dob;
        this.gender = gender;
        this.skill = skill;
    }

    public int getDob() {
        return dob;
    }

    public void setDob(int dob) {
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
