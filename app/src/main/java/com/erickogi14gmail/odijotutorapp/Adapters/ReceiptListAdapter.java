package com.erickogi14gmail.odijotutorapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erickogi14gmail.odijotutorapp.Data.Models.ReceiptPojo;
import com.erickogi14gmail.odijotutorapp.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
        holder.txtReceiptOdijo.setText(receiptPojo.getStudent_name());
        holder.txtReceiptCost.setText("");
        holder.txtReceiptCourse.setText(receiptPojo.getSubject_name());
        holder.txtReceiptTime.setText(receiptPojo.getStart());
        // if(receiptPojo.getSession_status()>0) {
        //  holder.txtRecieptDuration.setText("On Going session");
        // }else {


        Date endTime = time(receiptPojo.getEnd());
        Date startTime = time(receiptPojo.getStart());
        long timeDiff = endTime.getTime() - startTime.getTime();

        Log.d("difff", "" + timeDiff);
        //  long diff = date1.getTime() - date2.getTime();
        long seconds = timeDiff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;


        // int hours = (int)Math.ceil(timeDiff/1000*60*60);
        if (hours > 1) {
            holder.txtRecieptDuration.setText("" + hours + "  hrs");
        }
        int rate = Integer.valueOf(receiptPojo.getRate());
        long tr = hours * rate;

        if (tr > 1) {
            holder.txtReceiptCost.setText(String.valueOf(tr) + " Ksh");
        }
        //  }

        Picasso.with(context).load(receiptPojo.getStudent_image()).fit().placeholder(R.drawable.ic_person_black_24dp).into(holder.imageView);

    }

    public Date time(String d) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        try {
            date = sdf.parse(d);
        } catch (ParseException ex) {
            // Logger.getLogger(Prime.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
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

        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.odijo_photo);
            txtReceiptOdijo = itemView.findViewById(R.id.txt_odijo_name);
            txtReceiptTime = itemView.findViewById(R.id.txt_dateTime);
            txtReceiptCourse = itemView.findViewById(R.id.txt_course);
            txtReceiptCost = itemView.findViewById(R.id.txt_cost);
            txtRecieptDuration = itemView.findViewById(R.id.txt_duration);
        }
    }
}
