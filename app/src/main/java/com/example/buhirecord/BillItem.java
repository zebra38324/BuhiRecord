package com.example.buhirecord;

import java.util.Date;

public class BillItem {
    private Date date;
    private String category;
    private Float amount;

    public BillItem(Date d, String c, Float a) {
        date = d;
        category = c;
        amount = a;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public Float getAmount() {
        return amount;
    }
}
