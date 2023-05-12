package com.example.sqlite.model;

import java.io.Serializable;

public class Item implements Serializable {
    private int id;
    private String title;
    private String price;
    private String date;
    private String category;

    private User user;

    public Item() {
    }

    public Item(int id, String title, String price, String date, String category, User user) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.date = date;
        this.category = category;
        this.user = user;
    }

    public Item(String title, String price, String date, String category, User user) {
        this.title = title;
        this.price = price;
        this.date = date;
        this.category = category;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
