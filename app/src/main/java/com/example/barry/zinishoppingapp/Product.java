package com.example.barry.zinishoppingapp;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

@IgnoreExtraProperties  //helps against crashes from extras in firestore (Mitch Tabian note)
public class Product {

    private String name, brand, description;
    private double price;
    private int qty;
    private @ServerTimestamp Date timestamp;
    // this auto adds a timestamp to firestore doc (Mitch Tabian)
    // then alt+enter Product product in Main.java, and change signature to use Date time

   /* public Product() {

    }*/

    public Product() {
        //empty constructor required
    }

    public Product(String name, String brand, String description, double price, int qty) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.qty = qty;
        this.timestamp = timestamp;
    }

    //  ???? the video didn't include setters why????

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
