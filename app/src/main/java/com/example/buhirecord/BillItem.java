package com.example.buhirecord;

import android.content.ContentValues;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.UUID;

public class BillItem {
    private final UUID uuid;
    private long timestamp; // 创建或修改时的时间戳
    private String date; // yyyy-mm-dd
    private BillCategory category;
    private double amount;
    private ContentValues values;

    public BillItem(String d, BillCategory c, double a) {
        uuid = UUID.randomUUID();
        init(d, c, a);
    }

    public BillItem(UUID u, long t, String d, BillCategory c, double a) {
        uuid = u;
        timestamp = t;
        init(d, c, a);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(BillCategory category) {
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

    public BillCategory getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public ContentValues getValues() {
        return values;
    }

    private void init(String d, BillCategory c, double a) {
        date = d;
        category = c;
        amount = a;
        amount = Double.parseDouble(new DecimalFormat("#.00").format(a));
        values = new ContentValues();
        update();
    }

    private void update() {
        timestamp = System.currentTimeMillis();
        values.put("uuid", String.valueOf(uuid));
        values.put("timestamp", timestamp);
        values.put("date", date);
        values.put("category", category.toString());
        values.put("amount", amount);
    }
}