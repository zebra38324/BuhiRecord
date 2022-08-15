package com.example.buhirecord;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;

public class BuhiDatePicker {
    private DatePickerDialog mDatePickerDialog;
    private String mDate;
    private BuhiDatePickerCallback mCallback;

    // month从0开始，显示日期字符串时需要加一
    public BuhiDatePicker(Context context, int year, int month, int day, BuhiDatePickerCallback callback) {
        mCallback = callback;
        mDate = GlobalUtils.getInstance().combineDateString(year, month + 1, day);
        mDatePickerDialog = new DatePickerDialog(context,
                0,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        mDate = GlobalUtils.getInstance().combineDateString(year, month + 1, day);
                        mCallback.call();
                    }
                },
                year,
                month,
                day);
    }

    public void showDatePickerDialog() {
        if (mDatePickerDialog != null) {
            mDatePickerDialog.show();
        }
    }

    public String getDate() {
        return mDate;
    }
}
