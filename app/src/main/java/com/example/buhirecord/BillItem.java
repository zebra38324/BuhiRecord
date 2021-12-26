package com.example.buhirecord;

import android.content.ContentValues;

import java.util.Date;
import java.util.UUID;

public class BillItem {
    private final UUID uuid;
    private long timestamp; // 创建或修改时的时间戳
    private String date; // yyyy-mm-dd
    private String category;
    private double amount;
    private final ContentValues values;

    public BillItem(String d, String c, double a) {
        uuid = UUID.randomUUID();
        date = d;
        category = c;
        amount = a;
        values = new ContentValues();
        update();
    }

    public BillItem(UUID u, long t, String d, String c, double a) {
        uuid = u;
        timestamp = t;
        date = d;
        category = c;
        amount = a;
        values = new ContentValues();
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public ContentValues getValues() {
        return values;
    }

    private void update() {
        timestamp = System.currentTimeMillis();
        values.put("uuid", String.valueOf(uuid));
        values.put("timestamp", timestamp);
        values.put("date", date);
        values.put("category", category);
        values.put("amount", amount);
    }
}