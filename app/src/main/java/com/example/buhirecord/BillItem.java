package com.example.buhirecord;

import android.content.ContentValues;

import java.util.Date;
import java.util.UUID;

public class BillItem {
    private UUID uuid;
    private long timestamp; // 创建或修改时的时间戳
    private Date date; // 创建时间
    private String category;
    private Double amount;
    private final ContentValues values;

    public BillItem(String c, Double a) {
        uuid = UUID.randomUUID();
        category = c;
        amount = a;
        values = new ContentValues();
        update();
    }

    public BillItem(Date d, String c, Double a) {
        uuid = UUID.randomUUID();
        date = d;
        category = c;
        amount = a;
        values = new ContentValues();
        update();
    }

    public BillItem(UUID u, long t, Date d, String c, Double a) {
        uuid = u;
        timestamp = t;
        date = d;
        category = c;
        amount = a;
        values = new ContentValues();
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

    public UUID getUuid() {
        return uuid;
    }

    public long getTimestamp() {
        return timestamp;
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

    public ContentValues getValues() {
        return values;
    }

    private void update() {
        timestamp = System.currentTimeMillis();
        values.put("uuid", String.valueOf(uuid));
        values.put("timestamp", timestamp);
        values.put("date", String.valueOf(date));
        values.put("category", category);
        values.put("amount", amount);
    }
}