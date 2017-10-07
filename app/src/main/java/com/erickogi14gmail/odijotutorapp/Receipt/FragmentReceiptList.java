package com.erickogi14gmail.odijotutorapp.Receipt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erickogi14gmail.odijotutorapp.R;
import com.erickogi14gmail.odijotutorapp.utills.RecyclerTouchListener;
import com.erickogi14gmail.odijotutorapp.utills.StaggeredHiddingScrollListener;

import java.util.ArrayList;

/**
 * Created by Eric on 9/15/2017.
 */

public class FragmentReceiptList extends Fragment {
    private View view;
    private ArrayList<ReceiptPojo> receiptPojos;
    private ReceiptListAdapter receiptListAdapter;

    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;

    private void insertItems() {
        receiptPojos = new ArrayList<>();
        for (int a = 0; a < 20; a++) {
            if ((a & 1) == 0) {
                ReceiptPojo receiptPojo = new ReceiptPojo(a, "Student Name", "Course Name", "1" + a + "/Sep/2017", a + "000",
                        "4hrs 30 mins");
                receiptPojos.add(receiptPojo);
            } else {
                ReceiptPojo receiptPojo = new ReceiptPojo(a, "Student", "Course Name", "1" + a + "/Sep/2017", a + "000",
                        "In Progress");
                receiptPojos.add(receiptPojo);
            }
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_receipt_list,container,false);
        insertItems();
        intViews();
        return view;
    }

    private void intViews() {

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        receiptListAdapter = new ReceiptListAdapter(getContext(), receiptPojos);
        receiptListAdapter.notifyDataSetChanged();
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mStaggeredLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(receiptListAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getContext(), ReceiptActivity.class);
                intent.putExtra("data", receiptPojos);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        recyclerView.setOnScrollListener(new StaggeredHiddingScrollListener() {

            @Override
            public void onHide() {//hideViews();
                //  f.hideRadioBtns();

            }

            @Override
            public void onShow() {//showViews();
                // f.showRadioBtns();


            }
        });
    }
}
