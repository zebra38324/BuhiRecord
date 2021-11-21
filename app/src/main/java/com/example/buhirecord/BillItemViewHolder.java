package com.example.buhirecord;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

public class BillItemViewHolder extends RecyclerView.ViewHolder {
    TextView category;
    TextView amount;

    public BillItemViewHolder(@NonNull View billItemView) {
        super(billItemView);
        category = billItemView.findViewById(R.id.bill_item_view_holder_category);
        amount = billItemView.findViewById(R.id.bill_item_view_holder_amount);
    }
}