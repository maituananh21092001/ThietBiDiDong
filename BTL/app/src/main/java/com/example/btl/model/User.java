package com.example.btl.model;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String username;
    private String password;

    private String email;
    private String yourName;

    public User(int id, String username, String password,  String email, String yourName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.yourName = yourName;
    }

    public User(String username, String password, String email, String yourName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.yourName = yourName;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getYourName() {
        return yourName;
    }

    public void setYourName(String yourName) {
        this.yourName = yourName;
    }
}
