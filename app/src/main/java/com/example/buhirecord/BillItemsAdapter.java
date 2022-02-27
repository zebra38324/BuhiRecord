package com.example.buhirecord;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class BillItemsAdapter extends RecyclerView.Adapter<BillItemViewHolder> {
    private List<BillItem> mBillItemList;
    private BillItemsAdapter.OnItemLongClickListener mOnItemLongClickListener;

    public BillItemsAdapter(List<BillItem> billItemList) {
        mBillItemList = billItemList;
    }

    public void setLongClickListener(BillItemsAdapter.OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void flushItem() {
        mBillItemList.clear();
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateBillItemList(List<BillItem> billItemList) {
        flushItem();
        mBillItemList = billItemList;
        notifyDataSetChanged();
    }

    public BillItem getItem(int position) {
        return mBillItemList.get(position);
    }

    public void addItem(BillItem item, int position) {
        mBillItemList.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mBillItemList.remove(position);
        notifyItemRemoved(position);
        if (position != mBillItemList.size()) {
            notifyItemRangeChanged(position, mBillItemList.size()-position);
        }
    }

    @NonNull
    @Override
    public BillItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BillItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_bill_item_view_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BillItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        BillItem item = mBillItemList.get(position);
        holder.category.setText(item.getCategory().getSecond()); // 显示secondCategory
        holder.amount.setText(String.valueOf(item.getAmount()));
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemLongClickListener.onItemLongClick(holder.itemView, position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mBillItemList.size();
    }
}