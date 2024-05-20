package com.example.doancuoiky.model;

import java.io.Serializable;

public class RoomModel implements Serializable{
    private String key, reserveID, number, type, name, image, description, cost;
    private int status;

    public RoomModel(String key, String reserveID, String number, String type, String name, String image, String description, String cost, int status) {
        this.key=key;
        this.reserveID = reserveID;
        this.number = number;
        this.type = type;
        this.name = name;
        this.image = image;
        this.description = description;
        this.status = status;
        this.cost = cost;
    }

    public String getReserveID() {
        return reserveID;
    }

    public void setReserveID(String reserveID) {
        this.reserveID = reserveID;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
