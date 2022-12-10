package com.softaloy.softe_commerce.models;

import java.io.Serializable;

public class MyCartModel implements Serializable {
    String currentDate, currentTime, productName, productPrice,totalquality;
    int totalPrice;
    String documentId;

    public MyCartModel() {
    }

    public MyCartModel(String currentDate, String currentTime, String productName, String productPrice, String totalquality, int totalPrice) {
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalquality = totalquality;
        this.totalPrice = totalPrice;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getTotalquality() {
        return totalquality;
    }

    public void setTotalquality(String totalquality) {
        this.totalquality = totalquality;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}

