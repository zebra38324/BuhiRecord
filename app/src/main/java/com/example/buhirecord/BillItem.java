package com.example.buhirecord;

import java.util.Date;

public class BillItem {
    private Date date;
    private String category;
    private Double amount;

    public BillItem(String c, Double a) {
        category = c;
        amount = a;
    }

    public BillItem(Date d, String c, Double a) {
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

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public Double getAmount() {
        return amount;
    }
}
