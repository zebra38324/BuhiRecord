package com.example.buhirecord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class CalendarActivity extends AppCompatActivity
        implements CalendarView.OnCalendarSelectListener{
    private CalendarView mCalendarView;
    private CalendarLayout mCalendarLayout;
    private TextView mTitleDate;
    private EditText mEditText;
    private BillItemsAdapter mBillItemsAdapter;
    private RecyclerView mBillItemsRecyclerView;
    private BillItemPopupWindow mBillItemPopupWindow;
    private BuhiRecordDatabaseHelper mBuhiRecordDatabaseHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatabaseHelper();
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_calendar);
        mCalendarLayout = findViewById(R.id.activity_calendar_layout);
        mCalendarView = findViewById(R.id.activity_calendar_view);
        mCalendarView.setOnCalendarSelectListener(this);
        mTitleDate = findViewById(R.id.activity_calendar_top_date);
        mTitleDate.setText(mCalendarView.getCurYear() + "-" + mCalendarView.getCurMonth() + "-" + mCalendarView.getCurDay());
        mEditText = findViewById(R.id.activity_calendar_bottom_edit);
        mBillItemPopupWindow = new BillItemPopupWindow(CalendarActivity.this);
        initBillItemsAdapter();
        initEnterButton();
        initExpandButton();
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        String date = getCalendarDate(calendar);
        mTitleDate.setText(date);
        // TODO: 点击不同日期，显示每天的账单
        updateBillItemsAdapter(date);
    }

    private void initBillItemsAdapter() {
        List<BillItem> billItemList = new ArrayList<>();
        billItemList.add(new BillItem(getCalendarViewDate(), "总计", 0.0));

        mBillItemsAdapter = new BillItemsAdapter(billItemList);
        mBillItemsAdapter.setLongClickListener(new BillItemsAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                if (position == 0) {
                    return;
                }
                BillItemViewHolder vh = (BillItemViewHolder)mBillItemsRecyclerView.findViewHolderForAdapterPosition(position);
                assert vh != null;
                mBillItemPopupWindow.Show(vh.amount);
                Button removeBillItemButton = mBillItemPopupWindow.getContentView().findViewById(R.id.bill_item_popup_window_btn_remove_item);
                removeBillItemButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BillItem item = mBillItemsAdapter.getItem(position);
                        mBuhiRecordDatabaseHelper.removeRecord(item);
                        mBillItemsAdapter.removeItem(position);
                        mBillItemPopupWindow.dismiss();
                        updateBillItemsAdapter(getCalendarViewDate());
                    }
                });
            }
        });

        mBillItemsRecyclerView = findViewById(R.id.activity_calendar_bill_items_recycler_view);
        mBillItemsRecyclerView.setAdapter(mBillItemsAdapter);
        mBillItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        updateBillItemsAdapter(getCalendarViewDate());
    }

    private void initEnterButton() {
        Button enterButton = findViewById(R.id.activity_calendar_bottom_btn_enter);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = mEditText.getText().toString();
                if (inputText.length() == 0) {
                    return;
                }
                double amount = Double.parseDouble(inputText);
                BillItem item = new BillItem(getCalendarViewDate(), "TODO", amount);
                mBillItemsAdapter.addItem(item, mBillItemsAdapter.getItemCount());
                mEditText.setText("");
                mBuhiRecordDatabaseHelper.addRecord(item);
                updateBillItemsAdapter(getCalendarViewDate());
            }
        });
    }

    private void initExpandButton() {
        Button expandButton = findViewById(R.id.activity_calendar_top_btn_expand);
        expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCalendarLayout.isExpand()) {
                    expandButton.setText(R.string.expand);
                    mCalendarLayout.setModeOnlyWeekView();
                    mCalendarLayout.shrink();
                } else {
                    expandButton.setText(R.string.shrink);
                    mCalendarLayout.setModeOnlyMonthView();
                    mCalendarLayout.expand();
                }
            }
        });
    }

    private void initDatabaseHelper() {
        GlobalUtils.setContext(this);
        mBuhiRecordDatabaseHelper = GlobalUtils.getBuhiRecordDatabaseHelper();
    }

    private void updateBillItemsAdapter(String date) {
        List<BillItem> billItemList = mBuhiRecordDatabaseHelper.getRecord(date);
        double sum = getSum(billItemList);
        billItemList.add(0, new BillItem(date, "总计", sum));
        mBillItemsAdapter.updateBillItemList(billItemList);
    }

    private double getSum(List<BillItem> billItemList) {
        double sum = 0;
        for (BillItem item: billItemList) {
            sum += item.getAmount();
        }
        return sum;
    }

    private String getCalendarDate(Calendar calendar) {
        return calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay();
    }

    private String getCalendarViewDate() {
        return mCalendarView.getCurYear() + "-" + mCalendarView.getCurMonth() + "-" + mCalendarView.getCurDay();
    }

}