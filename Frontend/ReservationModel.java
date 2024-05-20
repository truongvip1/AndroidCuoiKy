package com.example.doancuoiky.model;

import java.io.Serializable;

public class ReservationModel implements Serializable{
    private String key, userID, moreRequire, checkIn, checkOut, roomType, roomNumber, totalPrice;
    private int status;

    public ReservationModel(String key, String userID, String moreRequire, String checkIn, String checkOut, String roomType, String roomNumber, String totalPrice, int status) {
        this.key = key;
        this.userID = userID;
        this.moreRequire = moreRequire;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomType = roomType;
        this.roomNumber = roomNumber;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMoreRequire() {
        return moreRequire;
    }

    public void setMoreRequire(String moreRequire) {
        this.moreRequire = moreRequire;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}