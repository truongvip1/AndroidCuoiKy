package com.example.doancuoiky.model;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String key;
    private String name;
    private String email;
    private String numberphone;
    private String username;
    private String password;
    private String img;
    private String birthday;
    private String address;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHiredate() {
        return hiredate;
    }

    public void setHiredate(String hiredate) {
        this.hiredate = hiredate;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    private String hiredate;
    private int role, salary;

    public UserModel(String key, String name, String email, String numberphone, String username, String password, String img, String birthday, String address, String hiredate, int role, int salary) {
        this.key = key;
        this.name = name;
        this.email = email;
        this.numberphone = numberphone;
        this.username = username;
        this.password = password;
        this.img = img;
        this.birthday = birthday;
        this.address = address;
        this.hiredate = hiredate;
        this.role = role;
        this.salary = salary;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumberphone() {
        return numberphone;
    }

    public void setNumberphone(String numberphone) {
        this.numberphone = numberphone;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}