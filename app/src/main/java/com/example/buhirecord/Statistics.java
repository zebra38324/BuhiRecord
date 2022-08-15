package com.example.buhirecord;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Calendar;

public class Statistics extends AppCompatActivity {
    private BuhiRecordDatabaseHelper mBuhiRecordDatabaseHelper;
    private BuhiDatePicker mBeginDatePicker;
    private Button mSelectBeginDateButton;
    private BuhiDatePicker mEndDatePicker;
    private Button mSelectEndDateButton;
    private BuhiChart mBuhiChart;
    private Canvas mChartCanvas;
    private TextView mSumTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_statistics);
        mSumTextView = findViewById(R.id.activity_statistics_bottom_sum);
        initBuhiChart();
        initDatabaseHelper();
        initDatePicker();
        initSelectDateButton();
    }

    private double getBillSumInRange(String beginDate,String endDate) {
        List<String> dates = GlobalUtils.getInstance().getDateInRange(beginDate, endDate);
        List<Float> amounts = new ArrayList<>();
        double sum = 0.0;
        for (String date: dates) {
            double daySum = 0.0;
            List<BillItem> billItemList = mBuhiRecordDatabaseHelper.getRecord(date);
            for (BillItem billItem: billItemList) {
                daySum += billItem.getAmount();
            }
            sum += daySum;
            amounts.add((float) daySum);
        }
        updateBuhiChart(amounts); // 这里逻辑很乱
        return sum;
    }

    private void initBuhiChart() {
        mBuhiChart = findViewById(R.id.activity_statistics_middle_chart);
    }

    private void initDatabaseHelper() {
        GlobalUtils.setContext(this);
        mBuhiRecordDatabaseHelper = GlobalUtils.getBuhiRecordDatabaseHelper();
    }

    private void initDatePicker() {
        BuhiDatePickerCallback callback = new BuhiDatePickerCallback() {
            @Override
            public void call() {
                dateUpdated();
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mBeginDatePicker = new BuhiDatePicker(this, year, month, day, callback);
        mEndDatePicker = new BuhiDatePicker(this, year, month, day, callback);
    }

    // TODO: 精简重复代码
    private void initSelectDateButton() {
        mSelectBeginDateButton = findViewById(R.id.activity_statistics_btn_begin_date);
        mSelectBeginDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBeginDatePicker != null) {
                    mBeginDatePicker.showDatePickerDialog();
                }
            }
        });

        mSelectEndDateButton = findViewById(R.id.activity_statistics_btn_end_date);
        mSelectEndDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEndDatePicker != null) {
                    mEndDatePicker.showDatePickerDialog();
                }
            }
        });

        dateUpdated();
    }

    private void dateUpdated() {
        mSelectBeginDateButton.setText(mBeginDatePicker.getDate());
        mSelectEndDateButton.setText(mEndDatePicker.getDate());
        double sum = getBillSumInRange(mBeginDatePicker.getDate(), mEndDatePicker.getDate());
        sum = Double.parseDouble(new DecimalFormat("#.00").format(sum));
        mSumTextView.setText(String.valueOf(sum));
    }

    private void updateBuhiChart(List<Float> amounts) {
        mBuhiChart.setPoints(mBeginDatePicker.getDate(), mEndDatePicker.getDate(), amounts);
    }
}