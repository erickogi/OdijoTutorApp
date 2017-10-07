package com.erickogi14gmail.odijotutorapp.Main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.erickogi14gmail.odijotutorapp.R;

import java.util.ArrayList;

/**
 * Created by Eric on 9/27/2017.
 */

public class FragmentRequests extends Fragment {
    private View view;
    private RequestPojo requestpojo;
    private ArrayList<RequestPojo> requestpojos;
    private RequestAdapter requestAdapter;

    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    public static Fragment fragment = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_request,container,false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        populate();

        initViews();

        return view;
    }

    private ArrayList<RequestPojo> populate() {
        requestpojos=new ArrayList<>();
        for(int a=0;a<50;a++){
            RequestPojo r=new RequestPojo();
            r.setClient_id(a);
            r.setRequest_id(a);
            r.setClient_name("Client Names");
            r.setClient_zone("BuruBuru Estate(Nairobi)");
            r.setClient_image("");
            r.setRequest_date("12/12/2017");
            r.setRequest_startDate("14:30");
            r.setRequest_endDate("16:30");
            r.setStatus(0);

            requestpojos.add(r);


        }

        return requestpojos;
    }
    private void initViews(){

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        requestAdapter=new RequestAdapter(getContext(), populate(), new ClickListenerRequestCard() {
            @Override
            public void onCardClicked(int position) {

            }

            @Override
            public void onBtnAcceptClicked(int position, Button A,Button B) {
             alertDialog(true,"You are about to accept this request\n Continue ? ",position);


            }

            @Override
            public void onBtnDeclineClicked(int position,Button B,Button A) {
                alertDialog(false,"You are about to decline this request\n Continue ? ",position);


            }

            @Override
            public void onClientImageClicked(int position) {

            }

            @Override
            public void onIconMoreClicked(int posotion, View v) {


            }
        });
        requestAdapter.notifyDataSetChanged();
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mStaggeredLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(requestAdapter);

        final FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.hide();




    }
    private void alertDialog(final boolean s, final String message, final int pos){
        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        if(s) {
                            requestpojos.get(pos).setStatus(1);
                            requestAdapter.updateList(requestpojos);
                        }else {
                            requestpojos.get(pos).setStatus(2);
                            requestAdapter.updateList(requestpojos);
                        }


                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();

                        break;
                }
            }
        };





        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setMessage(message).setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

    }
}
