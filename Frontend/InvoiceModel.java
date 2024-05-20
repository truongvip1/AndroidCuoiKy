package com.example.doancuoiky.model;

import java.io.Serializable;

public class InvoiceModel implements Serializable {
    private String key, checkIn, checkOut, date, review, roomNumber, total, userID;

    public InvoiceModel(String key, String checkIn, String checkOut, String date, String review, String roomNumber, String total, String userID) {
        this.key = key;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.date = date;
        this.review = review;
        this.roomNumber = roomNumber;
        this.total = total;
        this.userID = userID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
