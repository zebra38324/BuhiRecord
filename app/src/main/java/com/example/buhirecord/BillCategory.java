package com.example.buhirecord;

import androidx.annotation.NonNull;

public class BillCategory {
    public static final String defaultCategory = "其他";

    public static final String sum = "总计";

    public static final String diet = "饮食";
    public static final String breakfast = "早餐";
    public static final String lunch = "午餐";
    public static final String dinner = "晚餐";
    public static final String nightSnack = "夜宵";
    public static final String fruit = "水果";
    public static final String drink = "饮料";
    public static final String snack = "零食";
    public static final String food = "买菜"; // 我真的不知道咋起名字了

    public static final String transportation = "交通";

    public static final String accommodation = "住宿";

    public static final String shop = "购物";
    public static final String cloth = "衣服";
    public static final String grocery = "生活用品";

    public static final String entertainment = "娱乐";
    public static final String game = "游戏";
    public static final String sport = "运动";

    private String mFirstCategory;
    private String mSecondCategory;

    public BillCategory() {
        mFirstCategory = defaultCategory;
        mSecondCategory = defaultCategory;
    }

    public BillCategory(String splitString) {
        String [] category = splitString.split(";");
        if (category.length != 2) {
            mFirstCategory = defaultCategory;
            mSecondCategory = defaultCategory;
            return;
        }
        mFirstCategory = category[0];
        mSecondCategory = category[1];
    }

    public BillCategory(String firstCategory, String secondCategory) {
        mFirstCategory = firstCategory;
        mSecondCategory = secondCategory;
    }

    public String getFirst() {
        return mFirstCategory;
    }

    public String getSecond() {
        return mSecondCategory;
    }

    @NonNull
    public String toString() {
        return mFirstCategory + ";" +mSecondCategory;
    }

    public void setFirst(String firstCategory) {
        mFirstCategory = firstCategory;
    }

    public void setSecond(String secondCategory) {
        mSecondCategory = secondCategory;
    }

    public void setCategory(String firstCategory, String secondCategory) {
        setFirst(firstCategory);
        setSecond(secondCategory);
    }
}
