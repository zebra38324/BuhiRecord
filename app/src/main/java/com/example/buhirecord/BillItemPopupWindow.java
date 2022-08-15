package com.example.buhirecord;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import java.util.Objects;

public class BillItemPopupWindow extends PopupWindow {
    private final Context mContext;
    private View mContentView;

    public BillItemPopupWindow(Context context) {
        super(context);
        mContext = context;
        init();
    }

    @SuppressLint("InflateParams")
    private void init() {
        setOutsideTouchable(false);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.activity_bill_item_popup_window, null, false);
        setContentView(mContentView);
    }

    public void Show(View view) {
        mContentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int xOff = mContentView.getMeasuredWidth();
        int yOff = mContentView.getMeasuredHeight() / 2;
        this.showAsDropDown(view, xOff, -yOff);
    }
}