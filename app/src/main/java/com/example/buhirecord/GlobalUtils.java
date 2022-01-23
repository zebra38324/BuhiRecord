package com.example.buhirecord;

import android.content.Context;

public class GlobalUtils {
    private static GlobalUtils mInstance;
    private BuhiRecordDatabaseHelper mBuhiRecordDatabaseHelper;
    private Context mContext;

    private GlobalUtils() {

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
}
