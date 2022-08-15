package com.example.buhirecord;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class BuhiChart extends View {
    private Paint mXYPaint;
    private Paint mTextPaint;
    private Paint mLinePaint;
    private float mStep; // 一格的长度
    private float mTextSize;
    private float mWidth;
    private float mHeight;
    private List<PointF> mPoints;
    private float mMaxX;
    private float mMaxY;

    public BuhiChart(Context context) {
        this(context, null);
    }

    public BuhiChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BuhiChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        mPoints = new ArrayList<>();
    }

    private void initPaint() {
        mStep = 10;
        mTextSize = 40;
        mXYPaint = new Paint();
        mXYPaint.setColor(Color.BLUE);
        mXYPaint.setStyle(Paint.Style.STROKE);
        mXYPaint.setStrokeWidth(mStep);
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(mTextSize);
        mLinePaint = new Paint();
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(mStep);
    }

    public void setPoints(String beginDate, String endDate, List<Float> amounts) {
        mPoints.clear();
        // mDates = dates;
        for (int i = 0; i < amounts.size(); i++) {
            PointF point = new PointF();
            point.x = i;
            point.y = amounts.get(i);
            mPoints.add(point);
        }
        mMaxX = mPoints.size();
        mMaxY = getMaxY();
        invalidate();
    }

    private float getMaxY() {
        float maxY = Float.MIN_VALUE;
        for (PointF point: mPoints) {
            maxY = Math.max(maxY, point.y);
        }
        return maxY;
    }

    private float getRealX(float x) {
        return x * (mWidth - mStep * 4) / mMaxX + mStep * 2;
    }

    private float getRealY(float y) {
        return mHeight - y * (mHeight - mStep * 4) / mMaxY - mStep * 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(Color.YELLOW);
        mWidth = getWidth();
        mHeight = getHeight();

        canvas.drawLine(mStep/2, 0, mStep/2, mHeight, mXYPaint);
        canvas.drawLine(0, mHeight - mStep/2, mWidth, mHeight - mStep/2, mXYPaint);

        @SuppressLint("DrawAllocation") Path path = new Path();
        for (int i = 0; i < mPoints.size(); i++) {
            PointF point = mPoints.get(i);
            float realX = getRealX(point.x);
            float realY = getRealY(point.y);
            if (i == 0) {
                path.moveTo(realX, realY);
            } else {
                path.lineTo(realX, realY);
            }
        }
        canvas.drawPath(path, mLinePaint);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        canvas.drawText(String.valueOf(mMaxY), mStep, mTextSize, mTextPaint);
    }
}
