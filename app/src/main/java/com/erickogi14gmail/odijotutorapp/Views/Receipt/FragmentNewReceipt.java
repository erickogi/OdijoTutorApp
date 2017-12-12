package com.erickogi14gmail.odijotutorapp.Views.Receipt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Date;
import java.util.Locale;


/**
 * Created by Eric on 9/15/2017.
 */

public class FragmentNewReceipt extends Fragment {
    private View view;

    private TextView txtTime, txtName, txtCost, txtDuration, txtSybject;
    private ImageView img;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_receipt, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        return view;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtCost = view.findViewById(R.id.cost);
        txtDuration = view.findViewById(R.id.duration);
        txtName = view.findViewById(R.id.student_name);
        txtSybject = view.findViewById(R.id.subject_name);
        txtTime = view.findViewById(R.id.time);

        img = view.findViewById(R.id.photo);

        ReceiptPojo receiptPojo = (ReceiptPojo) getArguments().getSerializable("data");

        txtCost.setText("");
        txtTime.setText(receiptPojo.getStart());
        txtSybject.setText(receiptPojo.getSubject_name());
        txtName.setText(receiptPojo.getStudent_name());
        txtDuration.setText("");

        Picasso.with(getContext()).load(receiptPojo.getStudent_image()).placeholder(R.drawable.ic_person_black_24dp).fit().into(img);


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
            txtDuration.setText("" + hours + "  hrs");
        }
        int rate = Integer.valueOf(receiptPojo.getRate());
        long tr = hours * rate;

        if (tr > 1) {
            txtCost.setText(String.valueOf(tr) + " Ksh");
        }
        //  }
    }
}
