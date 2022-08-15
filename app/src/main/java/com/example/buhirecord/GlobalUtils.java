package com.example.buhirecord;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.Calendar;

public class GlobalUtils {
    private static GlobalUtils mInstance;
    private BuhiRecordDatabaseHelper mBuhiRecordDatabaseHelper;
    private Context mContext;
    private Calendar mCalender;

    private GlobalUtils() {
        mCalender = Calendar.getInstance();
    }

    public static GlobalUtils getInstance() {
        if (mInstance == null) {
            mInstance = new GlobalUtils();
        }
        return mInstance;
    }

    public static void setContext(Context context) {
        getInstance().mContext = context;
        getInstance().mBuhiRecordDatabaseHelper = new BuhiRecordDatabaseHelper(context);
    }

    public static BuhiRecordDatabaseHelper getBuhiRecordDatabaseHelper() {
        return getInstance().mBuhiRecordDatabaseHelper;
    }

    public List<String> getDateInRange(String beginDate, String endDate) {
        List<Integer> begin = splitDateString(beginDate);
        List<Integer> end = splitDateString(endDate);
        List<String> result = new ArrayList<>();

        // begin，end长度应都为3
        if (begin.size() != 3 || end.size() != 3) {
            return result;
        }
        for (int i = 0; i < 3; i++) {
            if (begin.get(i) < end.get(i)) {
                break;
            } else if (begin.get(i) > end.get(i)) {
                return result;
            }
        }

        for (int year = begin.get(0); year <= end.get(0); year++) {
            int beginMonth = year == begin.get(0) ? begin.get(1) : 1;
            int endMonth = year == end.get(0) ? end.get(1) : 12;
            for (int month = beginMonth; month <= endMonth; month++) {
                int beginDay = month == begin.get(1) ? begin.get(2) : 1;
                int endDay = month == end.get(1) ? end.get(2) : getLastDayOfMonth(year, month);
                for (int day = beginDay; day <= endDay; day++) {
                    result.add(combineDateString(year, month, day));
                }
            }
        }
        return result;
    }

    private int getLastDayOfMonth(int year, int month) {
        int originYear = mCalender.get(Calendar.YEAR);
        int originMonth = mCalender.get(Calendar.MONTH);
        int lastDay = 0;
        mCalender.set(Calendar.YEAR, year);
        mCalender.set(Calendar.MONTH, month - 1);
        lastDay = mCalender.getActualMaximum(Calendar.DAY_OF_MONTH);
        mCalender.set(Calendar.YEAR, originYear);
        mCalender.set(Calendar.MONTH, originMonth);
        return lastDay;
    }

    public List<Integer> splitDateString(String dateString) {
        String[] dateList = dateString.split("-");
        List<Integer> result = new ArrayList<>();
        for (String s: dateList) {
            result.add(Integer.valueOf(s));
        }
        return result;
    }

    public String combineDateString(int year, int month, int day) {
        return String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);
    }
}
