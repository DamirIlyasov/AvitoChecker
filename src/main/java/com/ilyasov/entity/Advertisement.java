package com.ilyasov.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Advertisement {
    private int year;
    private int price;
    private String city;
    private String description;
    private Date createdAt;

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAtFormatted() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM HH:mm");
        return formatter.format(createdAt);
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPrice() {
        return price;
    }

    public int getYear() {
        return year;
    }

    public String getCity() {
        return city;
    }

    public String getDescription() {
        return description;
    }
}
