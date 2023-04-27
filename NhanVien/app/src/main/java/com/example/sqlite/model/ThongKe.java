package com.example.sqlite.model;

public class ThongKe {
    private  String name;
    private  int len;

    public ThongKe(String name, int len) {
        this.name = name;
        this.len = len;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }
}
