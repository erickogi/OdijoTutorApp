package com.erickogi14gmail.odijotutorapp.Receipt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.erickogi14gmail.odijotutorapp.R;

import java.util.ArrayList;

/**
 * Created by Eric on 9/15/2017.
 */

public class ReceiptListAdapter extends RecyclerView.Adapter<ReceiptListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<ReceiptPojo> modeList;

    public ReceiptListAdapter(Context context, ArrayList<ReceiptPojo> modeList) {
        this.context = context;
        this.modeList = modeList;
    }

    @Override
    public ReceiptListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.receipt_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReceiptListAdapter.MyViewHolder holder, int position) {
        ReceiptPojo receiptPojo = modeList.get(position);
        holder.txtReceiptOdijo.setText(receiptPojo.getReceipt_odijo_name());
        holder.txtReceiptCost.setText(receiptPojo.getReceipt_cost() + "Ksh");
        holder.txtReceiptCourse.setText(receiptPojo.getReceipt_course_name());
        holder.txtReceiptTime.setText(receiptPojo.getReceipt_dateTime());
        holder.txtRecieptDuration.setText(receiptPojo.getReceipt_duration());
    }

    @Override
    public int getItemCount() {
        try {
            return modeList.size();

        } catch (NullPointerException np) {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtReceiptOdijo, txtReceiptCourse, txtReceiptCost, txtRecieptDuration, txtReceiptTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtReceiptOdijo = (TextView) itemView.findViewById(R.id.txt_odijo_name);
            txtReceiptTime = (TextView) itemView.findViewById(R.id.txt_dateTime);
            txtReceiptCourse = (TextView) itemView.findViewById(R.id.txt_course);
            txtReceiptCost = (TextView) itemView.findViewById(R.id.txt_cost);
            txtRecieptDuration = (TextView) itemView.findViewById(R.id.txt_duration);
        }
    }
}
