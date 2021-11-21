package com.example.buhirecord;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class BillItemsAdapter extends RecyclerView.Adapter<BillItemViewHolder> {
    private final List<BillItem> billItemList;
    private BillItemsAdapter.OnItemLongClickListener mOnItemLongClickListener;

    public BillItemsAdapter(List<BillItem> list) {
        billItemList = list;
    }

    public void setLongClickListener(BillItemsAdapter.OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public BillItem getItem(int position) {
        return billItemList.get(position);
    }

    public void addItem(BillItem item, int position) {
        billItemList.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        billItemList.remove(position);
        notifyItemRemoved(position);
        if (position != billItemList.size()) {
            notifyItemRangeChanged(position, billItemList.size()-position);
        }
    }

    @NonNull
    @Override
    public BillItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BillItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_bill_item_view_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BillItemViewHolder holder, int position) {
        BillItem item = billItemList.get(position);
        holder.category.setText(item.getCategory());
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
        return billItemList.size();
    }
}